import { css } from '@emotion/react';
import KidBackImg from '@/assets/img/page/background_kid.png';

export const kidsMainBase = css`
  display: flex;
  min-width: 360px;
  height: 100vh;
  justify-content: center;
  background-color: #91d5ff;
  background-image: url(${KidBackImg});
  background-size: cover;
  background-position: bottom;
  box-sizing: content-box;
`;

export const kidsMainContent = css`
  display: flex;
  flex-direction: column;
  width: 100%;
  padding: 3%;
  justify-content: space-between;
`;

export const topContainer = css`
  display: flex;
  width: 100%;
  height: fit-content;
  justify-content: space-between;
`;

export const bottomContainer = css`
  display: flex;
  position: relative;
  flex-direction: column;
  align-items: center;
  width: 100%;
  gap: 1rem;
`;

export const CharacterContainer = css`
  display: flex;
  flex-direction: column;
  position: absolute;
  top: 0;
  transform: translateY(-100%);
  align-items: center;
`;

export const mainCharacterCss = css`
  height: 12rem;
  object-fit: contain;
`;

export const mainCharacterMovingCss = css`
  @keyframes bounce {
    0% {
      transform: translateY(0) scaleX(1);
    }
    50% {
      transform: translateY(0) scaleX(-1);
    }
    100% {
      transform: translateY(0) scaleX(1); /* 원래 방향 복귀 */
    }
  }
  height: 12rem;
  animation: bounce 1s infinite steps(1, end);
  object-fit: contain;
`;

export const mainCharacterClickedCss = css`
  @keyframes foxRun {
    0% {
      transform: translateX(0) translateY(0) scaleX(-1); /* 시작: 기본 방향 */
    }
    20% {
      transform: translateX(50px) translateY(-10px) scaleX(-1); /* 오른쪽 위로 부드럽게 이동 */
    }
    40% {
      transform: translateX(80px) translateY(5px) scaleX(-1); /* 더 오른쪽 아래로 이동 */
    }
    60% {
      transform: translateX(70px) translateY(10px) scaleX(1); /* 왼쪽으로 방향 전환 및 이동 */
    }
    80% {
      transform: translateX(-30px) translateY(-5px) scaleX(1); /* 왼쪽 위로 부드럽게 이동 */
    }
    100% {
      transform: translateX(0) translateY(0) scaleX(-1); /* 원래 위치로 복귀 */
    }
  }

  height: 12rem; /* 여우 크기 */
  object-fit: contain;
  animation: foxRun 4s infinite ease-in-out; /* 4초 동안 부드럽게 반복 */
`;

export const iconGroupCss = css`
  display: flex;
  padding: 2% 0;
  gap: 1rem;
`;

export const iconHorizontalCss = css`
  display: flex;
`;

export const iconVerticalCss = css`
  display: flex;
  flex-direction: column;
  gap: 1rem;
`;

export const dotCss = css`
  position: absolute;
  top: 0;
  right: 5%;
`;
