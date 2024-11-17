/** @jsxImportSource @emotion/react */

import { useCallback, useEffect, useRef, useState } from 'react';
import { VoiceRecorder } from 'capacitor-voice-recorder';

const Microphone = () => {
  const audioRef = useRef<HTMLAudioElement | null>(null);
  const [isRecording, setIsRecording] = useState(false);
  const [isPlaying, setIsPlaying] = useState(false);
  const [recordedAudio, setRecordedAudio] = useState<{
    base64: string;
    mimeType: string;
  } | null>(null);

  const startRecording = useCallback(async () => {
    try {
      // 권한 요청
      const permissionResult =
        await VoiceRecorder.requestAudioRecordingPermission();
      console.log('-------record-------- 권한 요청', permissionResult.value);

      // 권한 확인
      const permissionCheck = await VoiceRecorder.hasAudioRecordingPermission();
      console.log(
        '-------record-------- 권한이 있는지 확인',
        permissionCheck.value
      );

      // 녹음 시작
      const startResult = await VoiceRecorder.startRecording();
      console.log('-------record--------녹음 시작', startResult.value);

      setIsRecording(true);
      console.log('녹음 시작');
    } catch (error) {
      console.error('-------record--------녹음 시작 실패:', error);
    }
  }, []);

  const stopRecording = useCallback(async () => {
    try {
      // 녹음 종료
      const stopResult = await VoiceRecorder.stopRecording();
      console.log('-------record--------녹음 종료', stopResult.value);

      // base64 데이터와 mimeType을 변수에 저장
      const base64Sound = stopResult.value.recordDataBase64;
      const mimeType = stopResult.value.mimeType;

      // Audio 객체를 생성하여 audioRef에 저장
      audioRef.current = new Audio(`data:${mimeType};base64,${base64Sound}`);
      setRecordedAudio({ base64: base64Sound, mimeType });

      setIsRecording(false);
      console.log('-------record--------녹음 종료');
    } catch (error) {
      console.error('-------record--------녹음 종료 실패:', error);
    }
  }, []);

  const playAudio = useCallback(() => {
    if (audioRef.current) {
      audioRef.current
        .play()
        .then(() => {
          setIsPlaying(true);
          console.log('오디오 재생 시작');
        })
        .catch((playError) => {
          console.error('오디오 재생 실패:', playError);
        });
    }
  }, []);

  const pauseAudio = useCallback(() => {
    if (audioRef.current) {
      audioRef.current.pause();
      setIsPlaying(false);
      console.log('오디오 재생 중지');
    }
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
  }, [startRecording, stopRecording]);

  return (
    <div>
      <h1>녹음기</h1>
      <button onClick={isRecording ? stopRecording : startRecording}>
        {isRecording ? '녹음 중지' : '녹음 시작'}
      </button>
      {recordedAudio && (
        <div>
          <h2>녹음된 오디오</h2>
          <button onClick={isPlaying ? pauseAudio : playAudio}>
            {isPlaying ? '재생 중지' : '재생'}
          </button>
        </div>
      )}
    </div>
  );
};

export default Microphone;
