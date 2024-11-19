import { css } from "@emotion/react";

export const base = css`
  display: flex;
  flex-direction: column;
  text-align: center;
  gap: 1rem;
  justify-content: center;
  align-items: center;
  position: relative; 
`

export const modalCss = css`
  border: 5px solid #8FDCFF;
  position: relative; 
  box-sizing: content-box;
`

export const xiconCss = css`
  position: absolute; 
  top: 1rem; 
  right: 1rem; 
  z-index: 3;
`
