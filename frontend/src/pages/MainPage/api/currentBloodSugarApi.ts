import { api } from '@/shared/api/interceptors';

export const getCurrentBloodSugar = async (nickname: string) => {
  return await api({
    method: 'POST',
    url: '/api/vital/bloodsugar/current',
    params: {
      nickname: nickname,
    },
  })
    .then((res) => {
      console.log('현재 혈당', res.data.data);
      return res.data.data;
    })
    .catch((err) => {
      console.log(err);
      return err;
    });
};
