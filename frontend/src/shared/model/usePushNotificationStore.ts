import { create } from 'zustand';

export type PushNotificationInfo = {
  receiverNickname: string | null;
  childNickname: string | null;
  title: string | null;
  message: string | null;
  setPushNotification: (notification: any) => void;
  removePushNotification: () => void;
};

export const usePushNotificationStore = create<PushNotificationInfo>(
  (set) => ({
    receiverNickname: null,
    childNickname: null,
    title: null,
    message: null,

    setPushNotification: (notification) => {
      const { receiverNickname, childNickname, title, message } =
        notification.data;
      set({
        receiverNickname,
        childNickname,
        title,
        message,
      });
    },

    removePushNotification: () => {
      set({
        receiverNickname: null,
        childNickname: null,
        title: null,
        message: null,
      });
    },
  })
);
