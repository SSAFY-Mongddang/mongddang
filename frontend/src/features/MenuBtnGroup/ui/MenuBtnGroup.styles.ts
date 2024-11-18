import ColorStyle from '@/shared/ui/styles/colorStyles';
import { css } from '@emotion/react';

export const btnGroup = css`
  display: flex;
  width: 100%;
  height: 100%;
  justify-content: center;
  align-items: center;
  align-content: center;
  align-self: stretch;
  flex-wrap: wrap;
  gap: 0.5rem;
  padding: 10px 10px 10px 10px;
`;

export const btn = (isDisable: boolean) => css`
  display: flex;
  flex: 1 1 40%;
  width: 100%;
  padding: 7% 0;
  justify-content: center;
  align-items: center;
  border: 1px solid #d9d9d9;
  border-radius: 10px;
  background: ${isDisable ? ColorStyle.dark.disabled : '#fff'};
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
`;
