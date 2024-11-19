import { SerializedStyles, css } from '@emotion/react';
import { TextFieldVariant } from './TextField.types';
import { Palette } from '@/shared/model/globalStylesTyes';
import ColorStyle from '../styles/colorStyles';

export const base = (
  multiLine: boolean,
  isFocused: boolean,
  color: Palette,
  variant: TextFieldVariant
) => css`
  position: relative;
  padding: 0.875rem 0.625rem;
  transition: all 150ms ease-in-out;

  ${variant === 'outlined'
    ? css`
        border: calc(0.0625rem * 1.5)
          ${isFocused ? ColorStyle[color].main : '#4C4848'} solid;
        border-radius: 0.3125rem;
      `
    : css`
        border-bottom: calc(0.0625rem * 1.5)
          ${isFocused ? ColorStyle[color].main : '#4C4848'} solid;
        border: none;
      `}

  ${multiLine &&
  css`
    overflow-y: scroll;
    -ms-overflow-style: none;
    ::-webkit-scrollbar {
      width: 0;
    }
  `}
`;

export const labelField = (
  palette: Palette,
  placeholder: string,
  defaultValue: string,
  inputValue: string,
  isFocused: boolean
) => css`
  position: absolute;
  top: calc(50% - 0.5rem);
  padding: 0 0.3125rem;

  color: ${isFocused ? ColorStyle[palette].main : '#4C4848'};
  font-size: ${inputValue || defaultValue || placeholder || isFocused
    ? '0.625rem'
    : '1rem'};
  transition: all 150ms ease-in-out;
  pointer-events: none;
`;

export const inputField = (multiLine: boolean) => css`
  width: 100%;
  margin: 0;
  padding: 0;
  border: 0;
  outline: none;
  ::placeholder {
    color: #999999;
  }

  ${multiLine &&
  css`
    overflow-y: scroll;
    -ms-overflow-style: none;
    ::-webkit-scrollbar {
      width: 0;
    }
  `}
`;

export const inputVariants: Record<TextFieldVariant, () => SerializedStyles> = {
  outlined: () => css``,
  standard: () => css`
    border-bottom: 1px solid #d5d5d5;
    border-radius: 0;
  `,
};

export const labelVariants: Record<
  TextFieldVariant,
  (
    palette: Palette,
    placeholder: string,
    defaultValue: string,
    inputValue: string,
    isFocused: boolean
  ) => SerializedStyles
> = {
  outlined: (placeholder, defaultValue, inputValue, isFocused) => css`
    ${inputValue || defaultValue || placeholder || isFocused
      ? css`
          top: -0.375rem;
          background-color: white;
        `
      : ''};
  `,
  standard: (placeholder, defaultValue, inputValue, isFocused) => css`
    ${inputValue || defaultValue || placeholder || isFocused
      ? css`
          top: -0.375rem;
        `
      : ''};
  `,
};
