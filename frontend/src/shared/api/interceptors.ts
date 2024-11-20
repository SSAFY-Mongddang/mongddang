import axios from 'axios';
import { PreferencesUser } from './user';

export const api = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
  withCredentials: true,
});

// 요청에 자동으로 accessToken 추가
api.interceptors.request.use(async (config) => {
  // const accessToken = localStorage.getItem('accessToken');
  //const accessToken = import.meta.env.VITE_TEST_USER_ACCESS_TOKEN;
  const accessToken = (await PreferencesUser.getUser()).userAccessToken;
  console.log('axios interceptor request accessToken: ', accessToken);

  if (accessToken) {
    config.headers.Authorization = `Bearer ${accessToken}`;
  }

  return config;
});
