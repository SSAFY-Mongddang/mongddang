import { create } from 'zustand';

type AudioInfo = {
  audioRef: HTMLAudioElement[];
  currIdx: number;
  play: () => void;
  toggleMute: () => void;
  isMuted: boolean;
};

export type SoundType = 'bubble' | 'bgm';

type AudioStoreInfo = {
  bgm: AudioInfo;
  bubble: AudioInfo;

  setAudioRef: (type: SoundType, ref: HTMLAudioElement) => void;
  toggleMuteBgm: () => void;
  toggleMuteEffect: () => void;
};

const POOL_SIZE = 1; // 오디오 풀의 크기

export const useAudioStore = create<AudioStoreInfo>((set, get) => ({
  bgm: {
    audioRef: [],
    currIdx: 0,
    play: () => {},
    toggleMute: () => {},
    isMuted: false,
  },
  bubble: {
    audioRef: [],
    currIdx: 0,
    play: () => {},
    toggleMute: () => {},
    isMuted: false,
  },

  setAudioRef: (type, ref) => {
    set((state) => {
      const existRefs = state[type].audioRef;
      if (existRefs.length >= POOL_SIZE) return state;

      const newRefs = [...existRefs, ref];

      const audioInfo: AudioInfo = {
        audioRef: newRefs,
        currIdx: state[type].currIdx,
        isMuted: state[type].isMuted,

        play: () => {
          const audioPool = get()[type].audioRef;
          const currIdx = get()[type].currIdx;

          const audio = audioPool[currIdx];
          if (audio) {
            audio.currentTime = 0;
            audio.play();
          }

          set((state) => ({
            ...state,
            [type]: {
              ...state[type],
              currIdx: (currIdx + 1) % POOL_SIZE,
            },
          }));
        },
        toggleMute: () => {
          set((state) => {
            const newIsMuted = !state[type].isMuted;
            state[type].audioRef.forEach((audio) => {
              audio.volume = newIsMuted ? 0 : 1;
            });
            return {
              ...state,
              [type]: {
                ...state[type],
                isMuted: newIsMuted,
              },
            };
          });
        },
      };

      return {
        ...state,
        [type]: audioInfo,
      };
    });
  },

  toggleMuteBgm: () => {
    get().bgm.toggleMute();
  },

  toggleMuteEffect: () => {
    get().bubble.toggleMute();
  },
}));
