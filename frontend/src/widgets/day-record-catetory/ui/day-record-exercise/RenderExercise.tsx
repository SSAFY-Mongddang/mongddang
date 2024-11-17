/** @jsxImportSource @emotion/react */

import { useNearestBloodSugar } from '@/entities/day-record';
import { DayRecordQueries } from '@/entities/day-record/api';
import { Bloodsugar } from '@/shared/api/blood-sugar';
import { ExerciseRecord } from '@/shared/api/day-record';
import { useQuery } from '@tanstack/react-query';
import { exerciseItem, textBox } from './style';
import { calcTimeDuration } from '../../lib/calcTimeDuration';

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
  const { data, isError, isLoading } = useQuery<ExerciseRecord[]>(
    DayRecordQueries.exerciseRecordsQuery(nickname, date)
  );

  const nearestTimeBloodSugar = useNearestBloodSugar(data, bloodSugarData);

  if (isError) {
    console.log('Error in RenderExercise');
    throw new Error('Error in RenderExercise');
  }
  if (isLoading) return <div>Loading...</div>;

  return (
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
                    ? nearestTimeBloodSugar[item.startTime].after2Hour + 'mg/dl'
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
  );
};
