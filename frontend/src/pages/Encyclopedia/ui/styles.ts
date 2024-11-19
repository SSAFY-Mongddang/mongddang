import { css } from '@emotion/react';

export const base = css`
  width: 100vw;
  height: 100vh;
  position: relative;
  overflow-y: auto;
  
`;

export const containerCss = css`
  position: relative;
  z-index: 1;
  height: 100%;
`;

export const imgCss = css`
  width: 100%;
  height: 100%;
  object-fit: cover;
  position: fixed;
  top: 0;
  left: 0;
  z-index: -1;
`;

export const cardsWrapperCss = css`
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  gap: 0.8rem;
  padding: 0 1rem;
  justify-content: flex-start;
  align-items: flex-start;
  width: 100%;
  word-break: keep-all; 
  overflow-wrap: break-word; 
`;

export const cardContainerCss = css`
  flex: 0 0 auto;
  margin: 0.3rem 0;
  word-break: keep-all;
  overflow-wrap: break-word;
  white-space: normal; 
`;