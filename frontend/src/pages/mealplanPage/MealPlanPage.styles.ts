import { css } from '@emotion/react';

export const mealPlanFormContainer = css`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  width: 100%;
  border-radius: 10px;
  padding: 3%;
  justify-content: space-between;
  background-color: #e1f5fe;
`;

export const mealPlanFormBox = css`
  display: flex;
  flex-direction: column;
  justify-content: center;
  width: 100%;
  border-radius: 10px;
  justify-content: space-between;
  background-color: #e1f5fe;
`;

export const inputTypo = css`
  text-align: center;
  background: #fff;
  border-radius: 10px;
`;

export const container = css`
  margin-bottom: 0.5rem;
  margin-left: 1.25rem;
  padding: 1rem 0.5rem;
  display: flex;
  position: relative;
  width: 70%;
  background-color: #fff;
  justify-content: center;
  align-items: center;
  text-align: center;
  border-radius: 10px;
  border: 0.25rem solid #e1f5fe;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.15);
  box-sizing: content-box;
  white-space: break-spaces;
  word-break: keep-all;

  &::after {
    content: '';
    position: absolute;
    right: 5%;
    bottom: 0;
    transform: translateY(0.7rem) rotate(-45deg);
    width: 1rem;
    height: 1rem;
    background: #fff;
    border-bottom-left-radius: 0.25rem;
    border-bottom: 0.25rem solid #e1f5fe;
    border-left: 0.25rem solid #e1f5fe;
    border-top-color: #fff;
  }
`;

export const imgContainerCss = css`
  margin-bottom: 0.5rem;
  display: flex;
  width: 100%;
  justify-content: flex-end;
`;
export const imgCss = css`
  width: 6rem;
`;
