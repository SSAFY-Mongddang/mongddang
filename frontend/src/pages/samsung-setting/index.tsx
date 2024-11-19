/** @jsxImportSource @emotion/react */

import { TopBar } from '@/shared/ui/TopBar';
import { base, containerCss } from './ui/styles';
import { Chip } from '@/shared/ui/Chip';
import { imgCss } from '../Encyclopedia/ui/styles';
import space from '../../assets/img/space.png';
import { Typography } from '@/shared/ui/Typography';
import { Button } from '@/shared/ui/Button';
// import { useQuery } from '@tanstack/react-query';
// import { getUserInfo } from './api/mypage-api';
import { useNavigate } from 'react-router-dom';
import { useUserStore } from '@/entities/user/model';

export const SamsungSetting = () => {
  const nav = useNavigate();
  const getUserInfo = useUserStore((state) => state.getUserInfo);

  const user = getUserInfo().user;

  return (
    <div>
      <div>상단</div>
      <TopBar type="iconpage" iconHandler={() => nav('/menu')}>
        삼성세팅
      </TopBar>
      <div css={base}>가나다</div>
      <img css={imgCss} src={space} alt="배경 이미지" />
      <div css={containerCss}>
        <Chip border={1} color="primary" fontSize={0.8} fontWeight={600}>
          삼성 세팅
        </Chip>
        <div
          style={{ display: 'flex', flexDirection: 'column', gap: '0.5rem' }}
        >
          <Typography color="dark" size="1" weight={700}>
            이름: {user?.name}
          </Typography>
          <Typography color="dark" size="1" weight={700}>
            닉네임: {user?.nickname}
          </Typography>
          <Typography color="dark" size="1" weight={700}>
            성별:
            {user?.gender === 'male' ? '남자' : '여자'}
          </Typography>
          <Typography color="dark" size="1" weight={700}>
            생년월일: {user?.birth}
          </Typography>
        </div>
        <div
          style={{ display: 'flex', flexDirection: 'column', gap: '0.5rem' }}
        >
          {user?.role === 'child' && (
            <Chip border={1} color="primary" fontSize={0.8} fontWeight={600}>
              연결된 보호자
            </Chip>
          )}
          {user?.connected?.map((guardian, index) => (
            <Typography key={index} color="dark" size="1" weight={700}>
              {guardian.name} ({guardian.nickname})
            </Typography>
          ))}
        </div>
      </div>
      <div style={{ display: 'flex', margin: '0 1rem', gap: '1rem' }}>
        <Button
          handler={() =>
            nav('/nickname/edit', {
              state: { nickname: user?.nickname },
            })
          }
          color="primary"
          fontSize="1.25"
          variant="contained"
          fullwidth
        >
          닉네임 수정
        </Button>
      </div>
    </div>
  );
};
