import { create } from 'zustand';
import { devtools } from 'zustand/middleware';
import { User, UserInfo } from './types';
import { PreferencesUser } from '@/shared/api/user';

interface UserStoreState extends UserInfo {
  fetchUser(): Promise<void>;
  getUser(): User | undefined;
  setUser(user: User): void;
}

export const useUserStore = create<UserStoreState>()(
  devtools((set, get) => ({
    user: undefined,

    async fetchUser() {
      const userInfo = await PreferencesUser.getUser();
      set(() => ({ user: userInfo.user }));
    },

    getUser() {
      return get().user;
    },

    async setUser(user: User) {
      const userInfo = await PreferencesUser.setUser({ user });
      set(() => ({
        user: userInfo.user,
      }));
    },
  }))
);
