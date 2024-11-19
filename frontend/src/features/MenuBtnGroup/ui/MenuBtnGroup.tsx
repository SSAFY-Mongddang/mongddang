/** @jsxImportSource @emotion/react */

import { IconTypo } from '@/shared/ui/IconTypo';
import { btn, btnGroup } from './MenuBtnGroup.styles';
import { useNavigate } from 'react-router-dom';
import { menuBtnData } from '../menuBtnData';
import { useAudioStore } from '@/shared/model/useAudioStore';

type MenuBtnGroupProps = {
  userRole: 'child' | 'protector';
};

const MenuBtnGroup = (props: MenuBtnGroupProps) => {
  const navigate = useNavigate();
  const { bubble } = useAudioStore();
  return (
    <div css={btnGroup}>
      {menuBtnData.map((item, i) => {
        return (
          <div
            css={btn(item.category === 'meal')}
            onClick={() => {
              if (item.category === 'meal') return;
              navigate(item.urlTo[props.userRole]);
              bubble.play();
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
