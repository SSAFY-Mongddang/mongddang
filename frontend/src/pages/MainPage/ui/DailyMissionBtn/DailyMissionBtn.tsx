/** @jsxImportSource @emotion/react */

import { Button } from '@/shared/ui/Button';
import { btnContent, btnCss, starCss } from './DailyMissionBtn.styles';
import { Typography } from '@/shared/ui/Typography';
import { mainIcons } from '../../constants/iconsData';

type DailyMissionBtnProps = {
  missionId: number;
  handleRewardBtn: (missionId: number) => void;
  btnStatus: string;
  reward: number;
};

const DailyMissionBtn = (props: DailyMissionBtnProps) => {
  const { missionId, btnStatus, reward, handleRewardBtn } = props;

  return (
    <Button
      color={btnStatus === 'rewardable' ? 'light' : 'dark'}
      fontSize="1"
      disabled={btnStatus === 'rewardable' ? false : true}
      isShadow={btnStatus === 'rewardable' ? true : false}
      scale={btnStatus === 'rewardable' ? '200' : '500'}
      variant="contained"
      handler={() => {
        handleRewardBtn(missionId);
      }}
      css={btnCss}
    >
      <div css={btnContent}>
        <img src={mainIcons.starCoin} alt="star" css={starCss} />
        <Typography color="dark" size="1" weight={600}>
          {btnStatus === 'rewardable' || btnStatus === 'not_rewardable'
            ? reward
            : btnStatus === 'already_rewarded'
              ? '보상완료'
              : ''}
        </Typography>
      </div>
    </Button>
  );
};

export default DailyMissionBtn;
