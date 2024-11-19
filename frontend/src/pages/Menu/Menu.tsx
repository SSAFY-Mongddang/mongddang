/** @jsxImportSource @emotion/react */

import MenuBtnGroup from '@/features/MenuBtnGroup/ui/MenuBtnGroup';
import {
  imgContainer,
  imgCss,
  menuBase,
  menuBtnContainer,
  menuBtnGroup,
  menuContent,
} from './styles';
import CloseBtn from './ui/CloseBtn/CloseBtn';
import MenuMongddang from '@/assets/img/page/menu.png';

const Menu = () => {
  return (
    <div css={menuBase}>
      <div css={menuContent}>
        {/* 메뉴 버튼 모음 */}
        <div css={menuBtnContainer}>
          <div css={imgContainer}>
            <img src={MenuMongddang} alt="" css={imgCss} />
          </div>
          <div css={menuBtnGroup}>
            <MenuBtnGroup userRole={'child'} />
          </div>
        </div>
      </div>
      {/* 닫기 버튼 */}
      <CloseBtn />
    </div>
  );
};

export default Menu;
