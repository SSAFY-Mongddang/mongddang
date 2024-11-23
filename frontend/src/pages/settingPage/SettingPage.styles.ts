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
  gap: 0.75rem;
`;

export const li = css`
  display: flex;
  justify-content: space-between;
  align-items: center;
  list-style: none;
  width: 100%;
`;

export const formBox = css`
  display: flex;
  gap: 0.5rem;
`;

export const lineBox = css`
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 0.5rem;
`;

export const line = css`
  flex: 1;
  height: 1px;
  border-bottom: 1px solid #d1d1d1;
  margin: 0.5rem 0;
`;
