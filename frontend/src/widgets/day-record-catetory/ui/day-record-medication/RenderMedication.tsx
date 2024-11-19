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
// import { DEFAULT_HAPPY_IMG_URL } from '@/shared/constants';
import DEFAULT_HAPPY_IMG_URL from '@/assets/img/fox_and_capybara/mongddang6_sleep.png';

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
    DayRecordQueries.medicationRecordsQuery(nickname, date)
  );

  const nearestTimeBloodSugar = useNearestBloodSugar(data, bloodSugarData);

  // if (isError) {
  //   // console.log('Error in RenderMedication');
  //   // throw new Error('Error in RenderMedication');
  //   if (error instanceof DayRecordError) {
  //     switch (error.code) {
  //       case 'NO_RECORDS':
  //         return <div>해당 날짜의 기록이 없습니다.</div>;
  //       case 'NO_CATEGORY_RECORDS':
  //         return <div>해당 날짜의 식사 기록이 없습니다.</div>;
  //       case 'INVALID_STRUCTURE':
  //         return <div>데이터 구조가 올바르지 않습니다.</div>;
  //       default:
  //         return <div>오류가 발생했습니다. 잠시 후 다시 시도해주세요.</div>;
  //     }
  //   }
  //   return <div>알 수 없는 오류가 발생했습니다.</div>;
  // }

  // if (isLoading) return <div>Loading...</div>;

  // 빌드 에러 방지용
  console.log('Prevent Error log', nearestTimeBloodSugar);

  return (
    <RecordErrorBoundary
      isError={isError}
      error={error}
      isLoading={isLoading}
      category="medication"
    >
      <section>
        {data &&
          data.map((item, index) => (
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
                <img css={imgBox} src={DEFAULT_HAPPY_IMG_URL} />
              </div>
            </div>
          ))}
      </section>
    </RecordErrorBoundary>
  );
};
