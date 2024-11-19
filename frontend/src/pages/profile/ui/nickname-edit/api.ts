import axios from 'axios';

export const api = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
  withCredentials: true,
});

// 닉네임 중복 조회
export const checkNickname = (nickname: string) => {
  return api({
    url: '/api/auth/check-nickname',
    method: 'post',
    data: {
      nickname: nickname,
    },
  });
};

// 닉네임 수정
export interface INickname {
  nickname: string
}

export const updateNickname = (
  accessToken: string,
  nickname: string
) => {
  const data = {
    nickname,
  };

  const options = {
    headers: {
      Authorization: `Bearer ${accessToken}`,
    },
  };

  return api.patch<INickname>(
    '/api/user/modify',
    data,
    options
  );
};