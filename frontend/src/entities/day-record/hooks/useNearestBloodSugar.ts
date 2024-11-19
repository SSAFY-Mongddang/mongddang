import { useMemo } from 'react';
import { DayRecordTypes } from '@/shared/api/day-record';
import { Bloodsugar } from '@/shared/api/blood-sugar';
import { findNearestTimeBloodSugar } from '../lib/findNearestBloodSugar';
import { RecordCategory } from '../api';
import dayjs from 'dayjs';

interface BloodSugarValues {
  startTime: number;
  endTime: number | null;
  after2Hour: number | string | null;
}

export const useNearestBloodSugar = <T extends RecordCategory>(
  recordData: DayRecordTypes[T] | undefined,
  bloodSugarData: Bloodsugar[] | undefined
) => {
  const nearestBloodSugarValues = useMemo(() => {
    if (!recordData || !bloodSugarData) return {};

    return recordData.reduce(
      (acc, record) => {
        const startTimeVal = findNearestTimeBloodSugar(
          bloodSugarData,
          record.startTime
        );

        const endTimeVal = record.endTime
          ? findNearestTimeBloodSugar(bloodSugarData, record.endTime)
          : null;

        const after2HVal =
          dayjs(record.endTime).add(2, 'hour') <= dayjs()
            ? findNearestTimeBloodSugar(
                bloodSugarData,
                dayjs(record.endTime).add(2, 'hour').toString()
              )
            : '없음';

        return {
          ...acc,
          [record.startTime]: {
            startTime: startTimeVal,
            endTime: endTimeVal,
            after2Hour: after2HVal,
          },
        };
      },
      {} as Record<string, BloodSugarValues>
    );
  }, [recordData, bloodSugarData]);

  return nearestBloodSugarValues;
};
