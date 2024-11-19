/** @jsxImportSource @emotion/react */
import { IconTypoProps } from './IconTypo.types';
import { containerCss, iconCss, menuCss } from './IconTypo.styles';
import { Typography } from '../Typography';

export const IconTypo = ({
  icon, // 이제 string 타입으로 받습니다
  menu,
  selected = false,
  size = 3,
  fontSize = '1',
  disabled = false,
  ...props
}: IconTypoProps) => {
  return (
    <div css={containerCss} {...props}>
      <img css={iconCss(size, disabled)} src={icon} />
      <Typography css={menuCss(selected)} size={fontSize}>
        {menu}
      </Typography>
    </div>
  );
};
