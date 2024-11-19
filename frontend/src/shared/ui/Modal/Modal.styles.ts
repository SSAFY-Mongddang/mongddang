import { css } from '@emotion/react';

export const base = (height: number, width: number) => css`
  position: relative;
  width: ${width}%;
  min-height: ${height}%;
  padding: 1.5rem;
  border-radius: 0.5rem;
  background-color: #fff;
  display: flex;
  justify-content: center;
  z-index: 10; 
`;

export const backdropStyle = css`
  display: flex;
  justify-content: center;
  align-items: center;
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: rgba(0, 0, 0, 0.5); 
`;
