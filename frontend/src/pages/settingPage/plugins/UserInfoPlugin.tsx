import { registerPlugin } from '@capacitor/core';

// 요청 데이터 형식 정의
export interface TokenPayload {
  token: string| null; // 필수로 포함되어야 하는 토큰 값
//   nickname: String| null;
}

// 플러그인 결과 데이터 형식 정의
export interface UserInfoResponse {
  message: string; // 처리 결과 메시지
  errorCode?: string; // 선택적으로 반환되는 에러 코드
} 

// 플러그인 인터페이스 정의
export interface UserInfoPlugin {
  /**
   * 네이티브 플러그인에서 토큰 저장
   * @param options 요청 데이터 (TokenPayload 형식)
   * @returns 네이티브 처리 결과 (UserInfoResponse  형식)
   */
  getAccessToken(options: TokenPayload): Promise<UserInfoResponse>;
}

// 플러그인 등록
export const UserInfoPlugin = registerPlugin<UserInfoPlugin>('UserInfoPlugin');
