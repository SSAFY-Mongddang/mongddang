export type MedicationStandard = {
  volume: number;
  minGlucose: number | null;
  maxGlucose: number | null;
};

export type MedicationItemType = {
  nickname: string;
  name: string;
  image: File | null;
  repeatStartTime: Date | null;
  repeatEndTime: Date | null;
  isFast: boolean;
  repeatTimes: string[];
  standards: MedicationStandard[] | null;
};
