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
  height: 100%;
  padding: 3%;
  gap: 0.5rem;
  overflow-y: auto;
`;

export const addMedicineBtnCss = css`
  display: flex;
  width: 100%;
  justify-content: flex-end;
`;

export const medicineListCss = css`
  display: flex;
  flex-direction: column;
  width: 100%;
  height: 100%;
  gap: 0.5rem;
  overflow-y: auto;
`;
