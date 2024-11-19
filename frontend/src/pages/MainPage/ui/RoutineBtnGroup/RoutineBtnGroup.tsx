/** @jsxImportSource @emotion/react */

import { Icon } from '@/shared/ui/Icon';
import {
  container,
  dietCss,
  endBtnCss,
  routineGroupCss,
  timerCss,
} from './RoutineBtnGroup.styles';
import { Typography } from '@/shared/ui/Typography';
import { Button } from '@/shared/ui/Button';
import { useStopwatchStore } from '../../model/useStopwatchStore';
import { mainIcons } from '../../constants/iconsData';

type RoutineBtnGroupProps = {
  changeRoutine: (currentRoutine: string) => void;
  handleDietModal: () => void;
  currentRoutine: string;
  handleAlert: (status: string) => void;
};

const RoutineBtnGroup = (props: RoutineBtnGroupProps) => {
  const { time, finalTime } = useStopwatchStore();
  console.log('타임', time)
  console.log('finalTime', finalTime)
  return (
    <div css={container}>
      {props.currentRoutine === '먹는 중' ||
      props.currentRoutine === '운동 중' ||
      props.currentRoutine === '자는 중' ? (
        <div css={dietCss}>
          <div css={timerCss}>
            <Typography color="dark" size="1" weight={500}>
              {props.currentRoutine}
            </Typography>
            <Typography color="dark" size="1" weight={500}>
              {finalTime}
            </Typography>
          </div>
          <Button
            color="primary"
            fontSize="1"
            isShadow
            variant="contained"
            handler={() => {
              props.handleAlert('askEndRoutine');
            }}
            css={endBtnCss}
          >
            끝내기
          </Button>
        </div>
      ) : (
        <div css={routineGroupCss}>
          <Icon
            size={2.5}
            onClick={() => {
              props.handleDietModal();
            }}
          >
            <img alt="icon-0" src={mainIcons.meal} />
          </Icon>
          <Icon
            size={2.5}
            onClick={() => {
              props.changeRoutine('운동 준비');
              props.handleAlert('askStartRoutine');
            }}
          >
            <img alt="icon-1" src={mainIcons.exercise} />
          </Icon>
          <Icon
            size={2.5}
            onClick={() => {
              props.changeRoutine('자는 준비');
              props.handleAlert('askStartRoutine');
            }}
          >
            <img alt="icon-2" src={mainIcons.sleep} />
          </Icon>
        </div>
      )}
    </div>
  );
};

export default RoutineBtnGroup;
