/** @jsxImportSource @emotion/react */

// import { takeMedicine } from '@/shared/api/push-notification/takeMedicineApi';
import { Notification } from '../Notification';
import { Typography } from '../Typography';
import { container, textCss } from './PushNotification.styles';
import { usePushNotificationStore } from '@/shared/model/usePushNotificationStore';
// import { mainIcons } from '@/pages/MainPage/constants/iconsData';

export const PushNotification = () => {
  const { title, message, removePushNotification } = usePushNotificationStore();
  return (
    <>
      {message && (
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
      )}
    </>
  );
};

// const medicineNotification = () => {
//   const { title, message, removePushNotification } = usePushNotificationStore();
//   const checkTakeMedicine = () => {
//     takeMedicine();
//     removePushNotification();
//   };
//   return (
//     <Notification
//       bluehandler={() => {
//         checkTakeMedicine();
//       }}
//       ment={
//         <div css={textCss}>
//           <img src={mainIcons.medication} alt="medication" css={imgCss} />
//           <Typography color="dark" size="1.25" weight={600}>
//             {title}
//           </Typography>
//           <Typography color="dark" size="1" weight={500}>
//             {message}
//           </Typography>
//         </div>
//       }
//       type="primary"
//       css={container}
//       children={['주사 잘 맞았어!']}
//     />
//   );
// };

// const bloodSugarNotification = () => {
//   const { title, message, removePushNotification } = usePushNotificationStore();
//   return (
//     <Notification
//       bluehandler={() => {
//         removePushNotification();
//       }}
//       ment={
//         <div css={textCss}>
//           <img src={mainIcons.alertMongddang} alt="medication" css={imgCss} />
//           <Typography color="dark" size="1.25" weight={600}>
//             {title}
//           </Typography>
//           <Typography color="dark" size="1" weight={500}>
//             {message}
//           </Typography>
//         </div>
//       }
//       type="alert"
//       css={container}
//     />
//   );
// };
