export type BloodsugarStatus = 'normal' | 'high' | 'low';
export interface Bloodsugar {
  id: number;
  bloodSugarLevel: number;
  measurementTime: string;
  content: string | null;
  status: BloodsugarStatus;
  notification: boolean;
}

export interface BloodsugarResponse {
  code: number;
  message: string;
  data: {
    bloodSugar: Bloodsugar[];
  };
}
