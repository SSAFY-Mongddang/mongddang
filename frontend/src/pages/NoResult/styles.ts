import { css } from '@emotion/react';

export const containerCss = css`
  @keyframes fadeIn {
    from {
      opacity: 0;
      transform: translateY(1rem);
    }

    to {
      opacity: 1;
      transform: translateY(0);
    }
  }

  animation: fadeIn 0.2s ease 1;
`;

export const fitCss = css`
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: column;
  height: 100%;
`;

export const textCss = css`
  padding: 0.5rem;
`;
