/** @jsxImportSource @emotion/react */

import MenuBtnGroup from '@/features/MenuBtnGroup/ui/MenuBtnGroup';
import {
  bubbleBox,
  bubbleChat,
  childList,
  container,
  menuBtnContainer,
  menuBtnGroup,
  menuContent,
} from './styles';
import { Dropdown } from '@/shared/ui/Dropdown';
import { useEffect, useState } from 'react';
import { Typography } from '@/shared/ui/Typography';
import { useUserStore } from '@/entities/user/model';
import { useSelectedChildStore } from '@/entities/selected-child/model/store';
import { useShallow } from 'zustand/shallow';

const ProtectorMain = () => {
  const [isOpen, setIsOpen] = useState(false);
  const [selected, setSelected] = useState<string | undefined>(undefined);
  const { connectedChild } = useUserStore(
    useShallow((state) => ({
      connectedChild: state.getUserInfo().user?.connected,
    }))
  );
  const setSelectedChild = useSelectedChildStore(
    (state) => state.setSelectedChild
  );

  useEffect(() => {
    if (connectedChild) {
      setSelected(connectedChild[0].name);
    }
  }, [connectedChild]);

  return (
    <div css={container}>
      <div css={menuContent}>
        {/* 어린이 목록 */}
        <div css={childList}>
          <div css={bubbleBox}>
            <Dropdown
              options={connectedChild ?? []}
              onSelect={(value) => {
                setSelected(value.name);
                setSelectedChild(value);
              }}
              isOpen={isOpen}
              onClose={() => setIsOpen(false)}
              selectedValue={selected}
              buttonLabel={selected} // 버튼 라벨을 선택된 값으로 설정
              onOpen={() => setIsOpen(true)} // 드롭다운 열기 기능 추가
            />
            <div css={bubbleChat}>
              <Typography color="dark" size="1.25" weight={600}>
                어린이의 기록
              </Typography>
            </div>
          </div>
        </div>
        <div css={menuBtnContainer}>
          {/* 메뉴 버튼 모음 */}
          <div css={menuBtnGroup}>
            <MenuBtnGroup userRole={'protector'} />
          </div>
        </div>
      </div>
    </div>
  );
};

export default ProtectorMain;
