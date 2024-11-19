import { css } from "@emotion/react";
import { Colors } from "../styles/globalStyles";

export const containerCss = css`
  display: flex;
  flex-direction: column;
  width: 100%;
  min-width: 22.5rem; 
`;

export const tabListCss = css`
  display: flex;
  width: 100%;
`;

export const labelCss = (id:string, activeTab:string) => css`
  flex: 1;
  border: 0;
  padding: 1rem;
  border-radius: 1rem 1rem 0 0;
  box-shadow: 0.25rem 0 0.25rem ${Colors['dark'][500]};
  background-color: ${activeTab === id ? Colors['primary']['400'] : '#eee'};
  display: flex;
  justify-content: center;
  align-items: center;
  white-space: nowrap;
  
  & > div {
    display: flex;
    align-items: center;
    gap: 0.5rem;
  }
`;

export const contentCss = css`
  background-color: #fff;
  padding: 1rem;
  width: 100%;
`;