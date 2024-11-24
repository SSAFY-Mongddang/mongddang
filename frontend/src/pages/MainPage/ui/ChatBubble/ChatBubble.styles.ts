import { css } from '@emotion/react';

export const container = css`
  padding: 0.25rem;
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
    left: 15%;
    bottom: 0;
    transform: translateY(0.7rem) rotate(-45deg);
    width: 1rem;
    height: 1rem;
    background: #fff;
    border-bottom-left-radius: 0.25rem;
    border-bottom: 0.25rem solid #e1f5fe;
    border-left: 0.25rem solid #e1f5fe;
    border-top-color: #fff;
  }
`;
