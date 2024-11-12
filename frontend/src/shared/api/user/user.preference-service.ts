import { UserInfo } from './user.type';
import { Preferences } from '@capacitor/preferences';

export class PreferencesUser {
  private static async getState(): Promise<UserInfo> {
    const { value } = await Preferences.get({ key: 'user' });
    if (value === null) {
      const state: UserInfo = {
        user: undefined,
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

  static async setUser(user: UserInfo) {
    await this.setState(user);
    return user;
  }
}
