import { css } from '@emotion/react';

export const base = (size: number) => css`
  display: inline-flex;
  align-items: center; 
  justify-content: center; 
  width: ${size}rem; 
  height: ${size}rem; 
  font-size: ${size}rem;

  & > img {
    width: 100%;
    height: 100%;
    object-fit: contain;
  }
`;
