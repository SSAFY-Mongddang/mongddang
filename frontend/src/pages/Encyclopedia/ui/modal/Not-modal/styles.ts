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
  z-index: 1;
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

export const alignmentStyle = css`
  display: flex;
  align-items: center;
  gap: 8px;
`;