/** @jsxImportSource @emotion/react */
import React, { useState } from 'react';
import { MenuTabProps } from './MenuTab.types';
import { activeCss, containerCss, inactiveCss, variantCss } from './MenuTab.syles';


export const MenuTab = ({
  children,
  color = 'primary',
  fontSize = 1,
  fontWeight = 500,
  onChangeMenu = () => {},
  ...props
}: MenuTabProps) => {
  const [activeIndex, setActiveIndex] = useState(0);

  const handleTabClick = (index: number) => {
    setActiveIndex(index);
    onChangeMenu(index);
  };

  const tabCount = React.Children.count(children);

  return (
    <div css={containerCss()} {...props}>
      <div css={[activeCss(activeIndex, tabCount), variantCss(color, tabCount)]}></div>
      {React.Children.map(children, (child, index) => (
        <button
          css={[inactiveCss(tabCount, color, index === activeIndex, fontSize, fontWeight)]}
          onClick={() => handleTabClick(index)}
          color={color}
        >
          {child}
        </button>
      ))}
    </div>
  );
};
