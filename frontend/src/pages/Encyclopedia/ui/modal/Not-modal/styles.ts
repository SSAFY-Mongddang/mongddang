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