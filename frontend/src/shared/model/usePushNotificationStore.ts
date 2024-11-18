import { PushNotificationSchema } from '@capacitor/push-notifications';
import { create } from 'zustand';
import { PushNotificationCategory } from './globalTypes';

export type PushNotificationInfo = {
  receiverNickname: string | null;
  childNickname: string | null;
  title: string | null;
  message: string | null;
  category: PushNotificationCategory | null;
  setPushNotification: (notification: PushNotificationSchema) => void;
  removePushNotification: () => void;
};

export const usePushNotificationStore = create<PushNotificationInfo>((set) => ({
  receiverNickname: null,
  childNickname: null,
  title: null,
  message: null,
  category: null,

  setPushNotification: (notification) => {
    const { receiverNickname, childNickname, title, message, category } =
      notification.data;
    set({
      receiverNickname,
      childNickname,
      title,
      message,
      category,
    });
  },

  removePushNotification: () => {
    set({
      receiverNickname: null,
      childNickname: null,
      title: null,
      message: null,
      category: null,
    });
  },
}));
