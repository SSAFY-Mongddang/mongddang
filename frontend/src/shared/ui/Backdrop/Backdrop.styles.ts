import { css } from '@emotion/react';

export const base = (opacity: number, blur: number) => css`
  position: absolute;
  top: 0;
  width: 100%;
  height: 100vh;
  z-index: 5;
  background-color: rgba(0, 0, 0, 0.8);
  opacity: ${opacity};
  backdrop-filter: blur(${blur / 10}px);
`;
