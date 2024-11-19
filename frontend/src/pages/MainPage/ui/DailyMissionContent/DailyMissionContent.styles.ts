import { css } from '@emotion/react';

export const container = css`
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  width: 100%;
  height: 100%;
  border-radius: 0 0 1.5rem 1.5rem;
  background-color: #e1f5fe;
`;

export const missionListCss = css`
  display: flex;
  flex-direction: column;
  width: 100%;
  height: 70%;
  padding: 3% 7%;
  align-items: center;
  flex-wrap: nowrap;
  gap: 0.2rem;
  overflow: auto;

  & :last-child {
    border: none;
  }
`;

export const missionItemCss = css`
  display: flex;
  width: 100%;
  height: 100%;
  align-items: center;
  justify-content: space-between;
`;

export const missionNameCss = css`
  display: flex;
  align-items: center;
  gap: 1rem;
`;

export const checkImgCss = css`
  height: 2rem;
`;

export const lineCss = css`
  width: 100%;
  border-top: 1px dotted #fff; /* 색상 */
`;

export const guideCss = css`
  display: flex;
  justify-content: flex-end;
  align-items: flex-end;
`;

export const guideTextCss = css`
  display: flex;
  position: relative;
  width: 100%;
  height: 100%;
  justify-content: center;
  align-items: center;
`;

export const chatCss = css`
  display: flex;
  position: absolute;
  top: 5%;
  right: 0;
  justify-content: center;
  /* padding: 7%; */
  width: 9rem;
  height: 3.5rem;
  border-radius: 10px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  align-items: center;
  text-align: center;
  background-color: #fff;
  z-index: 10;

  &::after {
    content: '';
    position: absolute;
    bottom: 10%;
    transform: translateY(100%);
    right: 3%;
    width: 0;
    height: 0;
    border: 0.75rem solid transparent;
    border-radius: 10px;
    border-top-color: #fff;
  }
`;

export const mongImgCss = css`
  height: 9rem;
`;
