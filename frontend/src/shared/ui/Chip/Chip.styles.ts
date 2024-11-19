import { FontWeight, Palette } from '@/shared/model/globalStylesTyes';
import { css } from '@emotion/react';
import ColorStyle from '../styles/colorStyles';

export const base = (
  color: Palette,
  border: number,
  fontSize: number,
  fontWeight: FontWeight
) => css`
  border-radius: ${border}rem;
  background-color: ${ColorStyle.background[color]};
  color: ${ColorStyle.text[color]};
  box-shadow: 0 0 0.125rem 0 #ccc;
  user-select: none;
  /* padding: 0.5rem 1rem; */
  padding: 0.375rem 1rem;
  font-size: ${fontSize}rem;
  font-weight: ${fontWeight};
  text-align: center;
  display: flex;
  width: fit-content;
  align-items: center;
`;
