import { css } from '@emotion/react';

export const container = css`
  display: flex;
  flex-direction: column;
  min-width: 360px;
  height: 100vh;
  box-sizing: content-box;
`;

export const topbarCss = css`
  position: static;
`;

export const content = css`
  display: flex;
  flex-direction: column;
  width: 100%;
  height: 100vh;
  padding: 7% 3%;
  gap: 1rem;
  align-items: center;
  overflow-y: auto;
`;

export const inputContentCss = css`
  display: flex;
  flex-direction: column;
  width: 100%;
  padding: 3%;
  gap: 1rem;
`;

export const inputContentItemCss = css`
  display: flex;
  flex-direction: column;
  width: 100%;
  padding: 0 3%;
`;

export const textFieldCss = css`
  padding: 3% 0%;
`;

export const medicationTypeGroupCss = css`
  display: flex;
  width: 100%;
  justify-content: space-around;
  gap: 1rem;
  padding: 0 5%;
`;

export const titieCss = css`
  display: flex;
  justify-content: center;
`;

export const medicationTypeItemCss = css`
  display: flex;
  gap: 0.5rem;
`;

export const medicationAddBtnCss = css`
  position: static;
  width: 95%;
`;
