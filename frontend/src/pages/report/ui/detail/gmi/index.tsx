/** @jsxImportSource @emotion/react */

import { TopBar } from '@/shared/ui/TopBar';
import { Typography } from '@/shared/ui/Typography';
import { useNavigate } from 'react-router-dom';
import { containerCss } from './styles';

export const GmiDetail = () => {
  const nav = useNavigate();
  return (
    <div>
      <TopBar type="iconpage" iconHandler={() => nav('/report')}>
        혈당관리지표(GMI)
      </TopBar>
      <div css={containerCss}>
        <Typography color="dark" size="1.25" weight={600}>
          혈당관리지표(GMI)란?
        </Typography>
        <Typography color="dark" size="1" weight={600}>
          연속혈당측정기(CGM)로부터 얻은 혈당 데이터를 통해 예상한 당화혈색소 값을 의미합니다.
          <br />
          일반적으로 당화혈색소와 비슷한 수치지만 혈당 변동이 심한 경우에는 실제 당화혈색소와 다를 수 있습니다.
        </Typography>
      </div>
      <div css={containerCss}>
        <Typography color="dark" size="1.25" weight={600}>
          당화혈색소란?
        </Typography>
        <Typography color="dark" size="1" weight={600}>
          당뇨병이 있는 환자가 혈당 조절을 잘 하고 있는지 확인할 수 있는 검사예요.
          <br />
          혈액 내 산소 운반 역할을 하는 적혈구 속 헤모글로빈과 혈중 포도당이 결합한 형태를 말해요.
          <br />
          적혈구 수명이 약 120일이므로, 당화혈색소 수치를 통해 과거 2~3개월 간의 평균적인 혈당 조절 상태를 알 수 있어요.
        </Typography>
      </div>
    </div>
  );
};