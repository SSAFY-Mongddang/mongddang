// home_screen.dart

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
  StreamSubscription? _bloodSugarSubscription;

  @override
  void initState() {
    super.initState();
    _bloodSugarSubscription = StatRepository.bloodSugarStream.listen((newValue) {
      setState(() {
        bloodSugar = newValue;
        print('새로운 혈당 수치 업데이트: $bloodSugar');
      });
    });

    StatRepository.startSync();
  }

  @override
  void dispose() {
    StatRepository.stopSync();
    _bloodSugarSubscription?.cancel();
    super.dispose();
  }

  Future<void> fetchBloodSugar() async {
    setState(() {
      isLoading = true;
    });

    try {
      final response = await StatRepository.fetchData();
      if (response != null) {
        setState(() {
          bloodSugar = response['bloodSugarLevel'] as int?;
        });
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
      body: Center(
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
              color: Colors.grey[300]!,
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
            '어린이 서원',
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