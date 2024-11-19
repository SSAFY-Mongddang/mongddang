import { Colors } from '@/shared/ui/styles/globalStyles';
import { css } from '@emotion/react';

export const mainContentCss = css`
  display: flex;
  flex-direction: column;
`;

export const containerCss = css`
  flex: 1;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  gap: 0.625rem;
  padding: 1.25rem;
  margin: 8rem 0 1rem;
`;

export const buttonGroupCss = css`
  display: flex;
  gap: 0.625rem;
  margin-top: 0.625rem;
`;

export const nextbtnCss = css`
  width: 19rem;
  margin-top: 1rem;
`;


export const btnCss = (
  role: string | undefined,
  currentRole: 'child' | 'protector'
) => css`
  border: 2px solid
    ${role === currentRole ? Colors['primary'][400] : Colors['dark'][400]};
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 1.5rem;
  border-radius: 0.625rem;
`;
