import { css } from '@emotion/react';

export const buttonNoneStyle = css`
  background: none;
  border: none;
`;

export const recordingBackDrop = css`
  position: absolute;
  left: 0;
  right: 0;
  top: 0;
  bottom: 0;
  display: flex;
  align-items: center;
  justify-content: center;
`;

export const recordingSign = css`
  color: white;
  z-index: 9999;
`;

export const waveMic = css`
  @keyframes naturalSwing {
    0% {
      transform: scale(1);
    }
    50% {
      transform: scale(1.1);
    }
    100% {
      transform: scale(1);
    }
  }

  animation: naturalSwing 1s ease-in-out infinite;
`;
