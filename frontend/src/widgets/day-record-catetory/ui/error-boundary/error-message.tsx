/** @jsxImportSource @emotion/react */

import { RecordCategory } from '@/entities/day-record/api';
import { DayRecordError } from '@/entities/day-record/model';
import meal_mongddang from '@/assets/img/main_mongddang/meal_mongddang.png';
import sleep_mongddang from '@/assets/img/main_mongddang/sleep_mongddang.png';
import exercise_mongddang from '@/assets/img/main_mongddang/exercise_mongddang.png';
import high_mongddang from '@/assets/img/main_mongddang/high_mongddang.png';
import { img, imgBox } from './style';

interface ErrorMessageProps {
  error: Error | null;
  category: RecordCategory;
}

interface CategoryFeedbackItem {
  img: string;
  category: string;
  message: string;
}

const categoryFeedback: Record<RecordCategory, CategoryFeedbackItem> = {
  meal: {
    img: meal_mongddang,
    category: '식사',
    message: '몽땅이가 밥을 짓고 있어요!',
  },
  medication: {
    img: high_mongddang,
    category: '투약',
    message: '몽땅이는 별이 필요해요.',
  },
  exercise: {
    img: exercise_mongddang,
    category: '운동',
    message: '몽땅이는 헬창이 될 거예요!',
  },
  sleep: {
    img: sleep_mongddang,
    category: '수면',
    message: '몽땅이가 수면핑의 마법에 걸려버렸어요.',
  },
};

export const ErrorMessage = ({ error, category }: ErrorMessageProps) => {
  if (error instanceof DayRecordError) {
    switch (error.code) {
      case 'NO_RECORDS':
        return (
          <div css={imgBox}>
            <img css={img} src={`${categoryFeedback[category].img}`} />
            <div>{categoryFeedback[category].message}</div>
          </div>
        );
      case 'NO_CATEGORY_RECORDS':
        return (
          <div css={imgBox}>
            <img css={img} src={`${categoryFeedback[category].img}`} />
            <div>{categoryFeedback[category].message}</div>
          </div>
        );
      case 'INVALID_STRUCTURE':
        return <div>데이터 구조가 올바르지 않습니다.</div>;
      default:
        return <div>오류가 발생했습니다. 잠시 후 다시 시도해주세요.</div>;
    }
  }

  return <div>알 수 없는 오류가 발생했습니다.</div>;
};
