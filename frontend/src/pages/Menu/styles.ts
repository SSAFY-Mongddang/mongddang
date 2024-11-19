import { css } from '@emotion/react';

export const menuBase = css`
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
  padding: 3%;
  align-items: end;
`;

export const menuBtnContainer = css`
  display: flex;
  position: relative;
  width: 100%;
  height: 75%;
  background-color: #fff;
  border: 0.5rem solid #e1f5fe;
  border-radius: 10px;
  margin-bottom: 50px;
`;

export const imgContainer = css`
  display: flex;
  width: 100%;
  position: absolute;
  top: 0;
  transform: translateY(-60%);
  justify-content: center;
`;

export const imgCss = css`
  height: 12rem;
`;

export const menuBtnGroup = css`
  display: flex;
  width: 100%;
  padding: 3%;
`;
