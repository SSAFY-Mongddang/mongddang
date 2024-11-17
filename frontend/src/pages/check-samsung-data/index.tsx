/** @jsxImportSource @emotion/react */
import { TopBar } from '@/shared/ui/TopBar';
import { base, containerCss} from './ui/styles';
import { Chip } from '@/shared/ui/Chip';
import { imgCss } from '../Encyclopedia/ui/styles';
import space from '../../assets/img/space.png';
import { Typography } from '@/shared/ui/Typography';
import { Button } from '@/shared/ui/Button';
import { useNavigate } from 'react-router-dom';
import { useUserStore } from '@/entities/user/model';
import { registerPlugin } from '@capacitor/core';


export interface ForegroundPlugin {
  startForeground(): Promise<{ message: string }>;
  stopForeground(): Promise<{ message: string}>;
}

export const Foreground = registerPlugin<ForegroundPlugin>('Foreground')


export const CheckSamsungData = () => {
  const nav = useNavigate();
  const getUserInfo = useUserStore((state) => state.getUserInfo);

  const user = getUserInfo().user;

  return (
    <div>
      <div>
      </div>
      <TopBar type="iconpage" iconHandler={() => nav('/menu')}>
        삼성데이터 조회
      </TopBar>
      <div css={base}>
      </div>
      <img css={imgCss} src={space} alt="배경 이미지" />
      <div css={containerCss}>
        <Chip border={1} color="primary" fontSize={0.8} fontWeight={600}>
          혈당 모니터링
        </Chip>
        <div
          style={{ display: 'flex', width:'100%', flexDirection: 'column', gap: '0.5rem', alignItems:'center' }}
        >
          <div style={{display:'flex', justifyContent:'space-between', width:'70%'}}>
          </div>
          <Typography color="dark" size="1" weight={700}>
            5분마다 혈당이 체크됩니다. 
          </Typography>
        </div>
        <div
          style={{ display: 'flex', flexDirection: 'column', gap: '0.5rem' }}
        >
        <Button
          handler={() =>
            nav('/nickname/edit', {
              state: { nickname: user?.nickname },
            })
          }
          color="primary"
          fontSize="1.25"
          variant="contained"
          fullwidth
        >
          닉네임 수정
        </Button>
        </div>
      </div>
    </div>
  );
};