/** @jsxImportSource @emotion/react */

import { Notification } from '@/shared/ui/Notification';
import {
  bloodSugarTextCss,
  endEatAlertCss,
  bloodSugarAlertCss,
} from './Alerts.styles';
import { endEating } from '../../api/dietApi';
import { Typography } from '@/shared/ui/Typography';
import { endExercise, startExercise } from '../../api/exerciseApi';
import { endSleep, startSleep } from '../../api/sleepApi';
import { useStopwatchStore } from '../../model/useStopwatchStore';

type AskRoutineAlertProps = {
  handleAlert: (status: string) => void;
  currentRoutine: string;
  changeRoutine: (currentRoutine: string) => void;
  handleBloodSugar: (bloodSugar: number) => void;
};

type BloodSugarProps = {
  currentRoutine: string;
  bloodSugar: number;
  handleAlert: (status: string) => void;
};

// 루틴 시작 여부 질문 알림
export const AskStartRoutineAlert = (props: AskRoutineAlertProps) => {
  const handleStartRoutine = async () => {
    if (props.currentRoutine === '운동 준비') {
      const response = await startExercise();
      if (response.code === 200) {
        props.handleBloodSugar(response.data.bloodSugarLevel);
      }
    } else if (props.currentRoutine === '자는 준비') {
      const response = await startSleep();
      if (response.code === 200) {
        props.handleBloodSugar(response.data.bloodSugarLevel);
      }
    }
  };
  const { startStopwatch } = useStopwatchStore();
  return (
    <Notification
      ment={
        props.currentRoutine === '운동 준비'
          ? '운동 할거야?'
          : props.currentRoutine === '자는 준비'
            ? '이제 잘 거야?'
            : ''
      }
      twoBtn
      type="confirm"
      css={endEatAlertCss}
      children={
        props.currentRoutine === '운동 준비'
          ? ['아니, 안할래', '응, 할래!']
          : props.currentRoutine === '자는 준비'
            ? ['아직 안 잘래', '응, 잘래!']
            : []
      }
      bluehandler={() => {
        startStopwatch();
        props.handleAlert('startRoutine');
        handleStartRoutine();
        {
          if (props.currentRoutine === '운동 준비') {
            props.changeRoutine('운동 중');
          } else if (props.currentRoutine === '자는 준비') {
            props.changeRoutine('자는 중');
          }
        }
      }}
      redHandler={() => {
        props.handleAlert('');
      }}
    />
  );
};

// 루틴 시작 후 혈당 알림
export const StartRoutineAlert = (props: BloodSugarProps) => {
  return (
    <Notification
      ment={
        <div css={bloodSugarTextCss}>
          {props.currentRoutine === '먹는 중' ? (
            <Typography color="dark" size="1" weight={500}>
              🍽️ 맛있게 먹어! 🍽️
            </Typography>
          ) : props.currentRoutine === '운동 중' ? (
            <Typography color="dark" size="1" weight={500}>
              🏀 다치지 않기! 🏀
            </Typography>
          ) : props.currentRoutine === '자는 중' ? (
            <Typography color="dark" size="1" weight={500}>
              🌠 좋은 꿈 꿔! 🌠
            </Typography>
          ) : (
            <></>
          )}
          <Typography color="dark" size="1" weight={600}>
            {`지금 혈당 : ${props.bloodSugar}`}
          </Typography>
        </div>
      }
      type="primary"
      css={bloodSugarAlertCss}
      children={['확인']}
      bluehandler={() => {
        props.handleAlert('');
      }}
    />
  );
};

// 루틴 종료 여부 질문 알림
export const AskEndRoutineAlert = (props: AskRoutineAlertProps) => {
  const handleEndRoutine = async () => {
    if (props.currentRoutine === '먹는 중') {
      const response = await endEating();
      if (response.code === 200) {
        props.handleBloodSugar(response.data.bloodSugarLevel);
      }
    } else if (props.currentRoutine === '운동 중') {
      const response = await endExercise();
      if (response.code === 200) {
        props.handleBloodSugar(response.data.bloodSugarLevel);
      }
    } else if (props.currentRoutine === '자는 중') {
      const response = await endSleep();
      if (response.code === 200) {
        props.handleBloodSugar(response.data.bloodSugarLevel);
      }
    }
  };

  const { endStopwatch } = useStopwatchStore();

  return (
    <Notification
      ment={
        props.currentRoutine === '먹는 중'
          ? '다 먹었어?'
          : props.currentRoutine === '운동 중'
            ? '운동 다 했어?'
            : props.currentRoutine === '자는 중'
              ? '이제 일어날거야?'
              : ''
      }
      twoBtn
      type="confirm"
      css={endEatAlertCss}
      children={
        props.currentRoutine === '먹는 중'
          ? ['아니, 아직', '응, 다 먹었어!']
          : props.currentRoutine === '운동 중'
            ? ['아니, 아직', '응, 다 했어!']
            : props.currentRoutine === '자는 중'
              ? ['아니, 더 잘래', '응, 일어났어!']
              : []
      }
      bluehandler={() => {
        endStopwatch();
        handleEndRoutine();
        {
          if (props.currentRoutine === '먹는 중') {
            props.changeRoutine('먹기 끝');
          } else if (props.currentRoutine === '운동 중') {
            props.changeRoutine('운동 끝');
          } else if (props.currentRoutine === '자는 중') {
            props.changeRoutine('자기 끝');
          }
        }
        props.handleAlert('endRoutine');
      }}
      redHandler={() => {
        props.handleAlert('');
      }}
    />
  );
};

// 루틴 종료 후 혈당 알림
export const EndRoutineAlert = (props: BloodSugarProps) => {
  return (
    <Notification
      ment={
        <div css={bloodSugarTextCss}>
          {props.currentRoutine === '먹기 끝' ? (
            <Typography color="dark" size="1" weight={500}>
              🍽️ 다 먹었다! 🍽️
            </Typography>
          ) : props.currentRoutine === '운동 끝' ? (
            <Typography color="dark" size="1" weight={500}>
              🏀 운동 끝! 완전 멋져 🏀
            </Typography>
          ) : props.currentRoutine === '자기 끝' ? (
            <Typography color="dark" size="1" weight={500}>
              🌅 잘 잤어? 좋은 하루 보내 🌅
            </Typography>
          ) : (
            <></>
          )}

          <Typography color="dark" size="1" weight={600}>
            {`혈당: ${props.bloodSugar}`}
          </Typography>
        </div>
      }
      type="primary"
      css={bloodSugarAlertCss}
      children={['확인']}
      bluehandler={() => {
        props.handleAlert('');
      }}
    />
  );
};
