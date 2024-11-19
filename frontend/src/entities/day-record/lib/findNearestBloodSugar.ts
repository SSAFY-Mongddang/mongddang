import { Bloodsugar } from '@/shared/api/blood-sugar';
import dayjs from 'dayjs';

export const findNearestTimeBloodSugar = (
  data: Bloodsugar[],
  targetTime: string
): number => {
  const convertTimeToMinutes = (time: string): number => {
    const dayjsTime = dayjs(time);
    return dayjsTime.hour() * 60 + dayjsTime.minute();
  };

  const getTimeDifference = (time1: number, time2: number): number => {
    return Math.abs(time1 - time2);
  };

  const targetMinutes = convertTimeToMinutes(targetTime);
  let left = 0;
  let right = data.length - 1;
  let nearestEntry = data[0].bloodSugarLevel;
  let minDiff = Infinity;

  while (left <= right) {
    const mid = Math.floor((left + right) / 2);
    const currentMinutes = convertTimeToMinutes(data[mid].measurementTime);
    const timeDiff = getTimeDifference(targetMinutes, currentMinutes);

    if (timeDiff < minDiff) {
      minDiff = timeDiff;
      nearestEntry = data[mid].bloodSugarLevel;
    }

    if (currentMinutes < targetMinutes) {
      left = mid + 1;
    } else if (currentMinutes > targetMinutes) {
      right = mid - 1;
    } else {
      return data[mid].bloodSugarLevel;
    }
  }

  return nearestEntry;
};