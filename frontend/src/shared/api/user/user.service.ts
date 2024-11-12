import { api } from '../interceptors';
import { UserResponse } from './user.type';

export class UserService {
  static userQuery(): Promise<UserResponse> {
    return api.get('/api/user/info');
  }
}
