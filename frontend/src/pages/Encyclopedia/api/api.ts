
import { api } from '@/shared/api/interceptors';
import { AxiosResponse } from 'axios';
import { ICharacterData, ICharacterInfo, INewInfoResponse } from '../model/types';


// 회원정보 조회
export const getCharacterInfo = async (): Promise<ICharacterInfo> => {
  const response = await api({
    method: 'GET',
    url: '/api/game/collection/mongddang'
  });
  console.log('캐릭터 정보 조회 성공', response.data);
  return response.data;
};

// 보유 코인 조회
export const getCoinInfo = () => {
  return api({
      method: 'GET',
      url: '/api/game/coin'
  })
  .then((res)=>{
      console.log('코인 정보 조회 성공', res.data)
      return res
  })
  .catch((err)=>{
      console.log('코인 정보 조회 실패', err.message)
  })
}

// 새로 획득한 캐릭터(NEW) 표시 제거
export const getNewInfo = (mongddangId: number): Promise<AxiosResponse<INewInfoResponse>> => {
  return api({
    method: 'patch',
    url: '/api/game/collection/mongddang/check',
    data: { mongddangId }
  })
  .then((res) => {
    console.log(res.data);
    return res;
  })
  .catch((err) => {
    console.log(err);
    throw err;
  });
};

// 몽땅 모집
export const postRecruitment = ( mongddangId: number) => {
  return api({
    method: 'post',
    url: '/api/game/collection/mongddang/recruitment',
    data: {mongddangId}
  })
  .then((res)=>{
    console.log(res.data)
    return res
  })
  .catch((err)=>{
    console.log('에러',err)
  })
};

// 메인 캐릭터 설정
export const getMainInfo = (mongddangId: number): Promise<AxiosResponse<ICharacterData>> => {
  return api({
    method: 'patch',
    url: '/api/game/collection/mongddang/main',
    data: { mongddangId }
  })
  .then((res) => {
    console.log(res.data);
    return res;
  })
  .catch((err) => {
    console.log(err);
    throw err;
  });
};