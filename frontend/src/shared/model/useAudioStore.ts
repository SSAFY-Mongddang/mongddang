import { create } from 'zustand';

type BgmInfo = {
  audioRef: HTMLAudioElement | null;
  play: () => void;
  toggleMute: () => void;
  isMuted: boolean;
};

type BubbleInfo = {
  audioRef: HTMLAudioElement[];
  currIdx: number;
  play: () => void;
  toggleMute: () => void;
  isMuted: boolean;
};

export type SoundType = 'bubble' | 'bgm';

type AudioStoreInfo = {
  bgm: BgmInfo;
  bubble: BubbleInfo;

  setAudioRef: (type: SoundType, ref: HTMLAudioElement) => void;
  toggleMuteBgm: () => void;
  toggleMuteEffect: () => void;
};

const POOL_SIZE = 1; // 오디오 풀의 크기

export const useAudioStore = create<AudioStoreInfo>((set, get) => ({
  bgm: {
    audioRef: null,
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
      if (type === 'bgm') {
        const bgmInfo: BgmInfo = {
          audioRef: ref,
          isMuted: state.bgm.isMuted,
          play: () => {
            const audio = get().bgm.audioRef;
            if (audio) {
              audio.currentTime = 0;
              audio.play();
            }
          },
          toggleMute: () => {
            set((state) => {
              const newIsMuted = !state.bgm.isMuted;
              if (state.bgm.audioRef) {
                state.bgm.audioRef.volume = newIsMuted ? 0 : 1;
              }
              return {
                ...state,
                bgm: {
                  ...state.bgm,
                  isMuted: newIsMuted,
                },
              };
            });
          },
        };
        return {
          ...state,
          bgm: bgmInfo,
        };
      } else {
        const existRefs = state.bubble.audioRef;
        if (existRefs.length >= POOL_SIZE) return state;

        const newRefs = [...existRefs, ref];

        const bubbleInfo: BubbleInfo = {
          audioRef: newRefs,
          currIdx: state.bubble.currIdx,
          isMuted: state.bubble.isMuted,
          play: () => {
            const audioPool = get().bubble.audioRef;
            const currIdx = get().bubble.currIdx;

            const audio = audioPool[currIdx];
            if (audio) {
              audio.currentTime = 0;
              audio.play();
            }

            set((state) => ({
              ...state,
              bubble: {
                ...state.bubble,
                currIdx: (currIdx + 1) % POOL_SIZE,
              },
            }));
          },
          toggleMute: () => {
            set((state) => {
              const newIsMuted = !state.bubble.isMuted;
              state.bubble.audioRef.forEach((audio) => {
                audio.volume = newIsMuted ? 0 : 1;
              });
              return {
                ...state,
                bubble: {
                  ...state.bubble,
                  isMuted: newIsMuted,
                },
              };
            });
          },
        };

        return {
          ...state,
          bubble: bubbleInfo,
        };
      }
    });
  },

  toggleMuteBgm: () => {
    get().bgm.toggleMute();
  },

  toggleMuteEffect: () => {
    get().bubble.toggleMute();
  },
}));
