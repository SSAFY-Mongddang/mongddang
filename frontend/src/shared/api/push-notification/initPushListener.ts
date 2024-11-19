import { initPushNotification } from '@/shared/lib/pushNotification/initNotification';
import {
  PushNotificationSchema,
  PushNotifications,
} from '@capacitor/push-notifications';

export const InitializePushListener = async (
  setPushNotification: (notification: PushNotificationSchema) => void
) => {
  await initPushNotification();
  PushNotifications.removeAllListeners();
  PushNotifications.addListener(
    'pushNotificationReceived',
    (notification) => {
      setPushNotification(notification);
    }
  );
};
