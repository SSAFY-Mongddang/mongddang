/** @jsxImportSource @emotion/react */
import { TopBar } from '@/shared/ui/TopBar';
import space from '../../assets/img/space.png';
import { containerCss, imgCss } from '../Encyclopedia/ui/styles';
import { Description } from '../Encyclopedia/ui/description';
import { Toggle } from '@/shared/ui/Toggle';
import { Typography } from '@/shared/ui/Typography';
import { TitleComponent } from './ui/title-component';
import { toggleContainerCss } from './ui/styles';
import { useQuery } from '@tanstack/react-query';
import { getTitleInfo } from './api/api';
import { useState, useEffect } from 'react';
import { ItitleData } from './model/types';
import { useNavigate } from 'react-router-dom';

export const NicknameTitle = () => {
  const [isOn, setIsOn] = useState(false);
  const nav = useNavigate();

  useEffect(() => {
    const savedToggleState = localStorage.getItem('titleToggle');
    if (savedToggleState) {
      setIsOn(JSON.parse(savedToggleState));
    }
  }, []);

  const handleToggle = () => {
    const newToggleState = !isOn;
    setIsOn(newToggleState);
    localStorage.setItem('titleToggle', JSON.stringify(newToggleState));
  };

  const TitleQuery = useQuery({
    queryKey: ['title'],
    queryFn: async () => {
      return await getTitleInfo();
    },
  });
  console.log(TitleQuery.data?.data?.data)
  return (
    <div>
      <TopBar type="iconpage" iconHandler={() => nav('/main')}>
        칭호 도감
      </TopBar>
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
        <Toggle color="primary" size={2.5} isOn={isOn} onClick={handleToggle} />
      </div>
      {TitleQuery.data?.data?.data
        .filter((data: ItitleData) => (isOn ? data.isOwned : !data.isOwned))
        .map((data: ItitleData) => (
          <TitleComponent key={data.titleId} title={data} />
        ))}
    </div>
  );
};
