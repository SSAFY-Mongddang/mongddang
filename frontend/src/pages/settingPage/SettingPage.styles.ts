import { css } from '@emotion/react';

export const container = css`
  display: flex;
  position: static;
  flex-direction: column;
  min-width: 360px;
  box-sizing: content-box;
  height: 100vh;
`;

export const settingContent = css`
  padding: 0 5%;
  display: flex;
  flex-direction: column;
  width: 100%;
  height: 100%;
  gap: 1rem;
`;

export const li = css`
  display: flex;
  justify-content: space-between;
  align-items: center;
  list-style: none;
  width: 100%;
`;
