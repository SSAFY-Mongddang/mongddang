import { create } from 'zustand';
import { random } from 'lodash';
import {
  chatWithMongddang,
  startChatWithMongddang,
} from '../api/chatWithMongddangApi';

type ChatWithMongddangInfo = {
  // 보여줄 메시지가 없다면 '' 으로 설정, ''이라면 기본적으로 출력할 메시지 설정
  displayMessage: string; // 보여줄 메시지
  setDisplayMessage: (message: string) => void;

  startChat: () => void;
  sendChatMessage: (message: string) => void;
  finishChat: () => void;

  // 해당 시간만큼 보여준다.
  displayDuration: number; // 보여줄 시간
  reduceTimeTimeout: NodeJS.Timeout | null;
  setDisplayDuration: () => void;

  // (최대 20개만 저장) [,필요하다면 A,B 화자 정하는 로직 구현]
  history: string[]; // 이전 대화 내역
  addHistory: (message: string) => void;
};

export const useChatWithMongddangStore = create<ChatWithMongddangInfo>(
  (set, get) => ({
    displayMessage: '',

    displayDuration: 0,
    reduceTimeTimeout: null,

    history: [],

    setDisplayMessage(message) {
      set({ displayMessage: message });
    },

    // 대화를 시작하면 몽땅의 성격을 히스토리 0번째에 저장
    async startChat() {
      const response = await startChatWithMongddang();
      const { prompt, message } = response.data;

      get().setDisplayMessage(message); // 대화 출력
      get().addHistory('프롬프트' + prompt); // 대화 내역에 추가
      get().addHistory('\n상대의 대화' + message); // 대화 내역에 추가
      get().setDisplayDuration(); // 대화 출력 시간 설정
    },

    async sendChatMessage(message) {
      if (get().history.length === 0) {
        console.log('시작된 대화가 없습니다.');
        return;
      }

      const response = await chatWithMongddang(
        get().history.join('') +
          message +
          '마지막 나의 질문(대화)에만 대답을 해 줘. 인사는 매번 하지 않아도 돼'
      );

      console.log(
        "get().history.join('') + message",
        get().history.join('') + message
      );
      console.log('response.data', response.data);

      get().setDisplayMessage(response.data); // 대화 출력
      get().addHistory(
        '\n나의 대화:' + message + '\n상대의 대화' + response.data
      ); // 대화 내역에 추가
      get().setDisplayDuration(); // 대화 출력 시간 설정
    },

    finishChat() {
      set({ history: [], reduceTimeTimeout: null });
      get().setDisplayMessage('재밌는 시간이었어! 다음에 또 만나자!');
    },

    setDisplayDuration() {
      // 이전 타이머 유무
      const { reduceTimeTimeout: beforeReduceTimeTimeout } = get();

      // 이전 타이머가 존재하면 클리어
      if (beforeReduceTimeTimeout) {
        clearTimeout(beforeReduceTimeTimeout as NodeJS.Timeout);
      }

      // 20초 ~ 50초 랜덤 출력
      const duration = random(20000, 50000);

      // 새로운 타이머 설정
      const newReduceTimeTimeout = setTimeout(() => {
        set({ displayMessage: '' });
      }, duration);

      // 클리어 할 수 있도록 저장
      set({ reduceTimeTimeout: newReduceTimeTimeout });
    },

    addHistory(message) {
      const { history } = get();

      // 최대 20개만 저장
      if (history.length >= 20) {
        set({ history: [history[0], ...history.slice(2)] });
      } else {
        set({ history: [...history, message] });
      }
    },
  })
);
