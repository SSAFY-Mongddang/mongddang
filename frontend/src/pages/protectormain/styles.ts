import { css } from '@emotion/react';

export const container = css`
  display: flex;
  position: relative;
  min-width: 360px;
  min-height: 100vh;
  justify-content: center;
  background-color: #aedbff;
`;

export const menuContent = css`
  display: flex;
  width: 100%;
  flex-direction: column;
  padding: 3%;
  justify-content: space-around;
`;

export const childList = css`
  display: flex;
  justify-content: space-between;
  width: 100%;
  padding: 3%;
`;

export const bubbleBox = css`
  display: flex;
  flex-direction: column;
  padding: 3%;
  border-radius: 10px;
  justify-content: center;
  background-color: #fff;
  gap: 1rem;
`;

export const bubbleChat = css`
  display: flex;
  width: 100%;
  padding: 3%;
`;

export const menuBtnContainer = css`
  display: flex;
  width: 100%;
  height: fit-content;
  background-color: #fff;
  border: 0.5rem solid #e1f5fe;
  border-radius: 10px;
`;

export const menuBtnGroup = css`
  display: flex;
  width: 100%;
  padding: 3%;
`;
