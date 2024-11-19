import { css } from "@emotion/react";

export const base = css`
  display: flex;
  height: 57px;
  padding: 1px 9px 15px 7px;
  justify-content: center;
  align-items: center;
  margin:3rem 0;
  flex-direction: column;
`;

export const containerCss = css`
  margin: 1rem;
  background-color: #fff;
  border-radius: 0.625rem;
  display: flex;
  flex-direction: column;
  padding:2rem;
  align-items: center;
  justify-content: center;
  text-align: center;
  gap: 1rem;

`
export const toastCss = (showToast:boolean) => css`
  position: fixed;
  bottom: 3rem;
  left: 0;
  right: 0;
  display: flex;
  justify-content: center;
  z-index: 100;
  transform: opacity 0.5s ease-in-out;
  opacity: ${showToast ? 1: 0};
`

