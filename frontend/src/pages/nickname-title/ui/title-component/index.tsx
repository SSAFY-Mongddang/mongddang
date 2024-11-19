/** @jsxImportSource @emotion/react */
import { Button } from '@/shared/ui/Button';
import { Progress } from '@/shared/ui/Progress';
import { Typography } from '@/shared/ui/Typography';
import { useState, useEffect } from 'react';
import { base, containerCss, progressCss } from './styles';
import { UpdateCharacter } from '../modal/update-character';
import { useMutation, useQueryClient } from '@tanstack/react-query';
import { getTitleAchievement, getTitleMain } from '../../api/api';
import { ItitleData } from '../../model/types';
import AchievementToast from '../Toast';
import MainToast from '../Toast/main.tsx';
import { Icon } from '@/shared/ui/Icon/index.tsx';
import imagePath from '@/assets/img/icon/achievement_icon.png'

interface TitleComponentProps {
  title: ItitleData;
}

export const TitleComponent = ({ title }: TitleComponentProps) => {
  const [isModal, setIsModal] = useState(false);
  const [isToast, setIsToast] = useState(false);
  const [isMainToast, setIsMainToast] = useState(false);
  const queryClient = useQueryClient();

  const { mutate: achievementMutate } = useMutation({
    mutationFn: async () => {
      const accessToken = localStorage.getItem('accessToken') || '';
      if (!accessToken) {
        throw new Error('AccessToken이 필요합니다.');
      }
      return await getTitleAchievement(accessToken, title.titleId);
    },
    onSuccess: () => {
      setIsModal(false);
      queryClient.invalidateQueries({ queryKey: ['titles'] });
      window.location.href = window.location.pathname + '?showToast=true';
    },
    onError: () => {
      alert('업적 달성에 실패했습니다.');
    },
  });

  const { mutate: mainMutate } = useMutation({
    mutationFn: async () => {
      const accessToken = localStorage.getItem('accessToken') || '';
      if (!accessToken) {
        throw new Error('AccessToken이 필요합니다.');
      }
      return await getTitleMain(title.titleId);
    },
    onSuccess: () => {
      setIsModal(false);
      queryClient.invalidateQueries({ queryKey: ['titles'] });
      window.location.href = window.location.pathname + '?showMainToast=true';
    },
    onError: () => {
      alert('대표 달성에 실패했습니다.');
    },
  });

  const handleAchievementClick = () => {
    achievementMutate();
  };

  useEffect(() => {
    const urlParams = new URLSearchParams(window.location.search);
    const shouldShowToast = urlParams.get('showToast');
    const shouldShowMainToast = urlParams.get('showMainToast');

    if (shouldShowToast === 'true') {
      setIsToast(true);
      window.history.replaceState({}, '', window.location.pathname);
    }

    if (shouldShowMainToast === 'true') {
      setIsMainToast(true);
      window.history.replaceState({}, '', window.location.pathname);
    }
  }, []);

  useEffect(() => {
    if (isToast) {
      const timer = setTimeout(() => {
        setIsToast(false);
      }, 4000);

      return () => clearTimeout(timer);
    }
  }, [isToast]);

  useEffect(() => {
    if (isMainToast) {
      const timer = setTimeout(() => {
        setIsMainToast(false);
      }, 4000);

      return () => clearTimeout(timer);
    }
  }, [isMainToast]);

  return (
    <div css={base}>
      <div css={containerCss}>
        <div css={{ display: 'flex', flexDirection: 'column', gap: '0.3rem' }}>
          <div css={{ display: 'flex', alignItems: 'center', gap: '0.5rem' }}>
            <Typography 
              color="dark" 
              size="1" 
              weight={700}
            >
              {title.titleName}
            </Typography>
            {title.isOwned && title.isMain && (
              <Icon size={2} css={{ display: 'flex', alignItems: 'center', justifyContent: 'center' }}>
                <img
                  alt="메인 칭호"
                  src={imagePath}
                  css={{ width: '100%', height: '100%', objectFit: 'contain' }}
                />
              </Icon>
            )}
          </div>
          <Typography
            color="dark"
            scale="500"
            size="0.75"
            weight={600}
          >
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
              disabled={title.count !== title.executionCount}
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
