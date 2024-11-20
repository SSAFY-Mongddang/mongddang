/** @jsxImportSource @emotion/react */

import { useNearestBloodSugar } from '@/entities/day-record';
import { DayRecordQueries } from '@/entities/day-record/api';
import { Bloodsugar } from '@/shared/api/blood-sugar';
import { MedicationRecord } from '@/shared/api/day-record';
import { useQuery } from '@tanstack/react-query';
import {
  imgBox,
  itemName,
  leftContainer,
  medicineItem,
  timeText,
} from './style';
import dayjs from 'dayjs';
import { RecordErrorBoundary } from '../error-boundary/record-error-boundary';
import { SLEEP_DEFAULT_IMG } from '@/shared/constans';

interface RenderMedicationProps {
  nickname: string;
  date: string;
  bloodSugarData?: Bloodsugar[];
}

export const RenderMedication = ({
  nickname,
  date,
  bloodSugarData,
}: RenderMedicationProps) => {
  const { data, isError, isLoading, error } = useQuery<MedicationRecord[]>(
    DayRecordQueries.medicationRecordsQuery(
      nickname,
      dayjs(date).format('YYYY-MM-DD')
    )
  );

  const nearestTimeBloodSugar = useNearestBloodSugar(data, bloodSugarData);

  return (
    <RecordErrorBoundary
      isError={isError}
      error={error}
      isLoading={isLoading}
      category="medication"
    >
      <section>
        {data &&
          data.map((item, index) => {
            if (!item.content) return null;
            return (
              <div key={index} css={medicineItem}>
                <div css={leftContainer}>
                  <div css={itemName}>{item.content.name}</div>
                  <div>
                    {nearestTimeBloodSugar[item.startTime].startTime} mg/dl
                  </div>
                  <div>
                    {item.content.volume}단위(U) |{' '}
                    {item.content.route === 'injection' ? '주사' : '경구약'}
                  </div>
                  <div css={timeText}>
                    {dayjs(item.startTime).format('HH:mm')}
                  </div>
                </div>
                <div>
                  <img css={imgBox} src={SLEEP_DEFAULT_IMG} />
                </div>
              </div>
            );
          })}
      </section>
    </RecordErrorBoundary>
  );
};
