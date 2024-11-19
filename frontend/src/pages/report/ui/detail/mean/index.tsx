/** @jsxImportSource @emotion/react */

import { TopBar } from '@/shared/ui/TopBar';
import { useNavigate } from 'react-router-dom';
import { containerCss } from '../gmi/styles';
import { Typography } from '@/shared/ui/Typography';
import { DetailAverageChart } from '../../chart/abg-chart';

export const Mean = () => {
  const nav = useNavigate();
  return (
    <div>
      <TopBar type="iconpage" iconHandler={() => nav('/report')}>
        평균 혈당
      </TopBar>
      <DetailAverageChart/>
      <div css={containerCss}>
        <Typography color="dark" size="1.25" weight={600}>
          평균 혈당
        </Typography>
        <Typography color="dark" size="1" weight={600}>
          당뇨병 관리의 가장 기본은 혈당을 조절하는 것입니다.
          <br />
          만성 합병증의 위험이 바람직한 혈당 조절을 통해서 감소될 수 있기 때문입니다.
          <br />
          바람직한 혈당 조절 목표는 일반적으로 식전 혈당 80~130 mg/dL, 식후 2시간 혈당 180 mg/dL 미만입니다. 
          <br />
          하루의 평균 혈당은 100mg/dL 이하를 권장하고 있습니다.
        </Typography>
      </div>
    </div>
  );
};
