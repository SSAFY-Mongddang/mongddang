import ColorStyle from '@/shared/ui/styles/colorStyles';
import { css } from '@emotion/react';

export const mealContainer = css`
  flex-direction: column;
`;

export const mealItem = css`
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%;
  height: 10rem;
  gap: 1.8rem;
  border-bottom: 2px dashed ${ColorStyle.blue.active};
`;

export const imgBox = css`
  position: relative;
  display: flex;
  width: 10rem;
  height: 85%;
  object-fit: cover;
  margin: 0.5rem 0 0.5rem 0;
  border: 1px solid;
`;

export const mealImg = css`
  width: 100%;
  height: 100%;
  border: 2px solid;
`;

export const mealImgCover = css`
  position: absolute;
  background: rgb(0 0 0);
  width: 100%;
  height: 100%;
  opacity: 0;
  transition: opacity 0.3s ease;
  color: white;

  display: flex;
  padding: 0 1rem 0 1rem;
  justify-content: center;
  align-items: center;
`;

export const visibleCover = css`
  opacity: 0.8;
`;

// Dot indicator
export const dotContainer = css`
  position: absolute;
  right: -0.5rem;
  top: 45%;
  transform: translateY(-60%);
  gap: 4px;
`;

export const dotStyle = (isActive: boolean) => css`
  transition: all 0.3s ease;

  &::before,
  &::after {
    content: '';
    position: absolute;
    width: 8px;
    height: 8px;
    border-radius: 50%;
    background-color: #d9d9d9;
  }

  &::before {
    top: -8px;
    background-color: ${isActive && '#007aff'};
  }

  &::after {
    top: 8px;
    background-color: ${!isActive && '#007aff'};
  }
`;

export const mealTextBox = css`
  flex-grow: 1;
  display: flex;
  justify-content: center;
  font-weight: 500;
  font-size: 1.15rem;
`;

export const mealInnerBox = css`
  display: flex;
  flex-direction: column;
  text-align: center;
  gap: 0.5rem;
`;

export const beforeMeal = css`
  display: flex;
  flex-direction: column;
  position: relative;

  &::after {
    content: '';
    position: absolute;
    height: 1.5px;
    width: 100%;
    // 부모 요소 제일 아래에서 식전/후 혈당의 gap 절반 값
    top: calc(100% + 0.25rem);
    background-color: #C0C0C0;
  }
`;

export const afterMeal = css``;
