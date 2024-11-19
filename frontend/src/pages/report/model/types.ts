export interface GlucoseData {
  measurementDate: string;
  glucose: number;
}

export interface WeeklyAverage {
  date: string;
  average: number;
}

export interface BloodSugarStore {
  weeklyData: WeeklyAverage[] | null;
  setWeeklyData: (data: GlucoseData[]) => void;
}
