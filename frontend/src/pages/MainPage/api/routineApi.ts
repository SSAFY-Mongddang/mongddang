import { api } from '@/shared/api/interceptors';

export const getInitialRoutine = async () => {
  return await api({
    method: 'GET',
    url: '/api/record/ongoing',
  })
    .then((res) => {
      console.log(res);
      return res.data;
    })
    .catch((err) => {
      console.log(err);
      return err;
    });
};
