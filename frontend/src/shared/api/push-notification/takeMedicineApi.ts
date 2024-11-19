import { api } from '../interceptors';

export const takeMedicine = () => {
  return api({
    method: 'POST',
    url: '/api/record/medication/check',
  })
    .then((res) => {
      console.log('복약 확인 성공', res);
    })
    .catch((err) => {
      console.log('복약 확인 실패', err);
    });
};
