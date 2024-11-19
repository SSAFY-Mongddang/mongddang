import { css } from "@emotion/react";


export const base = css`
  margin: 1rem;
  background-color: #fff;
  padding: 1rem;
  border-radius: 0.625rem;
`

export const containerCss =css`
  display: flex;
  justify-content:space-between ;
  margin-bottom: 0.8rem;
`

export const progressCss = css`
  display: flex;
  align-items: center;
  gap: 1rem;  

  > :first-child {
    flex: 0 0 70%; 
  }

  > :last-child {
    flex-shrink: 0;
  }
`