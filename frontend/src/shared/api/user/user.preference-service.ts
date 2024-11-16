import { UserInfo } from './user.type';
import { Preferences } from '@capacitor/preferences';

export class PreferencesUser {
  private static async getState(): Promise<UserInfo> {
    const { value } = await Preferences.get({ key: 'user' });
    if (value === null) {
      const state: UserInfo = {
        user: null,
        // TODO: Access Token 하드코딩 수정
        userAccessToken: import.meta.env.VITE_TEST_USER_ACCESS_TOKEN,
        userIdToken: null,
      };
      await this.setState(state);
      return state;
    }
    return JSON.parse(value);
  }

  private static async setState(state: UserInfo) {
    await Preferences.set({
      key: 'user',
      value: JSON.stringify(state),
    });
  }

  static async getUser(): Promise<UserInfo> {
    return await this.getState();
  }

  static async setUser(userInfo: UserInfo) {
    await this.setState(userInfo);
    return userInfo;
  }

  static async updateUser(userInfo: Partial<UserInfo>) {
    const currentState = await this.getState();
    const updateState: UserInfo = {
      ...currentState,
      ...userInfo,
    };
    await this.setState(updateState);
    return updateState;
  }
}
