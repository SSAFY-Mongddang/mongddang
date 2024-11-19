import { css } from '@emotion/react';
import { Tnotification } from './Notification.types';
import ColorStyle from '../styles/colorStyles';

export const base = (type: Tnotification, width: number, height: number) => css`
  position: relative;
  width: ${width}rem;
  height: ${height}rem;
  padding: 1.5rem 0;
  border-radius: 1.25rem;
  background-color: #fff;
  box-sizing: content-box;

  ${type === 'alert' &&
  css`
    border: 5px solid ${ColorStyle['danger'].main};
  `}
  ${type === 'primary' &&
  css`
    border: 5px solid ${ColorStyle['primary'].main};
  `}
`;

export const btnCss = css`
  position: static;
  display: flex;
  justify-content: center;
  position: absolute;
  width: 7rem;
  bottom: 1rem;
  left: 50%;
  transform: translateX(-50%);
`;

export const btnContainerCss = css`
  display: flex;
  justify-content: center;
  position: absolute;
  margin: 0 1.5rem;
  gap: 1rem;
  bottom: 1.5rem;
  left: 0;
  right: 0;
`;

export const backdropCss = css`
  display: flex;
  align-items: center;
  justify-content: center;
  position: fixed;
  background-color: rgba(0, 0, 0, 0.5);
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
`;
