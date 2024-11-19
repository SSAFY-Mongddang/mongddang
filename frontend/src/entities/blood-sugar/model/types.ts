import { BloodsugarStatus } from '@/shared/api/blood-sugar/blood-sugar.type';

export interface BloodsugarFilter {
  status?: BloodsugarStatus;
  notification?: boolean;
  date?: string;
}
