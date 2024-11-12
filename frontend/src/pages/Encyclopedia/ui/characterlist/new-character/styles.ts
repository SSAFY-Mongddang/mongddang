import { css } from '@emotion/react';

export const containerCss = css`
  box-sizing: content-box;
  display: flex;
  width: 5rem;
  height: 7.5rem;
  padding: 0.625rem;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  gap: 0.625rem;
  flex-shrink: 0;
  border-radius: 0.625rem;
  background: #fff;
  box-shadow: 0 0.125rem 0.25rem 0 rgba(0, 0, 0, 0.25);
`;

export const newCss = css`
  position: absolute;
  top: 0%;
  left: 75%;
  transform: translateX(-50%);
  z-index: 2;
  padding: 0.125rem;
`;
