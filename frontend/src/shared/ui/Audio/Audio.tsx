import { SoundType, useAudioStore } from '@/shared/model/useAudioStore';
import { useEffect, useRef } from 'react';

interface AudioProps {
  audioSrc: string;
  type: SoundType;
  autoplay?: boolean;
  loop?: boolean;
}

const Audio = ({ audioSrc, type, autoplay, loop }: AudioProps) => {
  const audioRef = useRef<HTMLAudioElement | null>(null);
  const setAudioRef = useAudioStore((state) => state.setAudioRef);

  useEffect(() => {
    if (audioRef.current) {
      setAudioRef(type, audioRef.current); // 각 type에 맞게 ref를 추가
    }
  }, [setAudioRef, type]);

  return (
    <>
      <audio
        ref={audioRef}
        src={audioSrc}
        preload="auto"
        autoPlay={autoplay || false}
        loop={loop || false}
      />
    </>
  );
};

export default Audio;
