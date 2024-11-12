import { BloodSugarChart } from '@/widgets/blood-sugar-chart/ui';
import { TopBar } from '@/shared/ui/TopBar';
import { useNavigate } from 'react-router-dom';

export const TodayRecordPage = () => {
  const nav = useNavigate();

  return (
    <>
      <TopBar type="iconpage" iconHandler={() => nav(-1)}>
        내 기록
      </TopBar>
      <BloodSugarChart />
    </>
  );
};
