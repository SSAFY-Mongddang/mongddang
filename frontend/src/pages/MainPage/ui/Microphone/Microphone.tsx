/** @jsxImportSource @emotion/react */

import {
  PropsWithChildren,
  useCallback,
  useEffect,
  useRef,
  useState,
} from 'react';
import { VoiceRecorder } from 'capacitor-voice-recorder';
import { talkWithMongddang } from '../../api/chatWithMongddangApi';
import { IconTypo } from '@/shared/ui/IconTypo';
import { mainIcons } from '../../constants/iconsData';
import {
  buttonNoneStyle,
  recordingBackDrop,
  recordingSign,
  waveMic,
} from './Microphone.styles';
import { Backdrop } from '@/shared/ui/Backdrop';
import { Typography } from '@/shared/ui/Typography';
import { Button } from '@/shared/ui/Button';
import { Icon } from '@/shared/ui/Icon';

const Microphone = ({ children }: PropsWithChildren) => {
  const audioRef = useRef<HTMLAudioElement | null>(null);

  const [isRecording, setIsRecording] = useState(false);

  const startRecording = useCallback(async () => {
    try {
      await VoiceRecorder.requestAudioRecordingPermission(); // 권한 요청
      await VoiceRecorder.hasAudioRecordingPermission(); // 권한 확인
      await VoiceRecorder.startRecording(); // 녹음 시작

      setIsRecording(true);
      console.log('-------record-------- 녹음 시작');
    } catch (error) {
      console.error('-------record-------- 녹음 시작 실패:', error);
    }
  }, []);

  const stopRecording = useCallback(async () => {
    try {
      // 녹음 종료
      const stopResult = await VoiceRecorder.stopRecording();

      // base64 데이터와 mimeType을 변수에 저장
      const base64Sound = stopResult.value.recordDataBase64;
      const mimeType = stopResult.value.mimeType;

      // Blob 객체를 생성하여 File 객체로 변환
      const byteCharacters = atob(base64Sound);
      const byteNumbers = new Array(byteCharacters.length);
      for (let i = 0; i < byteCharacters.length; i++) {
        byteNumbers[i] = byteCharacters.charCodeAt(i);
      }
      const byteArray = new Uint8Array(byteNumbers);
      const file = new Blob([byteArray], { type: mimeType });

      const jsonData = {
        audio: byteNumbers,
        filename: 'audioFile',
        mimeType: file.type,
      };

      // talkWithMongddang 함수 호출
      const response = await talkWithMongddang(jsonData);

      // base64 문자열을 Blob 객체로 변환
      const base64String = response.data;
      const binaryString = atob(base64String);
      const len = binaryString.length;
      const bytes = new Uint8Array(len);
      for (let i = 0; i < len; i++) {
        bytes[i] = binaryString.charCodeAt(i);
      }

      // Blob 객체 / Blob URL / Audio 객체 생성
      const blob = new Blob([bytes], { type: 'audio/wav' });
      const url = URL.createObjectURL(blob);
      const audio = new Audio(url);

      // 재생
      audio.play();

      // Audio 객체를 생성하여 audioRef에 저장
      audioRef.current = new Audio(`data:${mimeType};base64,${base64Sound}`);

      setIsRecording(false);
    } catch (error) {
      console.error('-------record--------녹음 종료 실패:', error);
    }
  }, []);

  const cancelRecording = useCallback(() => {
    VoiceRecorder.stopRecording();
    setIsRecording(false);
  }, []);

  useEffect(() => {
    const checkStatus = async () => {
      try {
        const result = await VoiceRecorder.getCurrentStatus();
        console.log('-------record--------현재 상태', result.status);
      } catch (error) {
        console.log(error);
      }
    };

    checkStatus();

    return () => {
      VoiceRecorder.stopRecording();
    };
  }, [isRecording]);

  return (
    <button css={buttonNoneStyle}>
      {children}
      <button css={buttonNoneStyle} onClick={startRecording}>
        <IconTypo
          icon={mainIcons.mic}
          fontSize="0.75"
          menu={<div>대화하기</div>}
        />
      </button>

      {isRecording && (
        <div css={recordingBackDrop}>
          <div css={recordingSign}>
            <Icon css={waveMic} size={14}>
              <img src={mainIcons.mic} alt="mic" />
            </Icon>
            <Typography
              color="light"
              size="1.75"
              weight={300}
              style={{ marginBottom: '0.5rem' }}
            >
              녹음 중입니다
            </Typography>
            <Button
              color="secondary"
              fontSize="1"
              variant="outlined"
              handler={stopRecording}
              style={{ marginBottom: '1rem' }}
            >
              대화 전송하기
            </Button>
            <Typography
              color="light"
              size="1"
              weight={300}
              style={{ marginBottom: '0.5rem' }}
              onClick={cancelRecording}
            >
              녹음 취소하기
            </Typography>
          </div>
          <Backdrop />
        </div>
      )}
    </button>
  );
};

export default Microphone;
