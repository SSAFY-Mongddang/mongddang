/** @jsxImportSource @emotion/react */

import { TopBar } from '@/shared/ui/TopBar';
import { topbarCss } from '../medication/Medication.styles';
import { useNavigate } from 'react-router-dom';
import { useUserStore } from '@/entities/user/model';
import {
  container,
  imgContainerCss,
  imgCss,
  inputTypo,
  mealPlanFormBox,
  mealPlanFormContainer,
} from './MealPlanPage.styles';
import { Typography } from '@/shared/ui/Typography';
import { TextField } from '@/shared/ui/TextField';
import { Button } from '@/shared/ui/Button';
import { useEffect, useState } from 'react';
import { Notification } from '@/shared/ui/Notification';
import { DailyMeal, getMealPlan, saveMealPlan } from './api/mealPlanApi';
import MenuMongddang from '@/assets/img/main_mongddang/meal_mongddang-resized.png';
import { getLastMealData } from '../MainPage/api/dietApi';

const MealPlanPage = () => {
  const navigate = useNavigate();

  const [schoolNameInput, setSchoolNameInput] = useState('');
  const [monthInput, setMonthInput] = useState('');
  const [modalState, setModalState] = useState(0);

  const [mealList, setMealList] = useState<DailyMeal[]>([]);

  const [lastSchoolName, setLastSchoolName] = useState('');
  const [lastMonth, setLastMonth] = useState('');
  const [lastYear, setLastYear] = useState('');

  const nickname = useUserStore((state) => state.user?.nickname);

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

  useEffect(() => {
    const setLastMealData = async (nickname: string) => {
      const response = await getLastMealData(nickname);
      console.log('response', response);
      if (response) {
        setLastSchoolName(response.data.schoolName);
        setLastYear(response.data.year);
        setLastMonth(response.data.month);
      }
    };

    setLastMealData(nickname || '');
  }, []);

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
        <div
          css={container}
          style={{ display: 'flex', flexDirection: 'column' }}
        >
          <Typography
            color="dark"
            size="1"
            weight={600}
            style={{ lineHeight: '0.75rem' }}
          >
            {lastSchoolName ? (
              <>
                <p>
                  최근에{' '}
                  <span style={{ color: '#00b0ff' }}>{lastSchoolName}</span>
                </p>
                <p>
                  <span style={{ color: '#00b0ff' }}>
                    {lastYear}년 {lastMonth}월
                  </span>
                </p>
                <p>급식표를 등록했어요!</p>
              </>
            ) : (
              <>
                <p> </p>
                <p>아직 등록된 급식표가 없어요.</p>
                <p> </p>
              </>
            )}
          </Typography>
        </div>
        <div css={imgContainerCss}>
          <img src={MenuMongddang} alt="information mongddang" css={imgCss} />
        </div>

        <div css={mealPlanFormContainer}>
          <Typography
            color="dark"
            size="1.25"
            weight={600}
            style={{ marginBottom: '0.5rem' }}
          >
            급식표 등록
          </Typography>
          <Typography
            color="dark"
            size="1"
            weight={400}
            style={{ marginBottom: '1rem' }}
          >
            학교와 몇 월인지 알려주면 급식표가 등록됩니다
          </Typography>
          <div css={mealPlanFormBox}>
            <Typography
              color="dark"
              size="1"
              weight={600}
              style={{ marginBottom: '0.5rem' }}
            >
              학교 이름
            </Typography>
            <TextField
              label=""
              variant="standard"
              placeholder="어느 학교의 급식표인가요?"
              style={{ marginBottom: '1rem' }}
              css={inputTypo}
              onChange={(e) => setSchoolNameInput(e.target.value)}
              value={schoolNameInput}
            />
            <Typography
              color="dark"
              size="1"
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
