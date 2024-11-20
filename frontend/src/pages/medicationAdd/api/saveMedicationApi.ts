import { MedicationItemType } from '@/pages/medication/types';
import { api } from '@/shared/api/interceptors';

export const saveMedication = async (medicationData: MedicationItemType) => {
  console.log('data from saveMedication');
  console.log('medicationData: ', medicationData);

  return await api({
    method: 'POST',
    url: '/api/medication/register',
    data: {
      ...medicationData,
    },
  })
    .then((res) => {
      console.log(res.data);
    })
    .catch((err) => {
      console.log('saveMedication Err');
      console.log(err);
    });
};
