import { css } from '@emotion/react';

export const accordionContainer = css`
  border-bottom: 0.0625rem solid #ddd;
  border-radius: 0.25rem;
  width: 100%;
  max-width: 18.75rem;
`;

export const accordionTitle = css`
  padding: 0.625rem;
  cursor: pointer;
  font-weight: bold;
  display: flex;
  align-items: center;
  gap: 0.5rem; 
`;

export const titleContent = css`
  flex: 1;  
  display: flex;
  align-items: center;
  gap: 0.5rem;
`;

export const arrowIcon = css`
  margin-left: auto; 
`;

export const accordionContent = css`
  position: relative;
  overflow: hidden;
  transition: max-height 0.3s ease-in-out, opacity 0.3s ease-in-out;
`;

export const iconRotation = (isOpen: boolean) => css`
  transition: transform 0.3s ease;
  transform: rotate(${isOpen ? 90 : 0}deg);
`;

export const animatedContent = css`
  opacity: 1;
  transition: max-height 0.3s ease-in-out, opacity 0.3s ease-in-out;
  overflow: hidden;
  
  & > div {
    padding: 1rem;
  }
`;