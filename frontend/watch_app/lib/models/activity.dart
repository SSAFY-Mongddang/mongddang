// lib/models/activity.dart
import '../repository/stat_repository.dart';
enum ActivityType { eating, exercise, sleep }

class Activity {
  final ActivityType type;
  final String title;
  final String activeTitle;
  final String completeTitle;
  final String question;
  final String imagePath;
  int defaultScore;  // final 제거하여 수정 가능하게 변경
  final int bloodSugarLevel;

  Activity({
    required this.type,
    required this.title,
    required this.activeTitle,
    required this.completeTitle,
    required this.question,
    required this.imagePath,
    required this.defaultScore,
    this.bloodSugarLevel = 0,
  });

  // Map을 const에서 일반 변수로 변경하여 값 업데이트 가능하게 함
  static Map<ActivityType, Activity> activities = {
    ActivityType.eating: Activity(
      type: ActivityType.eating,
      title: '밥 먹기',
      activeTitle: '밥 먹기 중!',
      completeTitle: '밥 먹기 끝!',
      question: '밥 다 먹었어?',
      imagePath: 'assets/img/meal_mongddang.png',
      defaultScore: 0,  // API에서 받아올 값으로 업데이트될 예정
    ),
    ActivityType.exercise: Activity(
      type: ActivityType.exercise,
      title: '운동',
      activeTitle: '운동 중!',
      completeTitle: '운동 끝!',
      question: '운동 다했어?',
      imagePath: 'assets/img/exercise_mongddang.png',
      defaultScore: 0,
    ),
    ActivityType.sleep: Activity(
      type: ActivityType.sleep,
      title: '잠자기',
      activeTitle: '잠자기 중!',
      completeTitle: '잠자기 끝!',
      question: '이제 일어날거야?',
      imagePath: 'assets/img/sleep_mongddang.png',
      defaultScore: 0,
    ),
  };

  // 현재 혈당을 가져와서 defaultScore 업데이트
  static Future<void> updateCurrentBloodSugar() async {
    try {
      final response = await StatRepository.fetchData();
      final bloodSugar = response['bloodSugarLevel'] as int;

      // 모든 활동의 defaultScore를 현재 혈당으로 업데이트
      activities.forEach((type, activity) {
        activity.defaultScore = bloodSugar;
      });
    } catch (e) {
      print('혈당 업데이트 실패: $e');
    }
  }
}