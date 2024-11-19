export type RecordCategory = 'meal' | 'exercise' | 'sleep' | 'medication';

export interface RecordFilter {
  nickname: string;
  date: string;
  category: RecordCategory;
}

export class DayRecordError extends Error {
  constructor(message: string, public code?: string) {
    super(message);
    this.name = 'DayRecordError';
  }
}