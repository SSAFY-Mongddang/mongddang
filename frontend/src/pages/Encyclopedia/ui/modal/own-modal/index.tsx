/** @jsxImportSource @emotion/react */
import { Button } from '@/shared/ui/Button';
import { Icon } from '@/shared/ui/Icon';
import { Modal } from '@/shared/ui/Modal';
import { Typography } from '@/shared/ui/Typography';

import { HiOutlineX } from 'react-icons/hi';
import { Chip } from '@/shared/ui/Chip';
import { useMutation, useQueryClient } from '@tanstack/react-query';
import { AxiosResponse } from 'axios';
import { base, modalCss, storyTypographyCss, xiconCss } from './styles';
import { useState } from 'react';
import { ICharacterData } from '@/pages/Encyclopedia/model/types';
import { getMainInfo } from '@/pages/Encyclopedia/api/api';
import { UpdateCharacter } from '../update-character';

interface OwnModalProps {
  setstate: (value: boolean) => void;
  data: ICharacterData | null;
}

interface CharacterResponse {
  data: {
    data: ICharacterData[];
  };
}

interface MainCharacterResponse {
  code: string;
  message: string;
  data: {
    mongddangId: number;
    isMain: boolean;
  };
}

export const OwnModal = ({ setstate, data }: OwnModalProps) => {
  const queryClient = useQueryClient();
  const accessToken = localStorage.getItem('accessToken');
  const [isParentModalOpen, setIsParentModalOpen] = useState(true);
  const [isModal, setIsModal] = useState(false);
  const mainMutation = useMutation<
    AxiosResponse<MainCharacterResponse>,
    Error,
    number
  >({
    mutationFn: (characterId) => {
      if (!accessToken) {
        throw new Error('AccessToken이 필요합니다.');
      }
      return getMainInfo(accessToken, characterId);
    },
    onSuccess: (response, characterId) => {
      queryClient.setQueryData<CharacterResponse>(['character'], (oldData) => {
        console.log(response);
        if (!oldData) return oldData;

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

  const handleUpdateCharacterClose = () => {
    setIsModal(false);
    setIsParentModalOpen(true); // 메인 모달을 다시 열어줍니다
  };

  const clickEvent = () => {
    setIsParentModalOpen(false);
    setIsModal(true);
  };


  return (
    <div>
      {isParentModalOpen && (
        <Modal height={40} width={70} css={modalCss}>
          <Icon size={2} css={xiconCss} onClick={() => setstate(false)}>
            <HiOutlineX />
          </Icon>
          <div css={base}>
            <Chip border={0.625} color="primary" fontSize={1} fontWeight={700}>
              {data?.name}
            </Chip>
            <Icon size={5}>
              <img alt="icon-1" src="/img/말랑1.png" />
            </Icon>
            <Typography
              color="dark"
              size="1"
              weight={600}
              css={storyTypographyCss}
            >
              {data?.story}
            </Typography>
            <Button
              handler={clickEvent}
              color="primary"
              disabled={data?.isMain}
              fontSize="1"
              variant="contained"
            >
              {data?.isMain ? '대장' : '대장 설정'}
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
    </div>
  );
};
