import { css } from '@emotion/react';

export const container = css`
  display: flex;
  position: relative;
  width: 10rem;
  background-color: #fff;
  justify-content: center;
  align-items: center;
  text-align: center;
  border-radius: 10px;
  border: 0.25rem solid #e1f5fe;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.15);
  box-sizing: content-box;
  white-space: break-spaces;
  word-break: keep-all;

  &::after {
    content: '';
    position: absolute;
    bottom: 0;
    transform: translateY(100%);
    left: 15%;
    width: 0;
    height: 0;
    border: 0.75rem solid transparent;
    border-top-color: #fff;
  }
`;
