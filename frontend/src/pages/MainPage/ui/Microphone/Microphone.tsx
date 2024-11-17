/** @jsxImportSource @emotion/react */

import { useCallback, useEffect, useRef, useState } from 'react';
import { VoiceRecorder } from 'capacitor-voice-recorder';
import { talkWithMongddang } from '../../api/chatWithMongddangApi';
// import { CapacitorHttp, HttpOptions, HttpResponse } from '@capacitor/core';
// import { base } from '@/shared/ui/Typography/Typography.styles';

const Microphone = () => {
  const audioRef = useRef<HTMLAudioElement | null>(null);
  const [isRecording, setIsRecording] = useState(false);
  // const [isPlaying, setIsPlaying] = useState(false);
  // const [recordedAudio, setRecordedAudio] = useState<>(null);

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
      // console.log('녹음 시작');
    } catch (error) {
      console.error('-------record--------녹음 시작 실패:', error);
    }
  }, []);

  const stopRecording = useCallback(async () => {
    try {
      // 녹음 종료
      const stopResult = await VoiceRecorder.stopRecording();
      // console.log('-------record--------녹음 종료', stopResult.value);

      // base64 데이터와 mimeType을 변수에 저장
      const base64Sound = stopResult.value.recordDataBase64;
      // console.log('base64Sound:', base64Sound);
      const mimeType = stopResult.value.mimeType;

      // Blob 객체를 생성하여 File 객체로 변환
      const byteCharacters = atob(base64Sound);
      // console.log('byteCharacters:', byteCharacters);
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

      // console.log('-------record--------파일 생성 완료, file:', file);
      // console.log('byteArray', byteArray);

      // talkWithMongddang 함수 호출
      const response = await talkWithMongddang(jsonData);
      // console.log('API 응답:', response);
      // for (const key in response) {
      //   console.log('res key:', key);
      //   console.log('res value:', response[key]);
      // }
      // console.log('response.response:', response.response);
      // const responseRs = response.response;
      // console.log('responseRs:', responseRs);
      // for (const key in responseRs) {
      //   console.log('Rs key:', key);
      //   console.log('Rs value:', responseRs[key]);
      // }
      // const rsData = responseRs.data;
      // console.log('rsData:', rsData);
      // for (const key in rsData) {
      //   console.log('rsData key:', key);
      //   console.log('rsData value:', rsData[key]);
      // }

      // Base64 디코딩
      // console.log('-------record--------Base64 디코딩 시작');
      const base64String = response.data;
      // console.log('Base64:', base64String);
      const binaryString = atob(base64String);
      // console.log('Binary:', binaryString);
      const len = binaryString.length;
      // console.log('Length:', len);
      const bytes = new Uint8Array(len);
      // console.log('-------record--------Base64 디코딩 완료');

      for (let i = 0; i < len; i++) {
        bytes[i] = binaryString.charCodeAt(i);
      }
      // Blob 객체 생성
      const blob = new Blob([bytes], { type: 'audio/wav' });
      // console.log('-------record--------Blob 생성 완료');

      // Blob URL 생성
      const url = URL.createObjectURL(blob);
      // console.log('-------record--------Blob URL 생성 완료');

      // Audio 객체 생성 및 재생
      // console.log('-------record--------녹음 종료, 음성 재생');
      const audio = new Audio(url);
      audio.play();

      // Audio 객체를 생성하여 audioRef에 저장
      audioRef.current = new Audio(`data:${mimeType};base64,${base64Sound}`);
      // setRecordedAudio({ base64: base64Sound, mimeType });

      setIsRecording(false);
      // console.log('-------record--------녹음 종료');
    } catch (error) {
      console.error('-------record--------녹음 종료 실패:', error);
    }
  }, []);

  // const playAudio = useCallback(() => {
  //   if (audioRef.current) {
  //     audioRef.current
  //       .play()
  //       .then(() => {
  //         setIsPlaying(true);
  //         console.log('오디오 재생 시작');
  //       })
  //       .catch((playError) => {
  //         console.error('오디오 재생 실패:', playError);
  //       });
  //   }
  // }, []);

  // const pauseAudio = useCallback(() => {
  //   if (audioRef.current) {
  //     audioRef.current.pause();
  //     setIsPlaying(false);
  //     console.log('오디오 재생 중지');
  //   }
  // }, []);

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
      {/* {recordedAudio && (
        <div>
          <h2>녹음된 오디오</h2>
          <button onClick={isPlaying ? pauseAudio : playAudio}>
            {isPlaying ? '재생 중지' : '재생'}
          </button>
        </div>
      )} */}
    </div>
  );
};

export default Microphone;
