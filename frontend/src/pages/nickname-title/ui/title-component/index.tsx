/** @jsxImportSource @emotion/react */
import { Button } from '@/shared/ui/Button';
import { Progress } from '@/shared/ui/Progress';
import { Typography } from '@/shared/ui/Typography';
import { useState } from 'react';
import { base, containerCss, progressCss } from './styles';
import { UpdateCharacter } from '../modal/update-character';
import { useMutation, useQueryClient } from '@tanstack/react-query';
import { getTitleAchievement, getTitleMain } from '../../api/api';
import { ItitleData } from '../../model/types';
import AchievementToast from '../Toast';
import MainToast from '../Toast/main.tsx';
import { Icon } from '@/shared/ui/Icon/index.tsx';
import imagePath from '@/assets/img/icon/achievement_icon.png';
import { useUserStore } from '@/entities/user/model/store.ts';

interface TitleComponentProps {
  title: ItitleData;
}

export const TitleComponent = ({ title }: TitleComponentProps) => {
  const [isModal, setIsModal] = useState(false);
  const [isToast, setIsToast] = useState(false);
  const [isMainToast, setIsMainToast] = useState(false);
  const queryClient = useQueryClient();
  const accessToken = useUserStore((state) => state.userAccessToken);

  const { mutate: achievementMutate } = useMutation({
    mutationFn: async () => {
      if (!accessToken) {
        throw new Error('AccessToken이 필요합니다.');
      }
      console.log('유저가 획득 버튼으로 타이틀 획득 동작을 수행함');
      return await getTitleAchievement(accessToken, title.titleId);
    },
    onSuccess: () => {
      setIsModal(false);
      queryClient.invalidateQueries({ queryKey: ['titles'] });
      setIsToast(true);
      setTimeout(() => {
        setIsToast(false);
      }, 4000);
    },
    onError: () => {
      alert('업적 달성에 실패했습니다.');
    },
  });

  const { mutate: mainMutate } = useMutation({
    mutationFn: async () => {
      if (!accessToken) {
        throw new Error('AccessToken이 필요합니다.');
      }
      return await getTitleMain(title.titleId);
    },
    onSuccess: () => {
      setIsModal(false);
      queryClient.invalidateQueries({ queryKey: ['titles'] });
      setIsMainToast(true);
      setTimeout(() => {
        setIsMainToast(false);
      }, 4000);
    },
    onError: () => {
      alert('대표 달성에 실패했습니다.');
    },
  });

  const handleAchievementClick = () => {
    achievementMutate();
  };

  return (
    <div css={base}>
      <div css={containerCss}>
        <div css={{ display: 'flex', flexDirection: 'column', gap: '0.3rem' }}>
          <div css={{ display: 'flex', alignItems: 'center', gap: '0.5rem' }}>
            <Typography color="dark" size="1" weight={700}>
              {title.titleName}
            </Typography>
            {title.isOwned && title.isMain && (
              <Icon
                size={2}
                css={{
                  display: 'flex',
                  alignItems: 'center',
                  justifyContent: 'center',
                }}
              >
                <img
                  alt="메인 칭호"
                  src={imagePath}
                  css={{ width: '100%', height: '100%', objectFit: 'contain' }}
                />
              </Icon>
            )}
          </div>
          <Typography color="dark" scale="500" size="0.75" weight={600}>
            {title.description}
          </Typography>
        </div>
        <div>
          {title.isOwned && title.isMain ? (
            <Button
              color="primary"
              fontSize="1"
              variant="contained"
              handler={() => setIsModal(true)}
              disabled
            >
              대표
            </Button>
          ) : title.isOwned ? (
            <Button
              color="primary"
              fontSize="1"
              variant="contained"
              handler={() => setIsModal(true)}
            >
              대표설정
            </Button>
          ) : (
            <Button
              color="primary"
              fontSize="1"
              variant="contained"
              handler={handleAchievementClick}
              disabled={title.count >= title.executionCount}
            >
              획득
            </Button>
          )}
        </div>
      </div>
      <div css={progressCss}>
        <Progress
          color="success"
          max={title.count}
          size="lg"
          value={title.executionCount}
          variant="rectangle"
        />
        <Typography color="dark" scale="500" size="0.75" weight={500}>
          {title.executionCount}/{title.count}
        </Typography>
      </div>
      {isModal && (
        <UpdateCharacter
          bluehandler={mainMutate}
          redhandler={() => setIsModal(false)}
        />
      )}
      {isToast && <AchievementToast />}
      {isMainToast && <MainToast />}
    </div>
  );
};
