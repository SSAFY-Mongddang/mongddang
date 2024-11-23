import { api } from '@/shared/api/interceptors';

interface MealPlan {
  schoolName: string;
  month: string;
}

export const getMealPlan = async ({ schoolName, month }: MealPlan) => {
  console.log(schoolName, month);
  return await api({
    method: 'POST',
    url: '/api/meal-data/get',
    data: {
      schoolName: '인동초등학교',
      nickname: '세희',
      mealTime: 'lunch',
      month: '202411',
    },
  })
    .then((res) => {
      console.log('급식 조회', res);
      return res.data;
    })
    .catch((err) => {
      console.log(err);
      return err;
    });
};

export interface DailyMeal {
  date: string;
  meal: string[];
  mealTime: string;
  school: string;
}

export interface SaveMealPlan {
  nickname: string;
  mealList: DailyMeal[];
}

export const saveMealPlan = async ({ nickname, mealList }: SaveMealPlan) => {
  console.log('nickname', nickname);
  console.log('mealList', mealList);
  return await api({
    method: 'POST',
    url: '/api/meal-data/save',
    data: { nickname: '세희', mealList },
  })
    .then((res) => {
      console.log('급식 저장', res);
      return res.data;
    })
    .catch((err) => {
      console.log(err);
      return err;
    });
};
