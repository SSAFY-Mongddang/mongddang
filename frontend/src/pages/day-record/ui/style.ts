import { css } from '@emotion/react';

export const article = css`
  background-color: #e1f5fe;
  height: calc(100vh - 3rem);
  display: flex;
  flex-direction: column;
`;

export const chart = css`
  /* background-color: white; */
  flex-grow: 1;
  max-height: 60%;
  display: flex;
  justify-content: center;
  align-items: center;
`;

export const nickNameErr = css`
  position: absolute;
  top: 50%;
  left: 50%;
  translate: -50% -50%;
  text-align: center;
  font-weight: 500;
  font-size: 1.5rem;
`;

export const nickNameErrImg = css`
  width: 15rem;
  height: 15rem;
`;
