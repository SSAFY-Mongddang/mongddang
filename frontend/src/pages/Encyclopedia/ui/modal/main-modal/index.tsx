/** @jsxImportSource @emotion/react */
import { useState } from 'react';
import { Button } from '@/shared/ui/Button';
import { Icon } from '@/shared/ui/Icon';
import { Modal } from '@/shared/ui/Modal';
import { Typography } from '@/shared/ui/Typography';
import { HiOutlineX } from 'react-icons/hi';
import { Chip } from '@/shared/ui/Chip';
import { base, modalCss, xiconCss } from './styles';
import { UpdateCharacter } from '../update-character';
import { useQueryClient, useMutation } from '@tanstack/react-query';
import { AxiosResponse } from 'axios';
import { ICharacterData } from '@/pages/Encyclopedia/model/types';
import { getMainInfo } from '@/pages/Encyclopedia/api/api';
import { characterImages, formatId } from '@/pages/Encyclopedia/model/mongddang-img';

interface OwnModalProps {
  setstate: (value: boolean) => void; 
  data: ICharacterData | null; 
}

interface CharacterResponse {
  data: {
    data: ICharacterData[];
  };
}


export const MainModal = ({ setstate, data }: OwnModalProps) => {
  const [isModal, setIsModal] = useState(false);
  const [isParentModalOpen, setIsParentModalOpen] = useState(true);

  const clickEvent = () => {
    setIsParentModalOpen(false);
    setIsModal(true);
  };

  const closeParentModal = () => {
    setstate(false);
    setIsParentModalOpen(false);
  };

  const handleUpdateCharacterClose = () => {
    setIsModal(false);
    setIsParentModalOpen(true); 
  };
  const queryClient = useQueryClient();
  const mainMutation = useMutation<
    AxiosResponse<ICharacterData>,
    Error,
    number
  >({
    mutationFn: (characterId) => {
      return getMainInfo(characterId);
    },
    onSuccess: (response, characterId) => {
      console.log('on Success from mutation');
      
      queryClient.setQueryData<CharacterResponse>(['character'], (oldData) => {
        console.log('response of useMutation success from encyclopedia main modal: ', response);
        if (!oldData) return oldData;

        console.log('oldData of useMutation from encyclopedia main modal');
        console.log('oldData: ', JSON.stringify(oldData));
        
        return {
          ...oldData,
          data: {
            ...oldData.data,
            data: oldData.data.data.map((character) => ({
              ...character,
              isMain: character.id === characterId,
            })),
          },
        };
      });

      setstate(false);
    },
    onError: (error) => {
      console.error('대장 설정 실패:', error);
      alert('대장 설정에 실패했습니다. 다시 시도해주세요.');
    },
  });
  const handleSetMain = () => {
    if (data?.id) {
      mainMutation.mutate(data.id);
      setIsParentModalOpen(false);
      setIsModal(true);
    }
  };
  if (!data) return null;
  const imageKey = formatId(data.id);
  const imagePath = characterImages[imageKey];

  return (
    <>
      {isParentModalOpen && (
        <Modal height={40} width={70} css={modalCss}>
          <Icon size={2} css={xiconCss} onClick={closeParentModal}>
            <HiOutlineX />
          </Icon>
          <div css={base}>
            <Chip border={0.625} color="primary" fontSize={1} fontWeight={700}>
              {data?.name}
            </Chip>
            <Icon size={5}>
              <img alt="icon-1" src={imagePath} />
            </Icon>
            <Typography
              color="dark"
              size="1"
              weight={600}
              style={{ wordBreak: 'break-word' }}
            >
              {data?.story}
            </Typography>
            <Button
              handler={clickEvent}
              color="primary"
              fontSize="1"
              variant="contained"
            >
              대장 설정
            </Button>
          </div>
        </Modal>
      )}

      {isModal && (
        <UpdateCharacter
        bluehandler={handleSetMain}
          redhandler={handleUpdateCharacterClose}
        />
      )}
    </>
  );
};
