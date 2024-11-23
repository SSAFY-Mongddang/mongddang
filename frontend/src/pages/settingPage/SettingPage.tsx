/** @jsxImportSource @emotion/react */

import { TopBar } from '@/shared/ui/TopBar';
import {
  container,
  formBox,
  li,
  line,
  lineBox,
  settingContent,
} from './SettingPage.styles';
import { useNavigate } from 'react-router-dom';
import { Toggle } from '@/shared/ui/Toggle';
import { Typography } from '@/shared/ui/Typography';
import { useAudioStore } from '@/shared/model/useAudioStore';
import { useState } from 'react';
import { TextField } from '@/shared/ui/TextField';

const SettingPage = () => {
  const navigate = useNavigate();

  const { bgm, bubble } = useAudioStore();

  console.log('bgm', bgm.audioRef?.volume);

  const [bgmState, setBgmState] = useState<boolean>(!bgm.isMuted);
  const [bubbleState, setBubbleState] = useState<boolean>(!bubble.isMuted);

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
      <TopBar
        type="iconpage"
        iconHandler={() => {
          navigate('/menu');
        }}
      >
        설정
      </TopBar>
      <ul css={settingContent}>
        <div css={lineBox}>
          <p style={{ margin: 0 }}>혈당 기준</p>
          <line css={line} />
        </div>
        <li css={li}>
          <Typography
            color="dark"
            size="1.25"
            weight={500}
            style={{ marginRight: 'auto' }}
          >
            저혈당 알림
          </Typography>
          <div css={formBox}>
            <TextField
              color="primary"
              defaultValue="90"
              label=""
              type="text"
              variant="standard"
              style={{ width: '50px', padding: 0 }}
            />
            <span>이하</span>
          </div>
        </li>
        <li css={li}>
          <Typography
            color="dark"
            size="1.25"
            weight={500}
            style={{ marginRight: 'auto' }}
          >
            고혈당 알림
          </Typography>
          <div css={formBox}>
            <TextField
              color="primary"
              defaultValue="250"
              label=""
              type="text"
              variant="standard"
              style={{ width: '50px', padding: 0 }}
            />
            <span>이상</span>
          </div>
        </li>
      </ul>
      <ul css={settingContent}>
        <div css={lineBox}>
          <p style={{ margin: 0 }}>알림음</p>
          <line css={line} />
        </div>
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
      </ul>
    </div>
  );
};

export default SettingPage;
