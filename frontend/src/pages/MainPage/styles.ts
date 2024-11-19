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
