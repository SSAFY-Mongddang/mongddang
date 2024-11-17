import { Preferences } from '@capacitor/preferences';

// fcm 토큰 저장
export const setFcmToken = async (token: string) => {
  await Preferences.set({
    key: 'fcmToken',
    value: token,
  });
  console.log('fcm 토큰 저장됨', token);
};

// fcm 토큰 조회
export const getFcmToken = async (): Promise<string | null> => {
  const { value } = await Preferences.get({ key: 'fcmToken' });
  return value || null;
};
