import { css } from '@emotion/react';

export const containerCss = css`
  display: flex;
  flex-direction: column;
  align-items: center;
  text-align: center;
`;

export const iconCss = (size: number, disabled: boolean) => css`
  width: ${size}rem;
  height: ${size}rem;
  margin-bottom: 0.5rem;

  ${disabled &&
  `
    filter: grayscale(100%) brightness(0%);
  `}
`;

export const menuCss = (selected: boolean) => css`
  color: ${selected ? '#3498db' : '#333'};
  font-weight: ${selected ? 'bold' : 600};
`;
