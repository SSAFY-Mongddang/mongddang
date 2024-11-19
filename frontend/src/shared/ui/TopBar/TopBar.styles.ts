import { css } from '@emotion/react';
import { TTopBar } from './TopBar.types';

export const base = (type: TTopBar) => css`
  position: sticky;
  top: 0;
  display: flex;
  align-items: center;
  justify-content: ${type === 'modal' ? 'space-between' : 'flex-start'};
  width: 100%;
  height: 3rem;
  min-height: 3rem;
  padding: 0 1rem;
  background-color: #8FDCFF;
  box-sizing: border-box;
  z-index: 10;
  color: #fff;

  ${type === 'modal' &&
  css`
    border-radius: 1.25rem 1.25rem 0 0;
  `}
`;
