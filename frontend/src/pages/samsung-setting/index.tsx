/** @jsxImportSource @emotion/react */
import { TopBar } from '@/shared/ui/TopBar';
import { base, containerCss } from './ui/styles';
import { Chip } from '@/shared/ui/Chip';
import { imgCss } from '../Encyclopedia/ui/styles';
import space from '../../assets/img/space.png';
import { Typography } from '@/shared/ui/Typography';
import { Button } from '@/shared/ui/Button';
import { useNavigate } from 'react-router-dom';
// import { useUserStore } from '@/entities/user/model';
import { ModalProvider } from './ui/modal/ModalContext';
import { SamsungModal } from './ui/modal';
import { useState, useEffect } from 'react';
import SamsungHealth from './plugin/SamsungHealthPlugin';
import PermissionToggles from './ui/modal/PermissionToggles';
import { mainIcons } from '../MainPage/constants/iconsData';
import { IconTypo } from '@/shared/ui/IconTypo';
import {Foreground} from './plugin/ForegroundPlugin';

export const SamsungSetting: React.FC = () => {
  const navigate = useNavigate();
  const [isModalOpen, setIsModalOpen] = useState(false)
  const [permissionResult, setPermissionResult] = useState<null | boolean>(null);


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

  const startForegroundPermission = async() =>{
    const response = Foreground.startForeground()
    console.log(`startForeground: ${response}`)
  }
  const stopForegroundPermission = async() =>{
    const response = Foreground.stopForeground()
    console.log(`stopForeground: ${response}`)
  }

  const onClickSearchBloodGlucose = ()=>{
    navigate('/checksamsungdata')
  }

  return (
    <div>
      <TopBar type="iconpage" iconHandler={() => navigate('/menu')}>
        삼성헬스 세팅
      </TopBar>
      <div css={base}></div>
      <img css={imgCss} src={space} alt="배경 이미지" />
      <div css={containerCss}>
        <Chip border={1} color="primary" fontSize={0.8} fontWeight={600}>
          삼성헬스 모니터링
        </Chip>
        <div
          style={{
            display: 'flex',
            flexDirection: 'column',
            alignItems: 'center',
            gap: '0.5rem',
          }}
        >
          <Typography color="dark" size="1" weight={700}>
            삼성헬스 정보와 연동합니다.
          </Typography>
        </div>
          <PermissionToggles/>
          <Chip border={1} color="primary" fontSize={0.8} fontWeight={600}>
            혈당 모니터링
          </Chip>
          <div
            >
              <div style={{display:'flex', justifyContent:'space-between', width:'100%'}}>
                  <div
                    onClick={startForegroundPermission}
                  >
                    <IconTypo
                      icon={mainIcons.notification}
                      fontSize="0.75"
                      menu="시작" 
                    />
                  </div>
                  <div
                    onClick={stopForegroundPermission}
                  >
                    <IconTypo
                      icon={mainIcons.notification}
                      fontSize="0.75"
                      menu="종료" 
                    />
                  </div>
          </div>
          <Typography color="dark" size="1" weight={700}>
            5분마다 혈당이 체크됩니다. 
          </Typography>
        </div>
          <Button
            handler={onClickPermBtn}
            color="primary" 
            fontSize="1.25"
            variant="contained"
            fullwidth
          >
            권한설정
          </Button>
          <Button
            handler={onClickSearchBloodGlucose}
            color="primary" 
            fontSize="1.25"
            variant="contained"
            fullwidth
          >
            삼성데이터조회
          </Button>
          <SamsungModal isOpen={isModalOpen} onClose={closePermModel}>
            <h2>모달 제목</h2>
            <p>모달 내용이 여기에 표시됩니다.</p> 
            <Button 
            handler={()=>{requestPermission("bloodGlucose")}}
            color="primary" 
            fontSize="1.25"
            variant="contained"
            fullwidth>혈당 권한</Button>
          </SamsungModal>
      </div>
    </div>
  );
};

const Root: React.FC = () => (
  <ModalProvider>
    <SamsungSetting />
  </ModalProvider>
);

export default Root;
