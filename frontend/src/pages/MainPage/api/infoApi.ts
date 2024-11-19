import { api } from '@/shared/api/interceptors';

// 유저 정보
export const getMainInfo = () => {
  return api({
    method: 'GET',
    url: '/api/main',
  })
    .then((res) => {
      console.log('메인페이지 정보 조회 성공', res.data.data);
      return res.data.data;
    })
    .catch((err) => {
      console.log('메인페이지 정보 조회 실패', err.message);
      return err;
    });
};

// 일일 미션 조회
export const getDailyMission = () => {
  return api({
    method: 'GET',
    url: '/api/mission',
  })
    .then((res) => {
      console.log('일일 미션 조회 성공', res.data);
      return res.data.data;
    })
    .catch((err) => {
      console.log(err);
      return err;
    });
};

// 일일 미션 보상 수령
export const getReward = async (missionId: number) => {
  await api({
    method: 'POST',
    url: '/api/mission/reward',
    data: {
      missionId: missionId,
    },
  })
    .then((res) => {
      console.log(res);
      return missionId;
    })
    .catch((err) => {
      console.log(err);
      return err;
    });
};
