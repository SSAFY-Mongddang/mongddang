import { css } from '@emotion/react';

import { ButtonVariant } from './Button.types';
import { ColorScale, Palette } from '@/shared/model/globalStylesTyes';
import { Colors } from '../styles/globalStyles';
import ColorStyle from '../styles/colorStyles';

export const base = (
  color: Palette,
  fullwidth: boolean,
  isShadow?: boolean,
  fontSize?: string
) => css`
  font-size: ${fontSize}rem;
  box-sizing: border-box;
  transition: all 100ms ease;
  user-select: none;
  padding: 0.55rem 1rem;
  border-radius: 0.625rem;
  font-family: 'Paperlogy';
  ${isShadow &&
  css`
    box-shadow: 0 4px 4px rgba(0, 0, 0, 0.25);
  `}

  :enabled {
    cursor: pointer;
    :active {
      background-color: ${ColorStyle[color].active};
      border: 0.125rem solid ${ColorStyle[color].active};
      box-shadow: inset 0 2px 2px rgba(0, 0, 0, 0.5);
    }
  }

  :disabled {
    cursor: default;
    color: ${ColorStyle[color].contrastText};
    background-color: #b6b3b3;
    border: 0.125rem solid #b6b3b3;
  }

  ${fullwidth &&
  css`
    width: 100%;
  `}
`;

export const variantCss = (
  variant: ButtonVariant,
  color: Palette,
  scale?: ColorScale
) => {
  switch (variant) {
    case 'contained':
      return css`
        background-color: ${!scale
          ? ColorStyle[color].button
          : Colors[color][scale]};
        color: ${ColorStyle[color].contrastText};
        border: 0.125rem solid
          ${!scale ? ColorStyle[color].button : Colors[color][scale]};
      `;

    case 'outlined':
      return css`
        border: 0.125rem solid
          ${color === 'light'
            ? '#d9d9d9'
            : !scale
              ? ColorStyle[color].button
              : Colors[color][scale]};
        font-family: 'Paperlogy';
        background-color: transparent;
        color: ${color === 'light'
          ? '#000'
          : !scale
            ? ColorStyle[color].main
            : Colors[color][scale]};
      `;
  }
};
