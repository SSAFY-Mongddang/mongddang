// lib/models/activity.dart
enum ActivityType { eating, exercise, sleep }

class Activity {
  final ActivityType type;
  final String title;
  final String activeTitle;
  final String completeTitle;
  final String question;
  final String imagePath;
  final int defaultScore;

  const Activity({
    required this.type,
    required this.title,
    required this.activeTitle,
    required this.completeTitle,
    required this.question,
    required this.imagePath,
    required this.defaultScore,
  });

  static const Map<ActivityType, Activity> activities = {
    ActivityType.eating: Activity(
      type: ActivityType.eating,
      title: '밥 먹기',
      activeTitle: '밥 먹기 중!',
      completeTitle: '밥 먹기 끝!',
      question: '밥 다 먹었어?',
      imagePath: 'assets/img/img3.png',
      defaultScore: 120,
    ),
    ActivityType.exercise: Activity(
      type: ActivityType.exercise,
      title: '운동',
      activeTitle: '운동 중!',
      completeTitle: '운동 끝!',
      question: '운동 다했어?',
      imagePath: 'assets/img/img2.png',
      defaultScore: 70,
    ),
    ActivityType.sleep: Activity(
      type: ActivityType.sleep,
      title: '잠자기',
      activeTitle: '잠자기 중!',
      completeTitle: '잠자기 끝!',
      question: '이제 일어날거야?',
      imagePath: 'assets/img/img1.png',
      defaultScore: 70,
    ),
  };
}