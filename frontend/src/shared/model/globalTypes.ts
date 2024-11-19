export type PushNotificationCategory =
  | 'blood_sugar'
  | 'game'
  | 'medication'
  | 'connect';

export type PushNotificationItem = {
  receiverNickname: string;
  childNickname: string;
  title: string;
  message: string;
  category: PushNotificationCategory;
};
