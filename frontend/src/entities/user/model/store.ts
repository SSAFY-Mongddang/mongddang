import { create } from 'zustand';
import { devtools } from 'zustand/middleware';
import { UserInfo } from './types';
import { PreferencesUser } from '@/shared/api/user';

interface UserStoreState extends UserInfo {
  fetchUser(): Promise<void>;
  getUserInfo(): UserInfo;
  setUser(user: UserInfo): Promise<void>;
  updateUser(newUserInfo: UserInfo): Promise<void>;
}

export const useUserStore = create<UserStoreState>()(
  devtools((set, get) => ({
    user: undefined,
    userToken: undefined,

    async fetchUser() {
      const userInfo = await PreferencesUser.getUser();
      set(() => ({ ...userInfo }));
    },

    getUserInfo() {
      return { user: get().user, userToken: get().userToken };
    },

    async setUser(newUserInfo: UserInfo) {
      const userInfo = await PreferencesUser.setUser(newUserInfo);
      set(() => ({ ...userInfo }));
    },

    async updateUser(newUserInfo: UserInfo) {
      const userInfo = await PreferencesUser.updateUser(newUserInfo);
      set(() => ({ ...userInfo }));
    },
  }))
);
