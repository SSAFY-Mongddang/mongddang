type MealTime = 'breakfast' | 'lunch' | 'dinner' | 'snack';
type MedicationRoute = 'mouth' | 'injection';
type DayOfWeek = '월' | '화' | '수' | '목' | '금' | '토' | '일';

// 기본 기록 인터페이스
interface BaseRecord {
  id: number;
  childId: number;
  imageUrl: string | null;
  isDone: boolean;
  startTime: string;
  endTime: string | null;
}

// 식사 기록
export interface MealRecord extends BaseRecord {
  category: 'meal';
  content: string[];
  mealTime: MealTime;
}

// 운동 기록
export interface ExerciseRecord extends Omit<BaseRecord, 'imageUrl'> {
  category: 'exercise';
}

// 수면 기록
export interface SleepRecord extends Omit<BaseRecord, 'imageUrl'> {
  category: 'sleeping';
}

// 투약 기록
export interface MedicationRecord extends BaseRecord {
  content: LongActingMedicationContent | FastActingMedicationContent;
  category: 'medication';
}

// 혈당 기준 인터페이스
export interface GlucoseStandard {
  volume: number;
  minGlucose: number;
  maxGlucose: number;
}

// 약물 정보 기본 인터페이스
export interface BaseMedicationContent {
  name: string;
  time: string;
  volume: number;
  route: MedicationRoute;
  isRepeat: boolean;
  repeatDays: DayOfWeek[];
  repeatStartTime: string;
  repeatEndTime: string;
  isFast: boolean;
  // ["09:00", "12:00", "18:00"]
  repeatTimes: string[];
}

// 지효성 약물 정보
export interface LongActingMedicationContent extends BaseMedicationContent {
  isFast: false;
  standards: null;
}

// 속효성 약물 정보
export interface FastActingMedicationContent extends BaseMedicationContent {
  isFast: true;
  standards: GlucoseStandard[];
}

export interface DayRecordTypes {
  exercise: ExerciseRecord[];
  meal: MealRecord[];
  medication: MedicationRecord[];
  sleep: SleepRecord[];
}

// 하루 기록
export interface DayRecords {
  day: string;
  records: DayRecordTypes;
}

// export interface DayRecordsResponse {
//   data: DayRecords[];
// }

export interface RecordParams {
  nickname: string;
  date: string;
}
