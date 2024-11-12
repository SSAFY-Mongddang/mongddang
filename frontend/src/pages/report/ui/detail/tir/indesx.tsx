/** @jsxImportSource @emotion/react */

import { TopBar } from '@/shared/ui/TopBar';
import { containerCss } from '../gmi/styles';
import { Typography } from '@/shared/ui/Typography';
import { useNavigate } from 'react-router-dom';

export const Tir = () => {
  const nav = useNavigate();
  return (
    <div>
      <TopBar type="iconpage" iconHandler={() => nav('/report')}>
        목표 범위 내 비율
      </TopBar>
      <div css={containerCss}>
        <Typography color="dark" size="1.25" weight={600}>
          목표 범위 내 비율(TIR)이란?
        </Typography>
        <Typography color="dark" size="1" weight={600}>
          혈당 변동성은 일상적인 생활 습관, 특히 식사 등의 전후에 혈당이 얼만큼 차이나는 지 보여주는 지표입니다.
          <br />
          혈당 변동성은 낮을수록 좋고, 목표ㅛ 범위 내 비율은 높을수록 좋습니다.
          <br />
          목표 범위는 70~180mg/dL이며, 하루 중 목표 범위 내 시간의 비율은 70% 이상을 권장합니다.
        </Typography>
      </div>
    </div>
  );
};
