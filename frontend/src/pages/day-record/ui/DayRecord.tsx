/** @jsxImportSource @emotion/react */

import { TopBar } from '@/shared/ui/TopBar';
import { useNavigate, useParams } from 'react-router-dom';
import { DayRecordCategory } from '@/widgets/day-record-catetory';
import { useUserStore } from '@/entities/user/model/store';
import { useShallow } from 'zustand/shallow';
import { useQuery } from '@tanstack/react-query';
import { BloodsugarQueries } from '@/entities/blood-sugar/api';
import { article, chart } from './style';
import { BloodSugarChart } from '@/widgets/blood-sugar-chart';

export const DayRecordPage = () => {
  const nav = useNavigate();
  const { date } = useParams();
  if (typeof date === 'undefined') {
    throw new Error('Impossible date');
  }
  const { getUserInfo } = useUserStore(
    useShallow((state) => ({ getUserInfo: state.getUserInfo }))
  );
  const nickname = getUserInfo()?.user?.nickname ?? 'test';

  const {
    data: bloodSugarData,
    isError: isBloodSugarErr,
    isLoading: isBloodSugarLoading,
  } = useQuery(BloodsugarQueries.todayBloodSugarQuery(nickname, date));

  if (isBloodSugarErr) throw new Error('Blood data error');
  if (isBloodSugarLoading) return <div>Loading...</div>;

  return (
    <>
      <header>
        <TopBar type="iconpage" iconHandler={() => nav(-1)}>
          내 기록
        </TopBar>
      </header>
      <article css={article}>
        <section css={chart}>
          {!isBloodSugarErr && !isBloodSugarLoading && (
            <BloodSugarChart data={bloodSugarData} />
          )}
        </section>
        <section>
          <DayRecordCategory
            nickname={nickname}
            bloodSugarData={bloodSugarData}
            date={date}
          />
        </section>
      </article>
    </>
  );
};
