import { MedicationStandard } from '@/pages/medication/types';
import { create } from 'zustand';
import dayjs from 'dayjs';

type MedicationAddItem = {
  nickname: string;
  name: string;
  image: null;
  repeatStartTime: Date | null;
  repeatEndTime: Date | null;
  isFast: boolean;
  repeatTimes: string[];
  standards: MedicationStandard[] | null;
  setUserInfo: (nickname: string) => void;
  setIsFastInfo: (isFast: boolean) => void;
  setMedicationInfo: (name: string) => void;
  setMedicationPeriod: (startDate: Date, endDate: Date) => void;
  setMedicationTime: (
    timeFields: { id: number; hour: number; minute: number }[]
  ) => void;
  setStandard: (standard: MedicationStandard[]) => void;
  resetStandard: () => void;
  initializeAdd: () => void;
};

export const useMedicationAddStore = create<MedicationAddItem>((set) => ({
  nickname: '',
  name: '',
  image: null,
  repeatStartTime: null,
  repeatEndTime: null,
  isFast: false,
  repeatTimes: [''],
  standards: [
    {
      volume: 0,
      minGlucose: 0,
      maxGlucose: 0,
    },
  ],

  // 닉네임 정보
  setUserInfo: (nickname: string) => {
    set({ nickname: nickname });
    console.log('store에 들어온', nickname);
  },

  // 약 정보
  setMedicationInfo: (name: string) => {
    set({
      name: name,
    });
  },

  // 속효, 지효
  setIsFastInfo: (isFast: boolean) => {
    set({ isFast: isFast });
  },

  // 복약 기간
  setMedicationPeriod: (startDate: Date, endDate: Date) => {
    set({
      repeatStartTime: startDate,
      repeatEndTime: endDate,
    });
    console.log(startDate, endDate);
  },

  // 복약 시간
  setMedicationTime: (timeFields) => {
    const timeStrings = timeFields.reduce<string[]>(
      (acc, { id, hour, minute }) => {
        console.log('timeFields', timeFields);
        if (hour < 0 || hour > 23 || minute < 0 || minute > 59) {
          console.warn(`Invalid time for id ${id}: ${hour}:${minute}`);
          return acc;
        }

        const timeString = dayjs().hour(hour).minute(minute).format('HH:mm');
        console.log('timeString', timeString);
        return [...acc, timeString];
      },
      []
    );

    set({ repeatTimes: timeStrings });
    console.log('복약 시간', timeStrings);
  },

  // 복약 용량
  setStandard: (standard: MedicationStandard[]) => {
    set({
      standards: standard,
    });
  },

  // 복약 용량 초기화
  resetStandard: () => {
    set({
      standards: [],
    });
  },

  // 스토어 초기화
  initializeAdd: () => {
    set((state) => ({
      // FIXME: nickname도 초기화 시키면 다음 저장이 불가
      nickname: state.nickname,
      name: '',
      image: null,
      repeatStartTime: null,
      repeatEndTime: null,
      isFast: false,
      repeatTimes: [''],
      standards: [
        {
          volume: 0,
          minGlucose: 0,
          maxGlucose: 0,
        },
      ],
    }));
  },
}));
