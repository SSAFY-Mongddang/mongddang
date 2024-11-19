/** @jsxImportSource @emotion/react */

import { mainIcons } from '@/pages/MainPage/constants/iconsData';
import { Notification } from '../Notification';
import { Typography } from '../Typography';
import { container, imgCss, textCss } from './PushNotification.styles';
import { usePushNotificationStore } from '@/shared/model/usePushNotificationStore';
import { takeMedicine } from '@/shared/api/push-notification/takeMedicineApi';

export const PushNotification = () => {
  const { title, message, removePushNotification, category } =
    usePushNotificationStore();

  const checkTakeMedicine = () => {
    takeMedicine();
    removePushNotification();
  };

  return (
    <>
      {message &&
        (category === 'game' ? (
          <Notification
            bluehandler={() => {
              removePushNotification();
            }}
            ment={
              <div css={textCss}>
                <Typography color="dark" size="1.25" weight={600}>
                  {title}
                </Typography>
                <Typography color="dark" size="1" weight={500}>
                  {message}
                </Typography>
              </div>
            }
            type="confirm"
            css={container}
          />
        ) : category === 'blood_sugar' ? (
          <Notification
            bluehandler={() => {
              removePushNotification();
            }}
            ment={
              <div css={textCss}>
                <img
                  src={mainIcons.alertMongddang}
                  alt="medication"
                  css={imgCss}
                />
                <Typography color="dark" size="1.25" weight={600}>
                  {title}
                </Typography>
                <Typography color="dark" size="1" weight={500}>
                  {message}
                </Typography>
              </div>
            }
            type="alert"
            css={container}
          />
        ) : category === 'medication' ? (
          <Notification
            bluehandler={() => {
              checkTakeMedicine();
            }}
            ment={
              <div css={textCss}>
                <img src={mainIcons.medication} alt="medication" css={imgCss} />
                <Typography color="dark" size="1.25" weight={600}>
                  {title}
                </Typography>
                <Typography color="dark" size="1" weight={500}>
                  {message}
                </Typography>
              </div>
            }
            type="primary"
            css={container}
            children={['주사 잘 맞았어!']}
          />
        ) : null)}
    </>
  );
};
