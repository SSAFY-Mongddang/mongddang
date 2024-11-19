import { ColorScale, Palette } from '@/shared/model/globalStylesTyes';
import { css } from '@emotion/react';
import { Colors } from '../styles/globalStyles';
import ColorStyle from '../styles/colorStyles';

export const toggle = (
  isOn: boolean,
  color: Palette = 'primary',
  scale?: ColorScale,
  size: number = 2.5
) => css`
  position: relative;
  width: ${size * 0.8}rem;
  height: ${size / 2.5}rem;
  border-radius: ${size / 2.5}rem;
  background-color: ${isOn
    ? scale
      ? Colors[color][scale]
      : ColorStyle[color].main
    : Colors['dark'][300]};
  transition: background-color 300ms;

  &::after {
    content: '';
    position: absolute;
    width: ${size / 2.5}rem;
    height: ${size / 2.5}rem;
    border-radius: 50%;
    background-color: white;
    left: ${isOn ? `calc(100% - ${size / 2.5}rem)` : '0'};
    transition: left 300ms ease-in-out;
  }
`;
