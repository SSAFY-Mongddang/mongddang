/** @jsxImportSource @emotion/react */
import { css } from '@emotion/react';
import { useState, useEffect } from 'react';
import { Button } from '@/shared/ui/Button';
import { Icon } from '@/shared/ui/Icon';
import { Modal } from '@/shared/ui/Modal';
import { Typography } from '@/shared/ui/Typography';
import { HiOutlineX } from 'react-icons/hi';
import { Chip } from '@/shared/ui/Chip';
import { base, modalCss, xiconCss } from '../main-modal/styles';
import { useQueryClient } from '@tanstack/react-query';
import { ICharacterData } from '@/pages/Encyclopedia/model/types';
import { characterImages, formatId } from '@/pages/Encyclopedia/model/mongddang-img';
import animation from '../../../../../assets/img/Animation.gif';

const containerStyle = css`
  position: relative;
  display: inline-block;
`;

const characterStyle = css`
  position: relative;
  z-index: 1;
`;

const animationContainerStyle = css`
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 2;
  width: 300px;
  height: 300px;
`;

const imageStyle = css`
  width: 100%;
  height: 100%;
  object-fit: contain;
`;

interface CharacterResponse {
  data: {
    data: ICharacterData[];
  };
}

interface OwnModalProps {
  setstate: (value: boolean) => void;
  setIsNew: (value: boolean) => void;
  characterId?: number;
  data: ICharacterData | null;
}

export const FindModal = ({ setstate, data }: OwnModalProps) => {
  const [isParentModalOpen, setIsParentModalOpen] = useState(true);
  const [showAnimation, setShowAnimation] = useState(true);
  const queryClient = useQueryClient();

  useEffect(() => {
    const timer = setTimeout(() => {
      setShowAnimation(false);
    }, 1000); 

    return () => clearTimeout(timer);
  }, []);

  const closeAllModals = () => {
    queryClient.setQueryData<CharacterResponse>(['character'], (oldData) => {
      window.location.reload()
      if (!oldData) return oldData;

      return {
        ...oldData,
        data: {
          ...oldData.data,
          data: oldData.data.data.map((character) => ({
            ...character,
            isNew: false,
          })),
        },
      };
    });

    setstate(false);
    setIsParentModalOpen(false);
  };

  if (!data) return null;

  const imageKey = formatId(data.id);
  const imagePath = characterImages[imageKey];

  return (
    <>
      {isParentModalOpen && (
        <Modal height={40} width={70} css={modalCss}>
          <Icon size={2} css={xiconCss} onClick={closeAllModals}>
            <HiOutlineX />
          </Icon>
          <div css={base}>
            <Chip border={0.625} color="primary" fontSize={1} fontWeight={700}>
              몽땅
            </Chip>
            <div css={containerStyle}>
              <Icon size={5} css={characterStyle}>
                <img 
                  alt={`${data.name} 캐릭터 이미지`} 
                  src={imagePath}
                  css={imageStyle}
                />
              </Icon>
              {showAnimation && (
                <div css={animationContainerStyle}>
                  <img 
                    src={animation} 
                    alt="애니메이션 효과" 
                    css={imageStyle}
                  />
                </div>
              )}
            </div>
            <Typography color="dark" size="1" weight={600}>
              {data.name}을(를) 찾았습니다!
            </Typography>
            <Button
              handler={closeAllModals}
              color="primary"
              fontSize="1"
              variant="contained"
              style={{zIndex:'3'}}
            >
              안녕!
            </Button>
          </div>
        </Modal>
      )}
    </>
  );
};