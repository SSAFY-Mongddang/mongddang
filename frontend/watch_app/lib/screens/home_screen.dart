import 'dart:async';
import 'package:flutter/material.dart';
import '../models/activity.dart';
import '../repository/stat_repository.dart';
import 'activity_screen.dart';

class HomeScreen extends StatefulWidget {
  const HomeScreen({super.key});

  @override
  State<HomeScreen> createState() => _HomeScreenState();
}

class _HomeScreenState extends State<HomeScreen> {
  int? bloodSugar;
  bool isLoading = false;
  bool showWarning = false;
  String warningMessage = '';
  Color warningColor = Colors.transparent;
  Timer? _warningTimer;
  Timer? _periodicFetchTimer;

  @override
  void initState() {
    super.initState();

    // 1분마다 혈당값 가져오기
    _periodicFetchTimer = Timer.periodic(const Duration(minutes: 1), (timer) {
      fetchBloodSugar();
    });

    // 초기 데이터 가져오기
    fetchBloodSugar();

    StatRepository.startSync();
  }

  void _checkBloodSugarLevel(int value) {
    if (value >= 180) {
      _showWarning('고혈당 주의!', Colors.red.withOpacity(0.6));
    } else if (value <= 70) {
      _showWarning('저혈당 주의!', Colors.blue.withOpacity(0.6));
    }
  }

  void _showWarning(String message, Color color) {
    setState(() {
      showWarning = true;
      warningMessage = message;
      warningColor = color;
    });

    // 기존 타이머가 있다면 취소
    _warningTimer?.cancel();

    // 5초 후에 경고를 숨김
    _warningTimer = Timer(const Duration(seconds: 5), () {
      if (mounted) {
        setState(() {
          showWarning = false;
        });
      }
    });
  }

  @override
  void dispose() {
    StatRepository.stopSync();
    _warningTimer?.cancel();
    _periodicFetchTimer?.cancel();
    super.dispose();
  }

  Future<void> fetchBloodSugar() async {
    setState(() {
      isLoading = true;
    });

    try {
      final response = await StatRepository.fetchData();
      if (response != null) {
        final newBloodSugar = response['bloodSugarLevel'] as int?;
        setState(() {
          bloodSugar = newBloodSugar;
        });
        if (newBloodSugar != null) {
          _checkBloodSugarLevel(newBloodSugar);
        }
      }
    } catch (e) {
      print('혈당 데이터 가져오기 실패: $e');
    } finally {
      setState(() {
        isLoading = false;
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.black,
      body: Stack(
        children: [
          Center(
            child: Container(
              width: 192,
              height: 192,
              decoration: BoxDecoration(
                shape: BoxShape.circle,
                gradient: LinearGradient(
                  begin: Alignment.topCenter,
                  end: Alignment.bottomCenter,
                  colors: [
                    Colors.lightBlue[200]!,
                    Colors.lightBlue[100]!,
                  ],
                ),
              ),
              child: Stack(
                children: [
                  _buildBackground(),
                  _buildActivityIcon(
                    position: const Offset(50, 18),
                    imagePath: 'assets/img/sleep_icon.png',
                    onTap: () => _navigateToActivity(context, ActivityType.sleep),
                  ),
                  _buildActivityIcon(
                    position: const Offset(43, 18),
                    imagePath: 'assets/img/exercise_icon.png',
                    onTap: () => _navigateToActivity(context, ActivityType.exercise),
                    alignment: Alignment.topRight,
                  ),
                  _buildActivityIcon(
                    position: const Offset(18, 68),
                    imagePath: 'assets/img/meal_icon.png',
                    onTap: () => _navigateToActivity(context, ActivityType.eating),
                  ),
                  _buildScoreDisplay(),
                  _buildBottomContent(),
                ],
              ),
            ),
          ),
          // 경고 오버레이
          if (showWarning)
            AnimatedOpacity(
              opacity: showWarning ? 1.0 : 0.0,
              duration: const Duration(milliseconds: 300),
              child: Container(
                color: warningColor,
                child: Center(
                  child: Text(
                    warningMessage,
                    style: const TextStyle(
                      fontSize: 24,
                      fontWeight: FontWeight.bold,
                      color: Colors.white,
                    ),
                  ),
                ),
              ),
            ),
        ],
      ),
    );
  }

  Widget _buildBackground() {
    return Positioned(
      bottom: 0,
      left: 0,
      right: 0,
      child: ClipRRect(
        borderRadius: const BorderRadius.vertical(
          top: Radius.circular(43),
          bottom: Radius.circular(96),
        ),
        child: Image.asset(
          'assets/img/background.png',
          height: 150,
          fit: BoxFit.fill,
          errorBuilder: (context, error, stackTrace) {
            return Container(
              height: 150,
              decoration: BoxDecoration(
                color: Colors.green[300],
                borderRadius: const BorderRadius.vertical(
                  top: Radius.circular(43),
                  bottom: Radius.circular(96),
                ),
              ),
            );
          },
        ),
      ),
    );
  }

  Widget _buildActivityIcon({
    required Offset position,
    required String imagePath,
    required VoidCallback onTap,
    Alignment alignment = Alignment.topLeft,
  }) {
    return Positioned(
      top: position.dy,
      left: alignment == Alignment.topLeft ? position.dx : null,
      right: alignment == Alignment.topRight ? position.dx : null,
      child: GestureDetector(
        onTap: onTap,
        child: Stack(
          children: [
            Container(
              width: 45,
              height: 45,
              decoration: BoxDecoration(
                shape: BoxShape.circle,
                color: Colors.white.withOpacity(0.3),
              ),
            ),
            Positioned(
              top: 2,
              left: 2,
              child: Image.asset(
                imagePath,
                width: 40,
                height: 40,
                errorBuilder: (context, error, stackTrace) {
                  return Container(
                    width: 30,
                    height: 30,
                    color: Colors.red.withOpacity(0.3),
                    child: const Icon(Icons.error),
                  );
                },
              ),
            ),
          ],
        ),
      ),
    );
  }

  Widget _buildScoreDisplay() {
    // 혈당 데이터 테두리 (null/저혈당/고혈당)
    Color getBorderColor() {
      if (bloodSugar == null) return Colors.grey[300]!;
      if (bloodSugar! <= 70) return Colors.blue;
      if (bloodSugar! < 180) return Colors.green;
      return Colors.red;
    }

    return Positioned(
      top: 70,
      right: 20,
      child: GestureDetector(
        onTap: fetchBloodSugar,
        child: AnimatedContainer(
          duration: const Duration(milliseconds: 300),
          width: 36,
          height: 36,
          decoration: BoxDecoration(
            shape: BoxShape.circle,
            color: Colors.white,
            border: Border.all(
              color: getBorderColor(),
              width: 2,
            ),
          ),
          child: isLoading
              ? const SizedBox(
            width: 20,
            height: 20,
            child: CircularProgressIndicator(
              strokeWidth: 2,
              valueColor: AlwaysStoppedAnimation<Color>(Colors.blue),
            ),
          )
              : Center(
            child: Text(
              bloodSugar?.toString() ?? '--',
              style: const TextStyle(
                fontSize: 16,
                fontWeight: FontWeight.bold,
                color: Colors.black,
              ),
            ),
          ),
        ),
      ),
    );
  }

  Widget _buildBottomContent() {
    return Positioned(
      bottom: 15,
      left: 0,
      right: 0,
      child: Column(
        mainAxisSize: MainAxisSize.min,
        children: [
          Image.asset(
            'assets/img/mongddang01.png',
            width: 70,
            height: 70,
            errorBuilder: (context, error, stackTrace) {
              return Container(
                width: 70,
                height: 70,
                color: Colors.transparent,
              );
            },
          ),
          const SizedBox(height: 4),
          const Text(
            '어린이 세희',
            textAlign: TextAlign.center,
            style: TextStyle(
              fontSize: 12,
              color: Colors.black87,
              fontWeight: FontWeight.bold,
            ),
          ),
        ],
      ),
    );
  }

  void _navigateToActivity(BuildContext context, ActivityType type) {
    Navigator.push(
      context,
      MaterialPageRoute(
        builder: (context) => ActivityScreen(type: type),
      ),
    );
  }
}