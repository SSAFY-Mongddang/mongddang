import ColorStyle from '@/shared/ui/styles/colorStyles';
import { css } from '@emotion/react';

export const medicineItem = css`
  display: flex;
  justify-content: space-between;
  align-items: center;
  border-bottom: 2px dashed ${ColorStyle.blue.active};
  font-weight: 500;
  font-size: 1.15rem;
`;

export const itemName = css`
  font-weight: 600;
  font-size: 1.25rem;
`;

export const leftContainer = css`
  display: flex;
  flex-direction: column;
  gap: 0.2rem;
  padding: 1rem 0 1rem 0;
`;

export const timeText = css`
  font-size: 1rem;
`;

export const imgBox = css`
  position: relative;
  display: flex;
  width: 8rem;
  height: 85%;
  object-fit: cover;
  margin: 0.5rem 0 0.5rem 0;
  border: 1px solid;
`;