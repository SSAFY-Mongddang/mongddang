/** @jsxImportSource @emotion/react */
import { TopBar } from '@/shared/ui/TopBar';
import space from '../../assets/img/space.png';
import { containerCss, imgCss } from '../Encyclopedia/styles';
import { Description } from '../Encyclopedia/description';
import { Toggle } from '@/shared/ui/Toggle';
import { Typography } from '@/shared/ui/Typography';
import { TitleComponent } from './ui/title-component';
import { toggleContainerCss } from './styles';
import { useQuery } from '@tanstack/react-query';
import { getTitleInfo } from './api';
import { ItitleData } from './types';
import { useState } from 'react';

export const NicknameTitle = () => {
  const [isOn, setIsOn] = useState(false);

  const TitleQuery = useQuery({
    queryKey: ['title'],
    queryFn: async () => {
      const accessToken = localStorage.getItem('accessToken') || '';
      return await getTitleInfo(accessToken);
    },
  });

  return (
    <div>
      <TopBar type="iconpage">칭호 도감</TopBar>
      <img css={imgCss} src={space} alt="배경 이미지" />
      <div css={containerCss}>
        <Description>
          <div>
            업적을 달성하면 <br /> 칭호를 얻을 수 있어!
          </div>
        </Description>
      </div>
      <div css={toggleContainerCss}>
        <Typography color="light" size="0.75" weight={700}>
          {isOn ? '모은 것만' : '안 모은 것만'}
        </Typography>
        <Toggle color="primary" size={2.5} isOn={isOn} onClick={() => setIsOn(!isOn)} />
      </div>
      {TitleQuery.data?.data?.data
        .filter((data: ItitleData) => (isOn ? data.isOwned : !data.isOwned)) // isOn이 true일 때는 isOwned가 true인 항목만, false일 때는 isOwned가 false인 항목만 필터링
        .map((data: ItitleData) => (
          <TitleComponent key={data.titleId} title={data} />
        ))}
    </div>
  );
};