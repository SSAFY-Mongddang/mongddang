/** @jsxImportSource @emotion/react */

import { Typography } from '@/shared/ui/Typography';
import { bloodSugarCss, container } from './CurrentBloodSugar.styles';
import { getCurrentBloodSugar } from '../../api/currentBloodSugarApi';
import { useEffect, useState } from 'react';

type CurrentBloodSugarProps = {
  nickname: string;
};

const CurrentBloodSugar = (props: CurrentBloodSugarProps) => {
  const [currentLevel, setCurrentLevel] = useState(0);
  const [currentStatus, setCurrentStatus] = useState('');

  const fetchBloodSugar = async () => {
    try {
      const bloodSugardata = await getCurrentBloodSugar(props.nickname);
      setCurrentLevel(bloodSugardata.bloodSugarLevel);
      setCurrentStatus(bloodSugardata.status);
    } catch (error) {
      console.error('Error fetching blood sugar data:', error);
    }
  };

  // 1분마다 fetchBloodSugar 호출
  useEffect(() => {
    fetchBloodSugar();
    const intervalId = setInterval(fetchBloodSugar, 60000);

    return () => clearInterval(intervalId);
  }, [props.nickname]);

  return (
    <div css={container}>
      <div
        css={bloodSugarCss}
        style={{
          borderColor:
            currentStatus === 'normal'
              ? '#17b68e'
              : currentStatus === 'high'
                ? '#FF1744'
                : currentStatus === 'low'
                  ? '#008999'
                  : '#17b68e',
        }}
      >
        <Typography color="dark" size="1.5" weight={600}>
          {currentLevel || '-'}
        </Typography>
      </div>
      <Typography color="dark" size="0.75" weight={500}>
        내 혈당
      </Typography>
    </div>
  );
};

export default CurrentBloodSugar;
