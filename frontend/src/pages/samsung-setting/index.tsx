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
import { Toggle } from '@/shared/ui/Toggle';
import { toggleContainerCss } from '../nickname-title/ui/styles';
import PermissionToggles from './ui/modal/PermissionToggles';

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
        <div style={{ display: 'flex', flexDirection: 'column', gap: '0.5rem' }}>
          <PermissionToggles/>
          {/* <div css={toggleContainerCss}>
              <Toggle color="primary" size={2.5} isOn={isBloodGlucosePermOn} onClick={()=>{ checkHealthDataPermission("bloodGlucose")}} />
          </div> */}
          <Button
            handler={onClickPermBtn}
            color="primary" 
            fontSize="1.25"
            variant="contained"
            fullwidth
          >
            권한설정
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
    </div>
  );
};

const Root: React.FC = () => (
  <ModalProvider>
    <SamsungSetting />
  </ModalProvider>
);

export default Root;
