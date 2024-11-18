import ColorStyle from '@/shared/ui/styles/colorStyles';
import { Colors } from '@/shared/ui/styles/globalStyles';
import { css } from '@emotion/react';
import loginBackground from '@/assets/img/page/login/login.png';

export const base = css`
  display: flex;
  align-items: center; // 수직 중앙 정렬
  justify-content: center; // 수평 중앙 정렬
  height: 100vh;
  background: url(${loginBackground}) 0 -2rem no-repeat;
  /* background-color: ${Colors.primary[400]}; */
`;

export const contentCss = css`
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 8rem;
`;

export const btnCss = css`
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 1rem;
  background-color: #fff;
  color: #000;
  border: 0.3rem solid ${ColorStyle.primary.main};
`;

export const googleCss = css`
  font-family: 'Paperlogy';
  border: 0.25rem solid ${Colors['primary'][500]};
  border-radius: 0.625rem;
`;

// Button base 스타일 수정
export const btn = css`
  position: absolute;
  bottom: 3rem;
  padding: 0; // 패딩 제거
  border: 0;
  background-color: transparent;
  transition: transform 0.1s;

  /* 클릭 효과만 남기고 배경색 변경 제거 */
  &:active {
    transform: translate(3px, 3px);
  }

  -webkit-tap-highlight-color: transparent;

  /* 기본 버튼 스타일 재정의 */
  &:enabled:active {
    background-color: transparent;
    border: none;
    box-shadow: none;
  }
`;

export const btnImg = css`
  object-fit: cover;
  width: 15rem;
  height: 5rem;
`;

export const pocket = css`
  position: absolute;
  width: 0.5rem;
  height: 1rem;
  background-color: black;
  transform: translate(-6rem, 5.3rem);
`;
