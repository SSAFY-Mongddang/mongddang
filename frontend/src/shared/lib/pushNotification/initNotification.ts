import { sendTokenToServer } from '@/shared/api/push-notification/pushNotificationApi';
import { getFcmToken, setFcmToken } from '@/shared/hooks/useFcmTokenStatus';
import { PushNotifications } from '@capacitor/push-notifications';

// 푸시 알림 권한 설정
export const initPushNotification = async () => {
  const { receive } = await PushNotifications.checkPermissions();
  if (receive !== 'granted') {
    const requestResult = await PushNotifications.requestPermissions();

    if (requestResult.receive !== 'granted') {
      return;
    }
  }

  const prevFcmToken = await getFcmToken();
  if (!prevFcmToken) {
    await PushNotifications.register();
    console.log('알림 권한이 허용됨');
    PushNotifications.addListener('registration', (token) => {
      console.log('푸시 알림 토큰 등록 완료', token.value);
      sendTokenToServer(token.value);
      setFcmToken(token.value);
    });
  }

  PushNotifications.addListener('registrationError', (error) => {
    console.error('푸시 알림 등록 실패:', error);
  });
};
