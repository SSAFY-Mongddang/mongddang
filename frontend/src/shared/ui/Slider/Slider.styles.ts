import { css } from "@emotion/react";

export const sliderContainerCss = css`
  position: relative;
  width: 100%;
  height: 8px;
  margin: 10px 0;
`;

export const fillCss = (standard: number, max: number) => css`
  border-top-left-radius: 10px;
  border-bottom-left-radius: 10px;
  position: absolute;
  left: 0;
  top: 0;
  height: 100%;
  background-color: #007bff;
  width: ${standard < 0 ? 0 : Math.round((standard / max) * 100)}%;
  transition: all 200ms ease;
`;

export const remainingFillCss = (standard: number, max: number) => css`
  position: absolute;
  left: ${standard < 0 ? 0 : Math.round((standard / max) * 100)}%;
  top: 0;
  height: 100%;
  background-color: #ff4444;
  border-top-right-radius: 10px;
  border-bottom-right-radius: 10px;
  width: ${standard < 0 ? 100 : Math.max(0, 100 - Math.round((standard / max) * 100))}%;
  transition: all 200ms ease;
`;

export const thumbCss = (value: number, standard: number, max: number) => css`
  position: absolute;
  width: 20px;
  height: 20px;
  border-radius: 50%;
  top: 50%;
  left: ${Math.round((value / max) * 100)}%;
  transform: translate(-50%, -50%);
  background-color: ${value <= standard ? '#007bff' : '#ff4444'};
  border: 2px solid white;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
  transition: all 200ms ease;
  z-index: 1;
`;