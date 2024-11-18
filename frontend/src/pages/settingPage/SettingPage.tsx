/** @jsxImportSource @emotion/react */

import { TopBar } from '@/shared/ui/TopBar';
import { container, li, settingContent } from './SettingPage.styles';
import { useNavigate } from 'react-router-dom';
import { Toggle } from '@/shared/ui/Toggle';
import { Typography } from '@/shared/ui/Typography';
import { useAudioStore } from '@/shared/model/useAudioStore';
import { useEffect, useState } from 'react';

const SettingPage = () => {
  const navigate = useNavigate();

  const { bgm, bubble } = useAudioStore();

  const [bgmState, setBgmState] = useState<boolean>(
    bgm?.audioRef[0]?.volume === 0 ? false : true
  );
  const [bubbleState, setBubbleState] = useState<boolean>(
    bubble?.audioRef[0]?.volume === 0 ? false : true
  );

  useEffect(() => {}, []);

  const handleClickBgmToggle = () => {
    bgm.toggleMute();
    if (bgmState) {
      setBgmState(false);
    } else {
      setBgmState(true);
    }
  };

  const handleClickBubbleToggle = () => {
    bubble.toggleMute();
    if (bubbleState) {
      setBubbleState(false);
    } else {
      setBubbleState(true);
    }
  };

  return (
    <div css={container}>
      <TopBar
        type="iconpage"
        iconHandler={() => {
          navigate('/menu');
        }}
      >
        설정
      </TopBar>
      <ul css={settingContent}>
        <li css={li}>
          <Typography color="dark" size="1.25" weight={500}>
            배경 음악
          </Typography>
          <Toggle
            color="primary"
            size={3.5}
            onToggle={handleClickBgmToggle}
            isOn={bgmState}
          />
        </li>
        <li css={li}>
          <Typography color="dark" size="1.25" weight={500}>
            효과음
          </Typography>
          <Toggle
            color="primary"
            size={3.5}
            onToggle={handleClickBubbleToggle}
            isOn={bubbleState}
          />
        </li>
      </ul>
    </div>
  );
};

export default SettingPage;
