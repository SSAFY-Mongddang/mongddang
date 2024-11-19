import { css } from '@emotion/react';

export const baseModalContainer = css`
  flex-direction: column;
  width: 90%;
  height: 90%;
  border-radius: 1.5rem;
  padding: 0;
  justify-content: flex-start;
`;

export const baseModalTopBar = css`
  position: static;
`;

export const baseModalContent = css`
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  width: 100%;
  height: 100%;
  padding: 3%;
  gap: 0.5rem;
  overflow-y: auto;
`;
