import { api } from '@/shared/api/interceptors';

export const startSleep = async () => {
  return await api({
    method: 'POST',
    url: '/api/record/sleep/start',
  })
    .then((res) => {
      console.log(res.data);
      return res.data;
    })
    .catch((err) => {
      console.log(err);
      return err;
    });
};

export const endSleep = async () => {
  return await api({
    method: 'PATCH',
    url: '/api/record/sleep/end',
  })
    .then((res) => {
      console.log(res.data);
      return res.data;
    })
    .catch((err) => {
      console.log(err);
      return err;
    });
};
