/** @jsxImportSource @emotion/react */

import { TopBar } from '@/shared/ui/TopBar';
import { container, li, settingContent } from './SettingPage.styles';
import { useNavigate } from 'react-router-dom';
import { Toggle } from '@/shared/ui/Toggle';
import { Typography } from '@/shared/ui/Typography';
import { useAudioStore } from '@/shared/model/useAudioStore';
import { useState, useEffect } from 'react';
import { Foreground } from './plugins/ForegroundPlugin';

const SettingPage = () => {
  const navigate = useNavigate();

  const { bgm, bubble } = useAudioStore();

  console.log('bgm', bgm.audioRef?.volume);

  const [bgmState, setBgmState] = useState<boolean>(!bgm.isMuted);
  const [bubbleState, setBubbleState] = useState<boolean>(!bubble.isMuted);
  const [isActive, setIsActive] = useState<boolean | null>(null);

  const checkStatus = async () => {
    try {
      const result = await Foreground.isCheckStatusMonitoring();
      setIsActive(result.isActive); // Native API 결과를 상태로 설정
    } catch (error) {
      console.error("Error checking monitoring status:", error);
      setIsActive(false); // 기본값으로 처리
    }
  };

  const startForegroundPermission = async () => {
    try {
      const response = await Foreground.startForeground();
      console.log(`startForeground: ${response}`);
    } catch (error) {
      console.error("Error starting foreground permission:", error);
    }
  };

  const stopForegroundPermission = async () => {
    try {
      const response = await Foreground.stopForeground();
      console.log(`stopForeground: ${response}`);
    } catch (error) {
      console.error("Error stopping foreground permission:", error);
    }
  };

  const handleClickMonitoringToggle = async () => {
    if (isActive === null) return; // 로딩 중에는 작동하지 않음
    try {
      if (isActive) {
        setIsActive(false)
        await stopForegroundPermission();
      } else {
        setIsActive(true)
        await startForegroundPermission();
      }
    } catch (error) {
      console.error("Error toggling monitoring status:", error);
    }
  };


  useEffect(() => {
    checkStatus();
  }, []);

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
      {isActive === null ? ( // 로딩 상태 처리
            <p>Loading...</p>
          ):(
      <div>
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
              onToggle={handleClickMonitoringToggle}
              isOn={isActive}
            />
        </li>
      </ul></div>)}
    </div>
  );
};

export default SettingPage;
