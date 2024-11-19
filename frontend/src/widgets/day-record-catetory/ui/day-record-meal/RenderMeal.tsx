/** @jsxImportSource @emotion/react */
import { useQuery } from '@tanstack/react-query';
import {
  afterMeal,
  beforeMeal,
  dotContainer,
  dotStyle,
  imgBox,
  mealContainer,
  mealImg,
  mealImgCover,
  mealInnerBox,
  mealItem,
  mealTextBox,
  visibleCover,
} from './styles';
import { DayRecordQueries } from '@/entities/day-record/api';
import { useState } from 'react';
import { IndexedToggleState } from './types';
import { MealRecord } from '@/shared/api/day-record';
import { Bloodsugar } from '@/shared/api/blood-sugar';
import { useNearestBloodSugar } from '@/entities/day-record';
import { RecordErrorBoundary } from '../error-boundary/record-error-boundary';
import { DEFAULT_MEAL_IMG_URL } from '@/shared/constants';
// import DEFAULT_MEAL_IMG_URL from '@/assets/img/fox_and_capybara/mongddang6_meal.png';

interface RenderMealProps {
  nickname: string;
  date: string;
  bloodSugarData?: Bloodsugar[];
}

export const RenderMeal = ({
  nickname,
  date,
  bloodSugarData,
}: RenderMealProps) => {
  const [isTap, setIsTap] = useState<IndexedToggleState>({});
  const {
    data: mealData,
    isLoading,
    isError,
    error,
  } = useQuery<MealRecord[]>(DayRecordQueries.mealRecordsQuery(nickname, date));

  const nearestTimeBloodSugar = useNearestBloodSugar(mealData, bloodSugarData);

  // if (isError) {
  // console.log('Error in RenderMeal');
  // console.log('Error in RenderMeal');
  // console.log(JSON.stringify(error.message));
  // console.log('Error in RenderMeal');
  // console.log('Error in RenderMeal');
  // throw new Error('Error in RenderMeal');
  // }

  // if (isLoading) return <Loading />;

  return (
    <RecordErrorBoundary
      isError={isError}
      error={error}
      isLoading={isLoading}
      category="meal"
    >
      <section css={mealContainer}>
        {mealData?.map((item, index) => (
          <div key={index} css={mealItem}>
            <div
              css={imgBox}
              onClick={() =>
                setIsTap((prev) => ({ ...prev, [index]: !prev[index] }))
              }
            >
              <img css={mealImg} src={DEFAULT_MEAL_IMG_URL} />
              <div css={[mealImgCover, isTap[index] && visibleCover]}>
                <span>{item.content.join(', ')}</span>
              </div>
              <div css={dotContainer}>
                <span css={[dotStyle(isTap[index])]} />
              </div>
            </div>
            <div css={mealTextBox} className="meal">
              {bloodSugarData && nearestTimeBloodSugar[item.startTime] && (
                <div css={mealInnerBox}>
                  <div css={beforeMeal}>
                    <span>
                      식사 전 혈당
                      <br />:{nearestTimeBloodSugar[item.startTime].startTime}
                      mg/dl
                    </span>
                  </div>
                  <div css={afterMeal}>
                    <span>
                      2시간 후 혈당
                      <br />:
                      {nearestTimeBloodSugar[item.startTime].after2Hour !==
                      '없음'
                        ? nearestTimeBloodSugar[item.startTime].after2Hour +
                          'mg/dl'
                        : '기록 없음'}
                    </span>
                  </div>
                </div>
              )}
            </div>
          </div>
        ))}
      </section>
    </RecordErrorBoundary>
  );
};
