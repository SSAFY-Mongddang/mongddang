import { api } from '../interceptors';

export const sendTokenToServer = async (token: string) => {
  console.log('알림 서버 전송용 토큰', token);
  await api({
    method: 'POST',
    url: '/api/fcm/token',
    data: {
      token: token,
    },
  })
    .then((res) => {
      console.log('서버에 푸시 알림 토큰 전송 성공', res);
    })
    .catch((err) => {
      console.log('서버에 푸시 알림 토큰 전송 실패', err);
    });
};
