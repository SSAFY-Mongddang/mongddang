/** @jsxImportSource @emotion/react */

import { Typography } from '@/shared/ui/Typography';
import {
  chatCss,
  checkImgCss,
  container,
  guideCss,
  guideTextCss,
  lineCss,
  missionItemCss,
  missionListCss,
  missionNameCss,
  mongImgCss,
} from './DailyMissionContent.styles';
import { mainIcons } from '../../constants/iconsData';
import DailyMissionBtn from '../DailyMissionBtn/DailyMissionBtn';
import React from 'react';
import {
  useDailyMissionQuery,
  useMissionRewardMutation,
} from '../../model/useDailyMissionQuery';
import Loading from '@/shared/ui/Loading';

type MissionInfo = {
  missionId: number;
  name: string;
  reward: number;
  status: string;
};

const DailyMissionContent = () => {
  const { data, isLoading } = useDailyMissionQuery();
  const missionRewardMutation = useMissionRewardMutation();

  if (isLoading) return <Loading />;

  const handleRewardBtn = (missionId: number) => {
    missionRewardMutation.mutate(missionId);
  };

  return (
    <div css={container}>
      <div css={missionListCss}>
        {/* 보상 */}
        {data?.map((mission: MissionInfo, i: number) => {
          return (
            <React.Fragment key={`mission-${new Date()}-${i}`}>
              <div css={missionItemCss}>
                <div css={missionNameCss}>
                  <img src={mainIcons.mission} alt="star" css={checkImgCss} />
                  <Typography color="dark" size="0.75" weight={600}>
                    {mission.name}
                  </Typography>
                </div>

                {/* 보상 버튼 */}
                <DailyMissionBtn
                  missionId={mission.missionId}
                  btnStatus={mission.status}
                  reward={mission.reward}
                  handleRewardBtn={handleRewardBtn}
                />
              </div>
              <div css={lineCss}></div>
            </React.Fragment>
          );
        })}
      </div>

      {/* 하단 가이드 */}
      <div css={guideCss}>
        {/* 말풍선 */}
        <div css={guideTextCss}>
          <div css={chatCss}>
            <Typography color="dark" size="0.75" weight={600}>
              오늘의 미션을 성공하면 <br />
              별가루를 얻을 수 있어
            </Typography>
          </div>
        </div>
        {/* 몽땅 캐릭터 */}
        <img
          src={mainIcons.exerciseMongddang}
          alt="exerciseMong"
          css={mongImgCss}
        />
      </div>
    </div>
  );
};

export default DailyMissionContent;
