import { DayRecordTypes } from '@/shared/api/day-record';

export type RecordCategory = keyof DayRecordTypes;
export type RecordType<T extends RecordCategory> = DayRecordTypes[T];

export interface RecordFilter<T> {
  nickname: string;
  date: string;
  category: T;
}
