/** @jsxImportSource @emotion/react */
import {useState } from 'react';
import { BottomBarProps } from './BottomBar.types';
import { containerCss } from './BottomBar.styles';
import { IconTypo } from '../IconTypo';
import img1 from '/img/말랑1.png';
import img2 from '/img/말랑2.png';
import img3 from '/img/말랑3.png';

export const BottomBar = ({
  children,
  icons = [img1, img2, img3],
  menus = ['도감', '메뉴', '기록'],
  onHandleChange,
  ...props
}: BottomBarProps) => {
  const [curMenu, setCurMenu] = useState<number|undefined>(undefined);

  const handler = (menu: number) => {
    onHandleChange?.(menu);  // optional chaining 추가
    setCurMenu(menu);
  };
  
  return (
    <div css={containerCss} {...props}>
      {icons.map((icon, idx) => {
        return (
          <div key={idx} onClick={() => handler(idx)}>
            <IconTypo
              key={idx}
              icon={icon}
              menu={menus[idx]}
              selected={curMenu === idx}
            />
          </div>
        );
      })}
      {children}
    </div>
  );
};