import { css } from '@emotion/react';

export const modalContainer = css`
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  border-radius: 10px;
  width: 90%;
  height: 90%;
  padding: 0;
  overflow-y: auto;
`;

export const modalContent = css`
  display: flex;
  flex-direction: column;
  height: 100%;
  padding: 3%;
  gap: 0.5rem;
`;

export const modalTopBar = css`
  position: sticky;
  width: 100%;
  border-radius: 10px 10px 0 0;
  justify-content: center;
  align-items: center;
`;

export const timeBtnGroup = css`
  display: flex;
  width: 100%;
  height: fit-content;
  justify-content: center;
  align-items: center;
  align-content: center;
  align-self: stretch;
  flex-wrap: wrap;
  gap: 0.5rem;
`;

export const timeBtn = css`
  display: flex;
  flex: 1 1 40%;
  width: 100%;
  padding: 7% 0;
  justify-content: center;
  align-items: center;
  border: 1px solid #d9d9d9;
  border-radius: 10px;
  background: #fff;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
`;

export const inputContainer = css`
  height: 50%;
  border-color: #d9d9d9;
  white-space: pre-line;
`;
