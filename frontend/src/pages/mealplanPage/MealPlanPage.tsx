/** @jsxImportSource @emotion/react */

import { TopBar } from '@/shared/ui/TopBar';
import { topbarCss } from '../medication/Medication.styles';
import { useNavigate } from 'react-router-dom';
import { useUserStore } from '@/entities/user/model';
import {
  inputTypo,
  mealPlanFormBox,
  mealPlanFormContainer,
} from './MealPlanPage.styles';
import { Typography } from '@/shared/ui/Typography';
import { TextField } from '@/shared/ui/TextField';
import { Button } from '@/shared/ui/Button';
import { useState } from 'react';
import { Notification } from '@/shared/ui/Notification';
import { DailyMeal, getMealPlan, saveMealPlan } from './api/mealPlanApi';

const MealPlanPage = () => {
  const navigate = useNavigate();

  const [schoolNameInput, setSchoolNameInput] = useState('');
  const [monthInput, setMonthInput] = useState('');
  const [modalState, setModalState] = useState(0);

  const [mealList, setMealList] = useState<DailyMeal[]>([]);

  console.log(schoolNameInput);
  console.log(monthInput);
  console.log(mealList);

  const userRole = useUserStore((state) => state.user?.role);

  const handleClickSaveConfirm = async () => {
    console.log('저장 확인');
    setSchoolNameInput('');
    setMonthInput('');
    setModalState(2);
    await saveMealPlan({ nickname: '세희', mealList });
    // navigate('/menu');
  };

  const handleClickSave = async () => {
    console.log('저장');
    console.log(schoolNameInput, monthInput);
    const mealPlan = await getMealPlan({
      schoolName: schoolNameInput,
      month: monthInput,
    });
    console.log('mealPlan', mealPlan);
    setMealList(mealPlan.data);
    setModalState(1);
  };

  const handleClickCancel = () => {
    console.log('취소');
    setModalState(0);
  };

  const handleClickDone = () => {
    console.log('완료');
    setModalState(0);
  };

  return (
    <div>
      <TopBar
        type="iconpage"
        css={topbarCss}
        style={{ marginBottom: '1rem' }}
        iconHandler={() => {
          navigate(
            userRole === 'child'
              ? '/menu'
              : userRole === 'protector'
                ? '/protector-main'
                : ''
          );
        }}
      >
        급식표 등록
      </TopBar>
      <div style={{ padding: '0.5rem' }}>
        <div css={mealPlanFormContainer}>
          <Typography color="dark" size="1.25" weight={600}>
            급식표 등록
          </Typography>
          <Typography
            color="dark"
            size="1"
            weight={500}
            style={{ marginBottom: '3rem' }}
          >
            학교와 몇 월인지 알려주면 급식표가 등록됩니다
          </Typography>
          <div css={mealPlanFormBox}>
            <Typography
              color="dark"
              size="1.25"
              weight={600}
              style={{ marginBottom: '0.5rem' }}
            >
              학교 이름
            </Typography>
            <TextField
              label=""
              variant="standard"
              placeholder="어느 학교의 급식표인가요?"
              style={{ marginBottom: '2rem' }}
              css={inputTypo}
              onChange={(e) => setSchoolNameInput(e.target.value)}
              value={schoolNameInput}
            />
            <Typography
              color="dark"
              size="1.25"
              weight={600}
              style={{ marginBottom: '0.5rem' }}
            >
              등록할 월
            </Typography>
            <TextField
              label=""
              variant="standard"
              placeholder="몇 월 급식표인가요?"
              style={{ marginBottom: '1rem' }}
              css={inputTypo}
              onChange={(e) => setMonthInput(e.target.value)}
              value={monthInput}
            />
            <Button
              color="primary"
              fontSize="1.25"
              variant="contained"
              handler={handleClickSave}
            >
              저장
            </Button>
          </div>
        </div>
      </div>
      <>
        {modalState === 0 ? null : modalState === 1 ? (
          <Notification
            height={25}
            redHandler={() => {
              handleClickCancel();
            }}
            bluehandler={() => {
              handleClickSaveConfirm();
            }}
            twoBtn
            ment={<DayMeal mealList={mealList} />}
            type="confirm"
          />
        ) : modalState === 2 ? (
          <Notification
            bluehandler={() => {
              handleClickDone();
            }}
            ment="급식표가 등록 됐습니다!"
            type="confirm"
          />
        ) : null}
      </>
    </div>
  );
};

const DayMeal = ({ mealList }: { mealList: DailyMeal[] }) => {
  return (
    <div
      style={{
        display: 'flex',
        flexDirection: 'column',
        textAlign: 'center',
      }}
    >
      <Typography
        color="dark"
        size="1.25"
        weight={600}
        style={{ marginBottom: '0.5rem' }}
      >
        이 급식표가 맞습니까?
      </Typography>
      <Typography
        color="dark"
        size="1"
        weight={400}
        style={{ marginBottom: '0.5rem' }}
      >
        {mealList?.[0].date}
      </Typography>
      <Typography
        color="dark"
        size="1.25"
        weight={600}
        style={{ marginBottom: '0.5rem' }}
      >
        {mealList?.[0].mealTime}
      </Typography>
      {mealList?.[0].meal.map((meal, idx) => (
        <Typography
          key={idx}
          color="dark"
          size="1"
          weight={500}
          style={{
            display: 'flex',
            flexDirection: 'column',
            marginBottom: '0.5rem',
          }}
        >
          {meal}
        </Typography>
      ))}
    </div>
  );
};

export default MealPlanPage;
