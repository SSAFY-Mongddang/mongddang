/** @jsxImportSource @emotion/react */

import { TopBar } from '@/shared/ui/TopBar';
import { container, li, settingContent } from './SettingPage.styles';
import { useNavigate } from 'react-router-dom';
import { Toggle } from '@/shared/ui/Toggle';
import { Typography } from '@/shared/ui/Typography';
import { useAudioStore } from '@/shared/model/useAudioStore';
import { useState, useEffect } from 'react';
import { Foreground } from './plugins/ForegroundPlugin';
import { UserInfo, useUserStore } from '@/entities/user/model';
import { UserInfoPlugin } from './plugins/UserInfoPlugin';

const SettingPage = () => {
  const navigate = useNavigate();

  const { bgm, bubble } = useAudioStore();

  console.log('bgm', bgm.audioRef?.volume);
  const getUserInfo = useUserStore((state) => state.getUserInfo);
  const [token, setToken] = useState<UserInfo["userAccessToken"]>(null); // 사용자 정보를 저장
  const [bgmState, setBgmState] = useState<boolean>(!bgm.isMuted);
  const [bubbleState, setBubbleState] = useState<boolean>(!bubble.isMuted);
  const [isActive, setIsActive] = useState<boolean | null>(null);
  const [isLoading, setIsLoading] = useState<boolean>(true);
  console.log(isLoading)

  console.log("토큰!!!!!!!!!!!!!!!!!!!!!",token)
  const getUserInfoToAndroid = async() => {
    try{
      await UserInfoPlugin.getAccessToken({"token": token})
    }catch (error){
      console.log(error)
    }
  }

  getUserInfoToAndroid()

  const checkStatus = async () => {
    try {
      const result = await Foreground.isCheckStatusMonitoring();
      setIsActive(result.isActive); // Native API 결과를 상태로 설정
    } catch (error) {
      console.error("Error checking monitoring status:", error);
      setIsActive(false); 
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
    const fetchUserInfo = async () => {
      setIsLoading(true); // 로딩 시작
      const userInfo = await getUserInfo(); // 비동기 호출
      setToken("eyJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6InJpbWh5ZW5hQGdtYWlsLmNvbSIsInJvbGUiOiJjaGlsZCIsImlkIjoyMiwiaWF0IjoxNzMyMzY2MzgwLCJleHAiOjQ4ODU5NjYzODB9.hiq8qCgMiaVhLeggoqXb0HTxOYksfTVKRBWjTgP26Rw")
      setIsLoading(false); // 로딩 종료
    };

    fetchUserInfo();
  }, [getUserInfo]);


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
