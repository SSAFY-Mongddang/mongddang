type UserName = string;
type UserNickname = string;

interface Title {
  id: number;
  name: UserName;
}

export interface ConnectedUser {
  name: UserName;
  nickname: UserNickname;
}

type UserRole = 'child' | 'protector';
type Gender = 'male' | 'female';

export interface CreateUser {
  idToken: string;
  role: UserRole;
  birth: string;
  name: UserName;
  nickname: UserNickname;
  gender: Gender;
}

export type UpdateUser = Partial<Pick<CreateUser, 'nickname'>>;

export interface User extends CreateUser {
  email: string;
  invitationCode: string | null;
  mainMongddangId: number | null;
  mainTitle: Title | null;
  connected: ConnectedUser[];
}

export interface UserInfo {
  user?: User;
  userToken?: string;
}


export interface UserResponse {
  code: number;
  message: string;
  data: User;
}
