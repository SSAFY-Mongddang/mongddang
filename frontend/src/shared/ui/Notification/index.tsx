/** @jsxImportSource @emotion/react */
import { NotificationProps } from './Notification.types';
import { Backdrop } from '../Backdrop';
import {
  backdropCss,
  base,
  btnContainerCss,
  btnCss,
} from './Notification.styles';
import { Typography } from '../Typography';
import { Button } from '../Button';

export const Notification = ({
  ment,
  type,
  twoBtn,
  bluehandler = () => {},
  redHandler = () => {},
  children = ['취소하기', '확인하기'],
  width = 20.625,
  height = 6.25,
  ...props
}: NotificationProps) => {
  return (
    <Backdrop css={backdropCss}>
      <div css={base(type, width, height)} {...props}>
        <Typography
          style={{ display: 'flex', justifyContent: 'center' }}
          color="dark"
          size={'1'}
          weight={500}
        >
          {ment}
        </Typography>
        {twoBtn ? (
          <div css={btnContainerCss}>
            <Button handler={redHandler} color={'danger'}>
              <Typography color="light" weight={600} size={'0.75'}>
                {children[0]}
              </Typography>
            </Button>
            <Button handler={bluehandler} color="primary">
              <Typography color="light" weight={600} size={'0.75'}>
                {children[1]}
              </Typography>
            </Button>
          </div>
        ) : (
          <Button
            css={btnCss}
            handler={bluehandler}
            color={
              type === 'confirm' || type === 'primary' ? 'primary' : 'danger'
            }
          >
            <Typography color="light" weight={600} size={'0.75'}>
              확인
            </Typography>
          </Button>
        )}
      </div>
    </Backdrop>
  );
};
