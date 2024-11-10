/** @jsxImportSource @emotion/react */

import { BottomBar } from '@/shared/ui/BottomBar';
import {
  bottomContainer,
  CharacterContainer,
  iconGroupCss,
  iconHorizontalCss,
  iconVerticalCss,
  kidsMainBase,
  kidsMainContent,
  mainCharacterCss,
  topContainer,
} from './styles';
import ProfileStatus from './ui/ProfileStatus/ProfileStatus';
import { IconTypo } from '@/shared/ui/IconTypo';
import CurrentBloodSugar from './ui/CurrentBloodSugar/CurrentBloodSugar';
import MainCharacter from '@/assets/img/말랑1.png';
import ChatBubble from './ui/ChatBubble/ChatBubble';
import { useEffect, useState } from 'react';
import DietModal from './ui/DietModal/DietModal';
import MailBox from './ui/MailBox/MailBox';
import { useNavigate } from 'react-router-dom';
import RoutineBtnGroup from './ui/RoutineBtnGroup/RoutineBtnGroup';
import {
  AskEndRoutineAlert,
  EndRoutineAlert,
  StartRoutineAlert,
} from './ui/Alerts/Alerts';
import { getRoutine, setRoutine } from './hooks/useRoutineStatus';

const KidsMainPage = () => {
  const navigate = useNavigate();
  const accessToken = localStorage.getItem('accessToken');
  const [openDietModal, setOpenDietModal] = useState(false);
  const [openMailBox, setOpenMailBox] = useState(false);
  const [alertStatus, setAlertStatus] = useState('');
  const [alertBloodSugar, setAlertBloodSugar] = useState(0);
  const [routineValue, setRoutineValue] = useState<string>('');

  useEffect(() => {
    const fetchRoutine = async () => {
      const routineValue = await getRoutine();
      setRoutineValue(routineValue);
    };
    fetchRoutine;
  }, [routineValue]);

  const handleDietModal = () => {
    setOpenDietModal(true);
  };

  const closeDietModal = () => {
    setOpenDietModal(false);
  };

  const closeMailBox = () => {
    setOpenMailBox(false);
  };

  // 일상 수행 상태 관리
  const changeRoutine = (currentRoutine: string) => {
    setRoutine(currentRoutine);
  };

  // 알림창 상태 관리
  const handleAlert = (status: string) => {
    setAlertStatus(status);
  };

  // 알림창 혈당 관리
  const handleBloodSugar = (bloodSugar: number) => {
    setAlertBloodSugar(bloodSugar);
  };

  // 바텀바 url 이동
  const moveBottomBar = (menu: number | undefined) => {
    if (menu === 0) {
      navigate('/encyclopedia');
    } else if (menu === 1) {
      navigate('/menu');
    } else if (menu === 2) {
      navigate(`/record/${new Date()}`);
    }
  };
  console.log('알림창 상태', alertStatus);

  return (
    <div css={kidsMainBase}>
      <div css={kidsMainContent}>
        {/* 상단 컴포넌트들 */}
        <div css={topContainer}>
          {/* 프로필 상태창 */}
          <ProfileStatus />
          {/* 아이콘 모음 */}
          <div css={iconGroupCss}>
            <div css={iconHorizontalCss}>
              <IconTypo
                icon="/img/%EB%A7%90%EB%9E%911.png"
                fontSize="0.75"
                menu={
                  <span>
                    오늘의 <br />
                    퀘스트
                  </span>
                }
              />
            </div>
            <div css={iconVerticalCss}>
              <div
                onClick={() => {
                  setOpenMailBox(true);
                }}
              >
                <IconTypo
                  icon="/img/%EB%A7%90%EB%9E%911.png"
                  fontSize="0.75"
                  menu="알림"
                />
              </div>
              <div
                onClick={() => {
                  navigate('/nickname/title');
                }}
              >
                <IconTypo
                  icon="/img/%EB%A7%90%EB%9E%911.png"
                  fontSize="0.75"
                  menu={
                    <div>
                      도전
                      <br />
                      퀘스트
                    </div>
                  }
                />
              </div>
              <CurrentBloodSugar />
            </div>
          </div>
        </div>

        <div css={bottomContainer}>
          {/* 메인캐릭터 + 말풍선 */}
          <div css={CharacterContainer}>
            <ChatBubble />
            <img src={MainCharacter} alt="" css={mainCharacterCss} />
          </div>

          {/* 일상생활 버튼 3종 */}
          <RoutineBtnGroup
            changeRoutine={changeRoutine}
            handleDietModal={handleDietModal}
            routineValue={routineValue}
            handleAlert={handleAlert}
          />

          {/* 바텀바 */}
          <BottomBar
            icons={[
              '/img/%EB%A7%90%EB%9E%911.png',
              '/img/%EB%A7%90%EB%9E%912.png',
              '/img/%EB%A7%90%EB%9E%913.png',
            ]}
            menus={['몽땅 도감', '메뉴', '일일 기록']}
            onHandleChange={moveBottomBar}
          />
        </div>
      </div>

      {/* 식단 등록 모달 */}
      {openDietModal && (
        <DietModal
          accessToken={accessToken}
          closeDietModal={closeDietModal}
          changeRoutine={changeRoutine}
          handleAlert={handleAlert}
          handleBloodSugar={handleBloodSugar}
        />
      )}

      {/* 알림창 */}
      {openMailBox && <MailBox closeMailBox={closeMailBox} />}

      {alertStatus === 'startRoutine' ? (
        // 루틴 시작 혈당 알림
        <StartRoutineAlert
          routineValue={routineValue}
          bloodSugar={alertBloodSugar}
          handleAlert={handleAlert}
        />
      ) : alertStatus === 'askEndRoutine' ? (
        // 루틴 종료 여부 질문 알림
        <AskEndRoutineAlert
          routineValue={routineValue}
          accessToken={accessToken}
          handleAlert={handleAlert}
          changeRoutine={changeRoutine}
          handleBloodSugar={handleBloodSugar}
        />
      ) : alertStatus === 'endRoutine' ? (
        // 루틴 종료 혈당 알림
        <EndRoutineAlert
          routineValue={routineValue}
          bloodSugar={alertBloodSugar}
          handleAlert={handleAlert}
        />
      ) : (
        <></>
      )}
    </div>
  );
};

export default KidsMainPage;
