/** @jsxImportSource @emotion/react */

import { TopBar } from '@/shared/ui/TopBar';
import { useNavigate, useParams } from 'react-router-dom';
import { DayRecordCategory } from '@/widgets/day-record-catetory';
import { useUserStore } from '@/entities/user/model/store';
import { useShallow } from 'zustand/shallow';
import { useQuery } from '@tanstack/react-query';
import { BloodsugarQueries } from '@/entities/blood-sugar/api';
import { article, chart, nickNameErr, nickNameErrImg } from './style';
import { BloodSugarChart } from '@/widgets/blood-sugar-chart';
import { useSelectedChildStore } from '@/entities/selected-child/model/store';
import { useMemo } from 'react';
import Loading from '@/shared/ui/Loading';
import useMinimumLoading from '@/shared/hooks/useMinimumLoading';
import sruprizeCapybara from '@/assets/img/fox_and_capybara/mongddang14_surprised.png';

export const DayRecordPage = () => {
  const nav = useNavigate();
  const { date } = useParams();
  if (typeof date === 'undefined') {
    throw new Error('Impossible date');
  }
  const { getUserInfo } = useUserStore(
    useShallow((state) => ({ getUserInfo: state.getUserInfo }))
  );
  const userInfo = getUserInfo();
  const selectedChild = useSelectedChildStore((state) => state.selectedChild);
  // const nickname = getUserInfo()?.user?.nickname ?? 'test';
  const nickname = useMemo(
    () =>
      userInfo.user?.role === 'child'
        ? userInfo.user.nickname
        : (selectedChild?.nickname ?? ''),
    [userInfo.user?.role, userInfo.user?.nickname, selectedChild?.nickname]
  );

  const {
    data: bloodSugarData,
    isError: isBloodSugarErr,
    isLoading: isBloodSugarLoading,
    error,
  } = useQuery(BloodsugarQueries.todayBloodSugarQuery(nickname, date));

  const showLoading = useMinimumLoading(isBloodSugarLoading);

  if (isBloodSugarErr) {
    console.log(JSON.stringify(error));
    throw new Error('Blood data error');
  }

  if (showLoading) return <Loading />;

  const nicknameErrRender = () => {
    return (
      <>
        <div css={nickNameErr}>
          <img css={nickNameErrImg} src={sruprizeCapybara} />
          <div>
            <span>아이를 다시 선택해주세요!</span>
          </div>
        </div>
      </>
    );
  };

  return (
    <>
      <header>
        <TopBar type="iconpage" iconHandler={() => nav(-1)}>
          내 기록
        </TopBar>
      </header>
      {nickname ? (
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
      ) : (
        nicknameErrRender()
      )}
    </>
  );
};
