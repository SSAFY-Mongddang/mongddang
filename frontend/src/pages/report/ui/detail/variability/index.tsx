/** @jsxImportSource @emotion/react */

import { TopBar } from '@/shared/ui/TopBar';
import { containerCss } from '../gmi/styles';
import { Typography } from '@/shared/ui/Typography';
import { useNavigate } from 'react-router-dom';

export const Variability = () => {
  const nav = useNavigate();
  return (
    <div>
      <TopBar type="iconpage" iconHandler={() => nav('/report')}>
        혈당 변동성
      </TopBar>
      <div css={containerCss}>
        <Typography color="dark" size="1.25" weight={600}>
          혈당 변동성(GV)이란?
        </Typography>
        <Typography color="dark" size="1" weight={600}>
          혈당 변동성은 일상적인 생활 습관, 특히 식사 등의 전후에 혈당이 얼만큼
          차이나는 지 보여주는 지표입니다.
          <br />
          혈당 변동성은 낮을 수록 좋고, 목표 범위 내 비율은 높을 수록 좋습니다.
          <br />
          연속혈당측정기를 착용하며 혈당 변동성의 수치를 지속적으로 확인하고
          수치가 높게 나왔다면 낮추기 위한 노력을 해야 합니다.
        </Typography>
      </div>
    </div>
  );
};
