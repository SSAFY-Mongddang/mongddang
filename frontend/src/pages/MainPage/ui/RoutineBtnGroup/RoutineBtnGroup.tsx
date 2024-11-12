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

type RoutineBtnGroupProps = {
  changeRoutine: (currentRoutine: string) => void;
  handleDietModal: () => void;
  currentRoutine: string;
  handleAlert: (status: string) => void;
};

const RoutineBtnGroup = (props: RoutineBtnGroupProps) => {
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
            <Typography color="dark" size="1" weight={400}>
              00:01
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
            <img alt="icon-0" src="/img/%EB%A7%90%EB%9E%911.png" />
          </Icon>
          <Icon
            size={2.5}
            onClick={() => {
              props.changeRoutine('운동 준비');
              props.handleAlert('askStartRoutine');
            }}
          >
            <img alt="icon-1" src="/img/%EB%A7%90%EB%9E%912.png" />
          </Icon>
          <Icon
            size={2.5}
            onClick={() => {
              props.changeRoutine('자는 준비');
              props.handleAlert('askStartRoutine');
            }}
          >
            <img alt="icon-2" src="/img/%EB%A7%90%EB%9E%913.png" />
          </Icon>
        </div>
      )}
    </div>
  );
};

export default RoutineBtnGroup;
