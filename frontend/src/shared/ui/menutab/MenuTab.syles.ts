import { css } from '@emotion/react';
import ColorStyle from '../styles/colorStyles';
import { FontWeight, Palette } from '@/shared/model/globalStylesTyes';

export const containerCss = () => css`
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: transparent;
  position: relative;
  border-bottom: 0.125rem solid #eee;
`;

// 선택된 메뉴탭 스타일
export const activeCss = (activeIndex: number, tabCount: number) => css`
  height: calc(100% - 0.625rem);
  width: calc(${100 / tabCount}% - 0.625rem);
  position: absolute;
  top: 0.3125rem;
  left: ${activeIndex * (100 / tabCount)}%;
  transform: translateX(0.3125rem);
  border-radius: inherit;
  transition: all 300ms;
`;

export const variantCss = (color: Palette, tabCount: number) => css`
  background-color: transparent;
  border-bottom: 0.14375rem solid ${ColorStyle[color].main};
  margin-left: -0.2625rem;
  padding-bottom: 0.3125rem;
  width: ${100 / tabCount}%;
`;

// 선택 안 된 버튼 스타일
export const inactiveCss = (
  tabCount: number,
  color: Palette,
  isActive: boolean,
  fontSize: number,
  fontWeight: FontWeight
) => css`
  width: ${100 / tabCount}%;
  background: none;
  border: none;
  padding: 0.5rem;
  z-index: 2;
  font-size: ${fontSize}rem;
  font-weight: ${fontWeight};
  color: ${isActive ? ColorStyle[color].main : '#eee'};

  :enabled {
    :active {
      background-color: transparent;
      border: none !important;
    }
  }
`;
