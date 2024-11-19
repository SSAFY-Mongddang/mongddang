import axios from 'axios';
import { IuserInfo } from '../model/mypage.types';

export const api = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
  withCredentials: true,
});

// 회원정보 조회
export const getUserInfo = (accessToken: string) => {
  return api.get<IuserInfo>('/api/user/info', {
    headers: {
      Authorization: `Bearer ${accessToken}`,
    },
  });
};