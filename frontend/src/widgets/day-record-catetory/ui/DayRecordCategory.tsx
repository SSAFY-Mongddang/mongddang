/** @jsxImportSource @emotion/react */

// import { DayRecordQueries } from '@/entities/day-record/api';
import { Bloodsugar } from '@/shared/api/blood-sugar';
import { TabMenu } from '@/shared/ui/TabMenu/indes';
// import { useQuery } from '@tanstack/react-query';
import { RenderExercise } from './day-record-exercise';
import { RenderMeal } from './day-record-meal';
import { RenderSleep } from './day-record-sleep';
import { RenderMedication } from './day-record-medication';

interface DayRecordCategoryProps {
  date: string;
  bloodSugarData: Bloodsugar[] | undefined;
  nickname: string;
}

export const DayRecordCategory = ({
  date,
  bloodSugarData,
  nickname,
}: DayRecordCategoryProps) => {
  // const { data: recordsData } = useQuery(
  //   DayRecordQueries.allRecordsQuery(nickname, date)
  // );

  const handleTabChange = (tabId: string) => {
    console.log(tabId);
  };

  return (
    <>
      <TabMenu
        contents={[
          <RenderMeal
            nickname={nickname}
            date={date}
            bloodSugarData={bloodSugarData}
          />,
          <RenderExercise
            nickname={nickname}
            date={date}
            bloodSugarData={bloodSugarData}
          />,
          <RenderSleep
            nickname={nickname}
            date={date}
            bloodSugarData={bloodSugarData}
          />,
          <RenderMedication
            nickname={nickname}
            date={date}
            bloodSugarData={bloodSugarData}
          />
        ]}
        onTabChange={handleTabChange}
      />
    </>
  );
};
