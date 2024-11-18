// stat_repository.dart

import 'dart:async';
import 'package:dio/dio.dart';
import 'package:flutter/services.dart';
import 'package:flutter_dotenv/flutter_dotenv.dart';

class StatRepository {
  static const platform = MethodChannel('com.example.watch_app');
  static Timer? _timer;
  static bool _isRunning = false;
  static double? _lastGlucoseValue;

  static final _bloodSugarController = StreamController<int>.broadcast();
  static Stream<int> get bloodSugarStream => _bloodSugarController.stream;

  static const double LOW_THRESHOLD = 70.0;
  static const double HIGH_THRESHOLD = 180.0;

  static Future<void> sendGlucoseToWatch(dynamic data) async {
    try {
      final glucoseValue = double.parse(data['bloodSugarLevel'].toString());

      if (_lastGlucoseValue == glucoseValue) {
        print('혈당값 변화 없음: $glucoseValue');
        return;
      }

      _lastGlucoseValue = glucoseValue;

      final isLow = glucoseValue <= LOW_THRESHOLD;
      final isHigh = glucoseValue >= HIGH_THRESHOLD;
      final needsAlert = isLow || isHigh;

      print('워치로 전송 시도: $glucoseValue (알림 필요: $needsAlert)');

      await platform.invokeMethod('sendGlucoseUpdate', {
        'glucose_value': glucoseValue.toString(),
        'needs_alert': needsAlert,
        'alert_type': isLow ? 'low' : (isHigh ? 'high' : 'normal')
      });

      print('워치로 전송 성공: $glucoseValue');

      if (needsAlert) {
        print('알림 타입: ${isLow ? "저혈당" : "고혈당"}');
      }
    } catch (e) {
      print('워치 전송 에러: $e');
    }
  }

  static Future<dynamic> fetchData() async {
    try {
      final dio = Dio();
      dio.options.headers = {
        'Authorization': 'Bearer ${dotenv.env['ACCESS_TOKEN']}',
        'Content-Type': 'application/json',
      };

      final response = await dio.post(
        '${dotenv.env['BASE_URL']}/api/vital/bloodsugar/current?nickname=어린이 서원',
      );

      if (response.data != null && response.data['data'] != null) {
        await sendGlucoseToWatch(response.data['data']);

        final bloodSugar = response.data['data']['bloodSugarLevel'] as int;
        print('새로운 혈당 데이터 수신: $bloodSugar');
        _bloodSugarController.add(bloodSugar);

        return response.data['data'];
      }

      return null;
    } catch (e) {
      _logError(e);
      throw e;
    }
  }

  static void _logError(dynamic e) {
    print('에러 상세 정보:');
    print('에러 타입: ${e.runtimeType}');
    if (e is DioException) {
      print('에러 메시지: ${e.message}');
      print('에러 타입: ${e.type}');
      print('상태 코드: ${e.response?.statusCode}');
      print('응답 데이터: ${e.response?.data}');
    }
  }

  static void startSync() {
    if (!_isRunning) {
      _isRunning = true;

      fetchData().then((data) {
        if (data != null) {
          final bloodSugar = data['bloodSugarLevel'] as int;
          _bloodSugarController.add(bloodSugar);
        }
      });

      _timer = Timer.periodic(const Duration(minutes: 1), (timer) async {
        try {
          final data = await fetchData();
          if (data != null) {
            double glucoseValue = double.parse(data['bloodSugarLevel'].toString());
            print('현재 혈당(주기적 업데이트): $glucoseValue mg/dL');

            if (glucoseValue <= LOW_THRESHOLD) {
              print('경고: 저혈당 감지! ($glucoseValue mg/dL)');
            } else if (glucoseValue >= HIGH_THRESHOLD) {
              print('경고: 고혈당 감지! ($glucoseValue mg/dL)');
            }
          }
        } catch (e) {
          print('데이터 가져오기 에러: $e');
        }
      });
    }
  }

  static void stopSync() {
    _timer?.cancel();
    _timer = null;
    _isRunning = false;
    _lastGlucoseValue = null;
    print('혈당 모니터링 중지됨');
  }

  static void dispose() {
    stopSync();
    _bloodSugarController.close();
  }
}