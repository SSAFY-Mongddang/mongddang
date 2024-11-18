import { api } from '@/shared/api/interceptors';

export const getMedicationList = async (nickname: string) => {
  return await api({
    method: 'GET',
    url: '/api/medication',
    params: {
      nickname: nickname,
    },
  })
    .then((res) => {
      console.log('복약 목록 조회 성공', res.data);
      return res.data.data.medications;
    })
    .catch((err) => {
      console.log(err);
    });
};
