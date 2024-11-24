import { css } from "@emotion/react";

export const base = css`
  display: flex;
  flex-direction: column;
  text-align: center;
  gap: 1rem;
  justify-content: center;
  align-items: center;
  position: relative;
  word-break: keep-all;
  overflow-wrap: break-word;
`;

export const modalCss = css`
  border: 5px solid #8FDCFF;
  position: relative;
  word-break: keep-all;
  overflow-wrap: break-word;
  white-space: normal;
  box-sizing: content-box;
`;

export const xiconCss = css`
  position: absolute;
  top: 1rem;
  right: 1rem;
`;

export const storyTypographyCss = css`
  word-break: keep-all;
  overflow-wrap: break-word;
  white-space: normal;
  width: 100%;
  padding: 0 1rem;
  line-height: 1.5;
  text-align: center;
`;
export const modalOverlay = css`
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
`;

export const modalContentStyle = css`
  background-color: #fff;
  padding: 20px;
  border-radius: 8px;
  width: 300px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
`;
