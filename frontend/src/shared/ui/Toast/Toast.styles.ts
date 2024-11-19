import { SerializedStyles, css } from '@emotion/react';
import { ToastVariant } from './Toast.types';
import { Palette } from '@/shared/model/globalStylesTyes';
import ColorStyle from '../styles/colorStyles';

const colors: Record<Palette, string> = {
  primary: '#F3E5F5',
  success: '#E3F2FD',
  danger: '#FFEBEE',
  secondary: '#EBEBEB',
  indigo: '#3F51B5',
  blue: '#2196F3',
  dark: '#F3E5F5',
  light: '#F3E5F5',
};

export const base = (fontSize: number) => css`
  display: flex;
  align-items: center;
  column-gap: 1rem;
  width: fit-content;
  max-width: calc(100% - 2rem);
  padding: 0.8rem;
  border-radius: 0.25rem;
  font-size: ${fontSize}rem;
  border-radius: 0.625rem;
`;

export const variants: Record<
  ToastVariant,
  (color: Palette, fontSize: number) => SerializedStyles
> = {
  contained: (color: Palette, fontSize: number) => css`
    background-color: ${colors[color]};
    font-size: ${fontSize}rem;
  `,
  filled: (color: Palette, fontSize: number) => css`
    background-color: ${ColorStyle[color].main};
    font-size: ${fontSize}rem;
  `,
  outlined: (color: Palette, fontSize: number) => css`
    background-color: white;
    border: 1px solid ${ColorStyle[color].main};
    font-size: ${fontSize}rem;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
  `,
};
