import axios from 'axios';

export const api = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
  withCredentials: true,
});

// 회원정보 닉네임 수정
export const login = (idToken: string) => {
  return api({
    url: '/api/auth/login',
    method: 'post',
    data: {
      idToken: idToken,
    },
  });
};

export interface LoginResponse {
  code: number;
  message: string;
  data: {
    isRegistered: boolean;
    accessToken: string;
  };
}
