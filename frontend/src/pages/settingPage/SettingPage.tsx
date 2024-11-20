/** @jsxImportSource @emotion/react */

import { TopBar } from '@/shared/ui/TopBar';
import { container, li, settingContent } from './SettingPage.styles';
import { useNavigate } from 'react-router-dom';
import { Toggle } from '@/shared/ui/Toggle';
import { Typography } from '@/shared/ui/Typography';
import { useAudioStore } from '@/shared/model/useAudioStore';
import { useState, useEffect } from 'react';
import {Foreground} from '../../pages/samsung-setting/plugin/ForegroundPlugin';
// import { flatMap } from 'lodash';
import { BloodGlucosePlugin } from '../check-samsung-data/plugin/BloodGlucosePlugin';
import { SamsungModal } from '../samsung-setting/ui/modal';
import { Button } from '@/shared/ui/Button'; 
import SamsungHealth from '../samsung-setting/plugin/SamsungHealthPlugin';
import PermissionToggles from '../samsung-setting/ui/modal/PermissionToggles';

const SettingPage = () => {
  const navigate = useNavigate();

  const { bgm, bubble} = useAudioStore();

  console.log('bgm', bgm.audioRef?.volume);

  const [bgmState, setBgmState] = useState<boolean>(!bgm.isMuted);
  const [bubbleState, setBubbleState] = useState<boolean>(!bubble.isMuted);
  const [isMonitoring, setIsMonitoring] = useState<boolean>(false);
  const [isModalOpen, setIsModalOpen] = useState(false)
  const [permissionResult, setPermissionResult] = useState<null | boolean>(null);
  console.log(permissionResult)
  
  const checkMonitorStatus =async ()=>{
    try{
      const isRunnigMonitor = await Foreground.getCurrentMonitoringStauts();
      console.log(`is Running: ${isRunnigMonitor}`)
      if(isRunnigMonitor){
        setIsMonitoring(true)
      } else{
        setIsMonitoring(false)
      }

    }catch(e){
      console.log(`checkMonitorStatus: ${e}`)
    }
  }

  useEffect(() => {
    checkMonitorStatus()
  },[])

  // const checkAllPermissions = async () => {
  //   try {
  //     const result = await SamsungHealth.checkPermissionStatusForHealthData();
  
  //     console.log('All Permissions:', result);
  //     for (const [key, value] of Object.entries(result)) {
  //       console.log(`${key}: ${value}`);
  //     }
  //     // Example: { bloodglucose: 'SUCCESS', steps: 'WAITING', ... }
  //   } catch (error) {
  //     console.error('Error checking permissions:', error);
  //   }
  // }

  const requestPermission = async (healthDataType: string) => {
    try {
      const result = await SamsungHealth.requestHealthPermission({
        healthDataType,
      });
      setPermissionResult(result.granted);
    } catch (error) {
      console.error("Error requesting health data permission:", error);
    }
  };
  const onClickPermBtn = ()=>{
    setIsModalOpen(true)
  }

  const closePermModel = ()=>{
    setIsModalOpen(false)
  }


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
        // BloodGlucosePlugin.checkUserBloodGlucosePerm()의 결과를 먼저 await로 대기
        const result = await BloodGlucosePlugin.checkUserBloodGlucosePerm();
        // result에서 isGranted 속성 확인
        if (!result.isGranted) {
          console.log("혈당 권한 없음!!");
          onClickPermBtn()
          return;
        } else {
          const response = await Foreground.startForeground();
          setIsMonitoring(true);
          console.log(`startForeground: ${response}`);
        }
      } catch (e) {
        console.error(`Error starting foreground: ${e}`);
      }
    }
  };
  
  const onClickSearchBloodGlucose = ()=>{
    navigate('/checksamsungdata')
  }
  

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
      <div>
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
            혈당 모니터링aa
          </Typography>
          <Toggle
            color="primary"
            size={3.5}
            onClick={handleClickForegroundToggle}
            isOn={isMonitoring}
          />
        </li>
      </ul>
      <PermissionToggles/>
      <Button
            handler={onClickSearchBloodGlucose}
            color="primary" 
            fontSize="1.25"
            variant="contained"
            fullwidth
          >
            삼성데이터조회
      </Button>
      </div>
      <SamsungModal isOpen={isModalOpen} onClose={closePermModel}>
            <h2>삼성헬스 권한</h2>
            <p>삼성 헬스 권한을 가져옵니다.</p> 
            <Button 
            handler={()=>{requestPermission("bloodGlucose")}}
            color="primary" 
            fontSize="1.25"
            variant="contained"
            fullwidth>혈당 권한</Button>
      </SamsungModal>
    </div>
  );
};

export default SettingPage;
