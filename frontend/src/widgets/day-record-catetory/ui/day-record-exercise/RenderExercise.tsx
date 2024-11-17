/** @jsxImportSource @emotion/react */

import { useNearestBloodSugar } from '@/entities/day-record';
import { DayRecordQueries } from '@/entities/day-record/api';
import { Bloodsugar } from '@/shared/api/blood-sugar';
import { ExerciseRecord } from '@/shared/api/day-record';
import { useQuery } from '@tanstack/react-query';
import { exerciseItem, textBox } from './style';
import { calcTimeDuration } from '../../lib/calcTimeDuration';
import { RecordErrorBoundary } from '../error-boundary/record-error-boundary';

interface RenderExerciseProps {
  nickname: string;
  date: string;
  bloodSugarData?: Bloodsugar[];
}

export const RenderExercise = ({
  nickname,
  date,
  bloodSugarData,
}: RenderExerciseProps) => {
  const { data, isError, isLoading, error } = useQuery<ExerciseRecord[]>(
    DayRecordQueries.exerciseRecordsQuery(nickname, date)
  );

  const nearestTimeBloodSugar = useNearestBloodSugar(data, bloodSugarData);

  // if (isError) {
  //   // console.log('Error in RenderExercise');
  //   // throw new Error('Error in RenderExercise');
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

  return (
    <RecordErrorBoundary
      isError={isError}
      error={error}
      isLoading={isLoading}
      category="exercise"
    >
      <section>
        {data &&
          data.map((item, index) => (
            <div key={index} css={exerciseItem}>
              <div css={textBox}>
                <div>
                  <span>
                    운동 전 혈당 :{' '}
                    {nearestTimeBloodSugar[item.startTime].startTime} mg/dl
                  </span>
                </div>
                <div>
                  <span>
                    2시간 후 혈당 :{' '}
                    {nearestTimeBloodSugar[item.startTime].after2Hour !== '없음'
                      ? nearestTimeBloodSugar[item.startTime].after2Hour +
                        'mg/dl'
                      : '없음'}
                  </span>
                </div>
              </div>
              <div>
                <span>
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
