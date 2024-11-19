/** @jsxImportSource @emotion/react */

import { Typography } from '@/shared/ui/Typography';
import {
  container,
  imgCss,
  noNotificationCss,
  notificationItemCss,
  notificationListCss,
  textCss,
  xCss,
} from './NotificationContent.styles';
import { useCallback } from 'react';
import {
  useNotificationQuery,
  useNotificationReadMutation,
} from '../../model/useNotificationQuery';
import Loading from '@/shared/ui/Loading';
import { mainIcons } from '../../constants/iconsData';

type NotificationItem = {
  notificationId: number;
  category: string;
  content: string;
  createdAt: Date;
  isNew: boolean;
};

const NotificationContent = () => {
  const { data: notifications, isLoading } = useNotificationQuery();
  const { mutate } = useNotificationReadMutation();
  const onDelete = useCallback(
    (notificationId: number) => {
      mutate(notificationId);
    },
    [mutate]
  );

  if (isLoading) return <Loading />;

  return (
    <div css={container}>
      <div css={notificationListCss}>
        {notifications ? (
          notifications.map((notification: NotificationItem) => {
            return (
              notification.isNew && (
                <div
                  css={notificationItemCss}
                  onClick={() => {
                    onDelete(notification.notificationId);
                  }}
                  key={`notification-${notification.notificationId}`}
                >
                  <Typography color="dark" size="0.75" weight={400} css={xCss}>
                    x
                  </Typography>
                  <Typography color="dark" size="1" weight={500} css={textCss}>
                    {notification.content}
                  </Typography>
                </div>
              )
            );
          })
        ) : (
          <div css={noNotificationCss}>
            <img src={mainIcons.sleepMongddang} alt="sleepMong" css={imgCss} />
            <Typography color="dark" size="1" weight={500} css={textCss}>
              알림이 없어요
            </Typography>
          </div>
        )}
      </div>
    </div>
  );
};

export default NotificationContent;
