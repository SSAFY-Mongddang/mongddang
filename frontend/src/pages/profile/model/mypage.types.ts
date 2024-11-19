export interface IconnectedUser {
  name: string,
  nickname: string
}

export interface IuserData {
  name: string,
  role: 'child' | 'pprotector',
  email:string,
  nickname: string,
  invitationCode: string,
  birth: string,
  gender: 'male' | 'female'
  mainMongddangId : number,
  mainTitleId: number,
  connected:IconnectedUser[],
}

export interface IuserInfo {
  code: string,
  message: string,
  data: IuserData
}