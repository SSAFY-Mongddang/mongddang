import { Bloodsugar } from '@/shared/api/blood-sugar';
import dayjs from 'dayjs';

export const timeConverter = (data: Bloodsugar[]) => {
  return data.map((item) => ({
    ...item,
    measurementTime: dayjs(item.measurementTime).format('HH:mm'),
  }));
};
