/** @jsxImportSource @emotion/react */

import { DayRecordQueries } from '@/entities/day-record/api';
import { Bloodsugar } from '@/shared/api/blood-sugar';
import { SleepRecord } from '@/shared/api/day-record';
import { useQuery } from '@tanstack/react-query';
import { sleepItem, textBox } from './style';
import { useNearestBloodSugar } from '@/entities/day-record';
import { calcTimeDuration } from '../../lib/calcTimeDuration';
import { RecordErrorBoundary } from '../error-boundary/record-error-boundary';

interface RenderSleepProps {
  nickname: string;
  date: string;
  bloodSugarData?: Bloodsugar[];
}

export const RenderSleep = ({
  nickname,
  date,
  bloodSugarData,
}: RenderSleepProps) => {
  const { data, isError, isLoading, error } = useQuery<SleepRecord[]>(
    DayRecordQueries.sleepRecordsQuery(nickname, date)
  );

  const nearestTimeBloodSugar = useNearestBloodSugar(data, bloodSugarData);

  // if (isError) {
  //   // console.log('Error in RenderSleep');
  //   // throw new Error('Error in RenderSleep');
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
  console.log('Prevent Error log', bloodSugarData, data);

  return (
    <RecordErrorBoundary
      isError={isError}
      error={error}
      isLoading={isLoading}
      category="sleep"
    >
      <section>
        {data &&
          data.map((item, index) => (
            <div key={index} css={sleepItem}>
              <div css={textBox}>
                <div>
                  <span>
                    잠자기 전 혈당 :{' '}
                    {nearestTimeBloodSugar[item.startTime].startTime} mg/dl
                  </span>
                </div>
                <div>
                  <span>
                    깨어난 후 혈당 :{' '}
                    {nearestTimeBloodSugar[item.startTime].endTime !== null
                      ? nearestTimeBloodSugar[item.startTime].endTime + 'mg/dl'
                      : '기록 없음'}
                  </span>
                </div>
              </div>
              <div>
                <span>
                  수면시간{' '}
                  {calcTimeDuration(item.startTime, item.endTime ?? '')}
                  {item.endTime !== null ? '분' : ''}
                </span>
              </div>
            </div>
          ))}
      </section>
    </RecordErrorBoundary>
  );
};
