import { TopBar } from '@/shared/ui/TopBar';
import { useNavigate } from 'react-router-dom';
import { Center } from '@mantine/core';
import { RecordCalendar } from '@/features/calendar';
import recordStyle from './style.module.css';
import { useEffect } from 'react';
import { useUserStore } from '@/entities/user/model';

export const RecordPage = () => {
  const nav = useNavigate();
  useEffect(() => {
    console.log('recordPage');
  }, []);

  const getUserInfo = useUserStore((state) => state.getUserInfo);
  const userInfo = getUserInfo();

  const navHandler = () => {
    if (userInfo.user?.role === 'child') nav('/menu');
    if (userInfo.user?.role === 'protector') nav('/protector-main');
  };

  return (
    <>
      <TopBar type="iconpage" iconHandler={navHandler}>
        내 기록
      </TopBar>

      <Center classNames={{ root: recordStyle.root }}>
        <RecordCalendar />
      </Center>
    </>
  );
};
