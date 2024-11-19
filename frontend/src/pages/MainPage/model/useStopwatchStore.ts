import { create } from 'zustand';
import { getExitTime, getStopwatch } from '../hooks/useStopwatchStatus';

// 사용할 필드와 메서드를 선언하기
type StopwatchInfo = {
  time: number;
  isRunning: boolean;
  intervalRef: NodeJS.Timeout | null;
  startStopwatch: () => void;
  endStopwatch: () => void;
  updateStopwatch: () => void;
  finalTime: string;
};

const formatTime = (time: number) => {
  // const hours = Math.floor(time / 3600000); // 1시간 = 3600초
  const minutes = Math.floor((time % 3600000) / 60000); // 1분 = 60초
  const seconds = Math.floor((time % 60000) / 1000); // 1분 = 60초
  return `${String(minutes).padStart(2, '0')} : ${String(seconds).padStart(2, '0')}`;
};

export const useStopwatchStore = create<StopwatchInfo>((set, get) => ({
  // 초기값을 설정
  time: 0,
  isRunning: false,
  intervalRef: null,
  finalTime: '⏳',

  // 스톱워치 시작
  startStopwatch: () => {
    const { isRunning } = get();
    if (isRunning === false) {
      const newInterval = setInterval(() => {
        set((state) => {
          const newTime = state.time + 1000;
          return {
            time: newTime,
            finalTime: formatTime(newTime),
          };
        });
      }, 1000);
      set({ isRunning: true, intervalRef: newInterval });
    }
  },

  // 스톱워치 끝
  endStopwatch: () => {
    const { isRunning, intervalRef } = get();
    if (isRunning === true) {
      if (intervalRef !== null) {
        clearInterval(intervalRef);

        set({
          time: 0,
          finalTime: '⏳',
          isRunning: false,
          intervalRef: null,
        });
      }
    }
    console.log('finalTime 업데이트0');
  },

  // 앱 다시 켰을 때 스톱워치 업데이트
  updateStopwatch: async () => {
    const latestExitTimeString = await getExitTime();
    const latestExitTime = Number(latestExitTimeString);
    if (latestExitTime !== 0) {
      const latestStopwatchTime = await getStopwatch();
      const newTime = Date.now() - latestExitTime + Number(latestStopwatchTime);

      const { intervalRef } = get();
      if (intervalRef) {
        clearInterval(intervalRef);
      }

      set({ time: newTime, isRunning: true });
      const newInterval = setInterval(() => {
        set((state) => {
          const restartTime = state.time + 1000;
          return {
            time: restartTime,
            finalTime: formatTime(restartTime),
          };
        });
      }, 1000);
      set({ intervalRef: newInterval });
    }
  },
}));
