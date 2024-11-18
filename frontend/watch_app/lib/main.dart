import 'package:flutter/material.dart';
import 'screens/home_screen.dart';
import 'repository/stat_repository.dart';
import 'package:flutter_dotenv/flutter_dotenv.dart';

void main() async {
  WidgetsFlutterBinding.ensureInitialized();  // Flutter 바인딩 초기화
  StatRepository.startSync();  // 동기화 시작
  await dotenv.load(fileName: ".env");
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,  // DEBUG 배너 제거
      title: '혈당 모니터 앱',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: const HomeScreen(),
    );
  }
}