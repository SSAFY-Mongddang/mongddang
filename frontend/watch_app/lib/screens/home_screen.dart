import 'package:flutter/material.dart';
import '../models/activity.dart';
import 'activity_screen.dart';

class HomeScreen extends StatelessWidget {
  const HomeScreen({super.key});

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
                imagePath: 'assets/img/img1.png',
                onTap: () => _navigateToActivity(context, ActivityType.sleep),
              ),
              _buildActivityIcon(
                position: const Offset(43, 18),
                imagePath: 'assets/img/img2.png',
                onTap: () => _navigateToActivity(context, ActivityType.exercise),
                alignment: Alignment.topRight,
              ),
              _buildActivityIcon(
                position: const Offset(18, 68),
                imagePath: 'assets/img/img3.png',
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
      child: Image.asset(
        'assets/img/img4.png',
        height: 150,
        fit: BoxFit.fill,
        errorBuilder: (context, error, stackTrace) {
          return Container(
            height: 70,
            decoration: BoxDecoration(
              color: Colors.green[300],
              borderRadius: const BorderRadius.only(
                bottomLeft: Radius.circular(96),
                bottomRight: Radius.circular(96),
              ),
            ),
          );
        },
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
      child: Container(
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
        child: const Center(
          child: Text(
            '80',
            style: TextStyle(
              fontSize: 16,
              fontWeight: FontWeight.bold,
              color: Colors.black,
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
            'assets/img/img3.png',
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
            '너 뭐 되는 갱얼쥐',
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