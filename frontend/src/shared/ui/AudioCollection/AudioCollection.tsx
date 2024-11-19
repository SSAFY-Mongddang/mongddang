import React from 'react';
import Audio from '../Audio/Audio';
import bgm from '@/assets/audios/bgm-low-decibel.mp3';
import bubble from '@/assets/audios/bubble.mp3';

const AudioCollectionComponent: React.FC = () => {
  return (
    <div style={{ visibility: 'hidden' }}>
      <Audio audioSrc={bgm} type="bgm" autoplay loop />
      <Audio audioSrc={bubble} type="bubble" />
    </div>
  );
};

// AudioCollection을 레이지 로드할 수 있도록 내보냅니다.
const AudioCollection = React.lazy(() =>
  Promise.resolve({ default: AudioCollectionComponent })
);

export default AudioCollection;
