/** @jsxImportSource @emotion/react */

import { TopBar } from '@/shared/ui/TopBar';
import { container, li, settingContent } from './SettingPage.styles';
import { useNavigate } from 'react-router-dom';
import { Toggle } from '@/shared/ui/Toggle';
import { Typography } from '@/shared/ui/Typography';
import { useAudioStore } from '@/shared/model/useAudioStore';
import { useState } from 'react';
import {Foreground} from '../../pages/samsung-setting/plugin/ForegroundPlugin';
import { flatMap } from 'lodash';

const SettingPage = () => {
  const navigate = useNavigate();

  const { bgm, bubble } = useAudioStore();

  console.log('bgm', bgm.audioRef?.volume);

  const [bgmState, setBgmState] = useState<boolean>(!bgm.isMuted);
  const [bubbleState, setBubbleState] = useState<boolean>(!bubble.isMuted);
  const [isMonitoring, setIsMonitoring] = useState<boolean>(false);

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

  const handleClickForegroundToggle = async () => {
    if (isMonitoring) {
      try {
        const response = await Foreground.stopForeground();
        setIsMonitoring(false);
        console.log(`stopForeground: ${response}`);
      } catch (e) {
        console.error(`Error stopping foreground: ${e}`);
      }
    } else {
      try {
        const response = await Foreground.startForeground();
        setIsMonitoring(true);
        console.log(`startForeground: ${response}`);
      } catch (e) {
        console.error(`Error starting foreground: ${e}`);
      }
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
        <li css={li}>
          <Typography color="dark" size="1.25" weight={500}>
            혈당 모니터링
          </Typography>
          <Toggle
            color="primary"
            size={3.5}
            onToggle={handleClickForegroundToggle}
            isOn={isMonitoring}
          />
        </li>
      </ul>
    </div>
  );
};

export default SettingPage;
