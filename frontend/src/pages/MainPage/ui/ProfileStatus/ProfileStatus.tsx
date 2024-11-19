/** @jsxImportSource @emotion/react */

import { Typography } from '@/shared/ui/Typography';
import {
  coinAmountCss,
  coinContainer,
  coinCss,
  container,
  nicknameCss,
  typoCss,
} from './ProfileStatus.styles';
import { mainIcons } from '../../constants/iconsData';

type ProfileStatusProps = {
  nickname: string;
  mainTitleName: string;
  coin: number;
};

const ProfileStatus = (props: ProfileStatusProps) => {

  return (
    <div css={container}>
      {/* 칭호 + 닉네임 */}
      <div css={nicknameCss}>
        <Typography color="blue" size="1" weight={600} css={typoCss}>
          {props.mainTitleName}
        </Typography>
        <Typography color="dark" size="1" weight={600}>
          {props.nickname} 님
        </Typography>
      </div>

      {/* 총 별가루 */}
      <div css={coinContainer}>
        <img css={coinCss} src={mainIcons.starCoinBag} alt="coin" />
        <div css={coinAmountCss}>
          <Typography color="dark" size="1" weight={600}>
            {props.coin}
          </Typography>
          &nbsp;
        </div>
      </div>
    </div>
  );
};

export default ProfileStatus;
