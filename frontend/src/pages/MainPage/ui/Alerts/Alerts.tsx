/** @jsxImportSource @emotion/react */

import { Notification } from '@/shared/ui/Notification';
import {
  bloodSugarTextCss,
  endEatAlertCss,
  bloodSugarAlertCss,
} from './Alerts.styles';
import { endEating } from '../../api/dietApi';
import { Typography } from '@/shared/ui/Typography';

type AskRoutineAlertProps = {
  accessToken: string | null;
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
  return (
    <Notification
      ment={props.currentRoutine === '운동 준비' ? '운동 할거야?' : ''}
      twoBtn
      type="confirm"
      css={endEatAlertCss}
      children={
        props.currentRoutine === '운동 준비'
          ? ['아니, 안할래', '응, 할래!']
          : []
      }
      bluehandler={() => {
        props.handleAlert('startRoutine');
        props.changeRoutine('운동 중');
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
    const response = await endEating(props.accessToken);
    if (response.code === 200) {
      props.handleBloodSugar(response.data.bloodSugarLevel);
    }
  };

  return (
    <Notification
      ment={props.currentRoutine === '먹는 중' ? '다 먹었어?' : ''}
      twoBtn
      type="confirm"
      css={endEatAlertCss}
      children={
        props.currentRoutine === '먹는 중'
          ? ['아니, 아직', '응, 다 먹었어!']
          : []
      }
      bluehandler={() => {
        handleEndRoutine();
        props.handleAlert('endRoutine');
        props.changeRoutine('먹기 끝');
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
