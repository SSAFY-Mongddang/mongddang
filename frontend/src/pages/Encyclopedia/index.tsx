/** @jsxImportSource @emotion/react */
import { useState } from 'react';
import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import { TopBar } from '@/shared/ui/TopBar';
import space from '../../assets/img/space.png';

import { getCharacterInfo, getNewInfo } from './api/api';
import { ICharacterData, ICharacterInfo, INewInfoResponse } from './model/types';

import { AxiosResponse } from 'axios';
import {
  base,
  cardContainerCss,
  cardsWrapperCss,
  containerCss,
  imgCss,
} from './ui/styles';
import { OwnModal } from './ui/modal/own-modal';
import { useNavigate } from 'react-router-dom';
import { Notmodal } from './ui/modal/Not-modal';
import { Notowncharacter } from './ui/characterlist/notown-character';
import { Newcharacter } from './ui/characterlist/new-character';
import { Owncharacter } from './ui/characterlist/owncharacter';
import { Description } from './ui/description';
import Loading from '@/shared/ui/Loading';

export const Encyclopedia = () => {
  const [isOwnModal, setIsOwnModal] = useState(false);
  const [isMainModal, setIsMainModal] = useState(false);
  const [isNotModal, setIsNotModal] = useState(false);
  const nav = useNavigate();
  const [selectedCharacter, setSelectedCharacter] =
    useState<ICharacterData | null>(null);

  const queryClient = useQueryClient();
  const mutation = useMutation<AxiosResponse<INewInfoResponse>, Error, number>({
    mutationFn: getNewInfo,
    onSuccess: async () => {
      await queryClient.invalidateQueries({ queryKey: ['character'] });
      setIsMainModal(true);
    },
    onError: (error) => {
      console.error('상태 변경 실패:', error);
      alert('상태 변경에 실패했습니다. 다시 시도해주세요.');
    },
});
  const openModal = (
    character: ICharacterData,
    modalType: 'own' | 'main' | 'not'
  ) => {
    setSelectedCharacter(character);
    if (modalType === 'own') {
      setIsOwnModal(true);
    } else if (modalType === 'main') {
      mutation.mutate(character.id);
    } else if (modalType === 'not') {
      setIsNotModal(true);
    }
  };
  const {
    data: characterData,
    isLoading,
    isError,
    error,
  } = useQuery<ICharacterInfo>({ 
    queryKey: ['character'],
    queryFn: getCharacterInfo,
    refetchOnWindowFocus: true,
    staleTime: 0,
  });

  if (isLoading) return <Loading/>;
  if (isError) return <div>에러 발생: {error.message}</div>;
  if (!characterData?.data) return <div>데이터가 없습니다.</div>; 
  return (
    <div css={base}>
      {isOwnModal && selectedCharacter && (
        <OwnModal data={selectedCharacter} setstate={setIsOwnModal} />
      )}
      {isMainModal && selectedCharacter && (
        <OwnModal data={selectedCharacter} setstate={setIsOwnModal} />
      )}
      {isNotModal && selectedCharacter && (
        <Notmodal data={selectedCharacter} setstate={setIsNotModal} />
      )}

      <TopBar type="iconpage" iconHandler={() => nav(-1)}>
        캐릭터 도감
      </TopBar>
      <div css={containerCss}>
        <Description>
          <div>
            지금까지 모은 별가루로 <br /> 내 친구들을 찾을 수 있어!
          </div>
        </Description>

        <div css={cardsWrapperCss}>
        {characterData.data.map((data: ICharacterData) => (  // data.data.data 대신 data.data로 수정
          <div key={data.id} css={cardContainerCss}>
            {!data.isOwned ? (
              <div onClick={() => openModal(data, 'not')}>
                <Notowncharacter data={data} />
              </div>
            ) : data.isOwned && data.isNew ? (
              <div onClick={() => openModal(data, 'main')}>
                <Newcharacter data={data} />
              </div>
            ) : (
              <div onClick={() => openModal(data, 'own')}>
                <Owncharacter data={data} />
              </div>
            )}
          </div>
        ))}
      </div>
      </div>

      <img css={imgCss} src={space} alt="배경 이미지" />
    </div>
  );
};
