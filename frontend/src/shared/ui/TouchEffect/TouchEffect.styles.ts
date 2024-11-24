import { css } from '@emotion/react';

export const effectCssInitialize = () => css`
  overflow: auto;
  height: 100vh;
  position: relative;
`;

export const circleEffect = () => css`
  @keyframes fadeOut {
    0% {
      opacity: 1;
    }
    100% {
      opacity: 0;
    }
  }

  position: absolute;
  border-radius: 50%;
  transform: translate(-50%, -50%);
  transition: opacity 1.5s;
  opacity: 1;
  width: 100px;
  height: 100px;
  animation: fadeOut 1.5s forwards;
  pointer-events: none;
  z-index: 9999999999999999999999999;
`;

export const effectArea = () => css`
  position: relative;
  width: 100%;
  height: 100%;
  overflow: hidden;
`;

export const imageSize = () => css`
  width: 100%;
  height: 100%;
  object-fit: contain;
`;
