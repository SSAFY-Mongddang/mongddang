import { create } from 'zustand';
import { devtools } from 'zustand/middleware';
import { UserInfo } from './types';
import { PreferencesUser } from '@/shared/api/user';

interface UserStoreState extends UserInfo {
  fetchUserInfo(): Promise<void>;
  getUserInfo(): UserInfo;
  setUserInfo(user: UserInfo): Promise<void>;
  updateUserInfo(newUserInfo: Partial<UserInfo>): Promise<void>;
}

export const useUserStore = create<UserStoreState>()(
  devtools((set, get) => ({
    user: null,
    userAccessToken: null,
    userIdToken: null,

    async fetchUserInfo() {
      const userInfo = await PreferencesUser.getUser();
      set(() => ({ ...userInfo }));
    },

    getUserInfo() {
      return {
        user: get().user,
        userAccessToken: get().userAccessToken,
        userIdToken: get().userIdToken,
      };
    },

    async setUserInfo(newUserInfo: UserInfo) {
      const userInfo = await PreferencesUser.setUser(newUserInfo);
      set(() => ({ ...userInfo }));
    },

    async updateUserInfo(newUserInfo: Partial<UserInfo>) {
      const userInfo = await PreferencesUser.updateUser(newUserInfo);
      set((state) => ({ ...state, ...userInfo }));
    },
  }))
);
