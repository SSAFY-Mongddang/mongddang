import { TopBar } from '@/shared/ui/TopBar';
import { useNavigate } from 'react-router-dom';
import { Center } from '@mantine/core';
import { RecordCalendar } from '@/features/calendar';
import recordStyle from './style.module.css';
import { useEffect } from 'react';

export const RecordPage = () => {
  const nav = useNavigate();
  useEffect(() => {
    console.log('recordPage');
  }, []);
  return (
    <>
      <TopBar type="iconpage" iconHandler={() => nav(-1)}>
        내 기록
      </TopBar>

      <Center classNames={{ root: recordStyle.root }}>
        <RecordCalendar />
      </Center>
    </>
  );
};
