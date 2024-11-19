import { api } from '@/shared/api/interceptors';

export const startExercise = () => {
  return api({
    method: 'POST',
    url: '/api/record/exercise/start',
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

export const endExercise = () => {
  return api({
    method: 'PATCH',
    url: '/api/record/exercise/end',
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
