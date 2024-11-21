/** @jsxImportSource @emotion/react */
import { useState } from 'react';
import { Button } from '@/shared/ui/Button';
import { Icon } from '@/shared/ui/Icon';
import { Modal } from '@/shared/ui/Modal';
import { Typography } from '@/shared/ui/Typography';
import { HiOutlineX } from 'react-icons/hi';
import { Chip } from '@/shared/ui/Chip';
import { useMutation, useQuery } from '@tanstack/react-query';
import {
  // alignmentStyle,
  base,
  modalCss,
  storyTypographyCss,
  xiconCss,
} from './styles';
import {
  ICharacterData,
  ICharacterInfo,
} from '@/pages/Encyclopedia/model/types';
import { getCoinInfo, postRecruitment } from '@/pages/Encyclopedia/api/api';
import { BuyModal } from '../buy-modal';
import { FindModal } from '../find-modal';
import {
  formatId,
  noCharacterImages,
} from '@/pages/Encyclopedia/model/no-mongddang-img';
import coin from '@/assets/img/icon/star_coin.png';
import { css } from '@emotion/react';
import coinbag from '@/assets/img/icon/star_coin_bag.png';
import { queryClient } from '@/shared/lib/queryClient';
import { AxiosResponse } from 'axios';

interface OwnModalProps {
  setstate: (value: boolean) => void;
  data: ICharacterData;
}

export const Notmodal = ({ setstate, data }: OwnModalProps) => {
  const [, setIsNew] = useState(false);
  const [buyModal, setBuyModal] = useState<boolean>(false);
  const [findModal, setFindModal] = useState<boolean>(false);
  const imageKey = formatId(data.id);
  const imagePath = noCharacterImages[imageKey];

  const characterMutation = useMutation({
    mutationFn: async () => {
      return await postRecruitment(data.id);
    },
    onSuccess: async () => {
      // await queryClient.invalidateQueries({ queryKey: ['character'] });
      queryClient.setQueryData<AxiosResponse<ICharacterInfo>>(
        ['character'],
        (oldData) => {
          if (!oldData) return oldData;
          console.log('olddata');
          console.log(oldData);

          const newData = {
            ...oldData,
            data: {
              ...oldData.data,
              data: oldData.data.data.map((character) => {
                if (character.id === data.id) {
                  return {
                    ...character,
                    isNew: true,
                    isOwned: true,
                  };
                }

                return character;
              }),
            },
          };

          console.log('newData');
          console.log(newData);

          return newData;
        }
      );
      setFindModal(true);
    },
    onError: (error) => {
      console.error('Error recruiting character:', error);
      alert('캐릭터 모집에 실패했습니다. 다시 시도해주세요.');
    },
  });

  const CoinQuery = useQuery({
    queryKey: ['coin'],
    queryFn: async () => {
      return await getCoinInfo();
    },
  });

  const handleBuyModalBlue = () => {
    setBuyModal(false);
    characterMutation.mutate();
  };

  const handleFindModalClose = () => {
    setFindModal(false);
    setstate(false);
  };

  return (
    <div>
      <Modal height={40} width={70} css={modalCss}>
        <Icon size={2} css={xiconCss} onClick={() => setstate(false)}>
          <HiOutlineX />
        </Icon>
        <div css={base}>
          <Chip border={0.625} color="primary" fontSize={1} fontWeight={700}>
            {data.name}
          </Chip>
          <Icon size={5}>
            <img alt={`${data.name} 캐릭터 이미지`} src={imagePath} />
          </Icon>
          <Typography
            color="dark"
            size="1"
            weight={600}
            css={storyTypographyCss}
          >
            {data.story}
          </Typography>
          <Button
            handler={() => setBuyModal(true)}
            color="primary"
            fontSize="1"
            variant="contained"
            css={css`
              display: flex;
              align-items: center;
              justify-content: center;
            `}
          >
            <Icon size={1.6}>
              <img alt="코인 사진" src={coin} />
            </Icon>
            {data.price}
          </Button>
          <Typography
            color="dark"
            size="1.25"
            weight={600}
            css={css`
              ${storyTypographyCss}
              display: flex;
              align-items: center;
              justify-content: center; // 수평 중앙 정렬
              gap: 2px;
              width: 100%; // 전체 너비 사용

              img {
                vertical-align: middle;
              }
            `}
          >
            <Icon size={3}>
              <img alt={'코인 사진'} src={coinbag} />
            </Icon>
            {CoinQuery.data?.data.data.coin}
          </Typography>
        </div>
      </Modal>
      {buyModal && (
        <BuyModal
          bluehandler={handleBuyModalBlue}
          redhandler={() => setBuyModal(false)}
        />
      )}
      {findModal && (
        <FindModal
          data={data}
          setstate={handleFindModalClose}
          setIsNew={setIsNew}
        />
      )}
    </div>
  );
};
