import ColorStyle from '@/shared/ui/styles/colorStyles';
import { css } from '@emotion/react';

export const exerciseItem = css`
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%;
  height: 5rem;
  font-weight: 500;
  font-size: 1.15rem;
  border-bottom: 2px dashed ${ColorStyle.blue.active};
`;

export const textBox = css`
  display: flex;
  flex-direction: column;
  
  gap: 0.5rem;
`;
