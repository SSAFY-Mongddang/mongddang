import dayjs from 'dayjs';

export const calcTimeDuration = (startTime: string, endTime: string) => {
  if (!endTime) {
    return '종료 시각 없음';
  }
  const start = dayjs(startTime);
  const end = dayjs(endTime);

  return end.diff(start, 'minute');
};
