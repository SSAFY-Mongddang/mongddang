/** @jsxImportSource @emotion/react */

import { IconTypo } from '@/shared/ui/IconTypo';
import { btn, btnGroup } from './MenuBtnGroup.styles';
import { useNavigate } from 'react-router-dom';
import { menuBtnData } from '../menuBtnData';

type MenuBtnGroupProps = {
  userRole: 'child' | 'protector';
};

const MenuBtnGroup = (props: MenuBtnGroupProps) => {
  const navigate = useNavigate();
  return (
    <div css={btnGroup}>
      {menuBtnData.map((item, i) => {
        return (
          <div
            css={btn}
            onClick={() => {
              navigate(item.urlTo[props.userRole]);
            }}
            key={`menuBtn-${i}`}
          >
            <IconTypo icon={item.icon} menu={item.menu[props.userRole]} />
          </div>
        );
      })}
    </div>
  );
};

export default MenuBtnGroup;
