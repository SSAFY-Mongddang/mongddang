/** @jsxImportSource @emotion/react */

import { Typography } from '@/shared/ui/Typography';
import { container } from './ChatBubble.styles';
import { useChatWithMongddangStore } from '../../model/useChatWithMongddangStore';
import { FormEvent, useEffect, useState } from 'react';
import {
  getRandomDefaultMessage,
  mongddangStatusMessages,
} from '../../constants/mongddangDefaultMessages';
import { TextField } from '@/shared/ui/TextField';
import Microphone from '../Microphone/Microphone';

interface ChatBubbleProps {
  status: string;
}

const ChatBubble = ({ status }: ChatBubbleProps) => {
  const { displayMessage, startChat, sendChatMessage, finishChat } =
    useChatWithMongddangStore();

  const [chatInput, setChatInput] = useState<string>('');
  const [isChatStarted, setIsChatStarted] = useState<boolean>(false);
  const [defaultMessage, setDefaultMessage] = useState<string>(
    getRandomDefaultMessage()
  );

  const handleStartChat = async () => {
    await startChat();
    setIsChatStarted(true);
  };
  // 대화 종료하기 버튼을 누르면 대화가 종료됨
  const handleFinishChat = async () => {
    await finishChat();
    setIsChatStarted(false);
  };

  const handleSendChatMessage = async (e: FormEvent) => {
    e.preventDefault();
    setChatInput('');
    sendChatMessage(chatInput);
  };

  // 수행 중인 상태가 존재한다면 해당 상태에 맞는 문구를 띄움
  const filteredMongddangStatusMessage =
    status &&
    mongddangStatusMessages.filter((message) => message.status === status)[0]
      ?.message;

  // 메시지를 주기적으로 변경
  useEffect(() => {
    const changeInterval =
      Math.floor(Math.random() * (20000 - 10000 + 1)) + 10000; // 10초 ~ 20초 사이에 한 번씩 변경

    const updateMessage = () => {
      setDefaultMessage(getRandomDefaultMessage());
    };

    // 초기 메시지 설정
    updateMessage();

    // 주기적으로 메시지 변경
    setInterval(() => {
      updateMessage();
    }, changeInterval);

    // 컴포넌트가 언마운트 될 때 interval 제거
    return () => {
      clearInterval(changeInterval);
    };
  }, []);

  return (
    <div css={container} style={{ display: 'flex', flexDirection: 'column' }}>
      <Microphone />

      <Typography color="dark" size="1" weight={600}>
        <span>
          {displayMessage || filteredMongddangStatusMessage || defaultMessage}
        </span>
      </Typography>

      {isChatStarted && (
        <form onSubmit={(e) => handleSendChatMessage(e)}>
          <TextField
            color="primary"
            defaultValue=""
            label="몽땅과 대화하기"
            maxRows={10}
            placeholder="안녕!"
            type="text"
            variant="outlined"
            value={chatInput}
            onChange={(e) => setChatInput(e.target.value)}
          />
        </form>
      )}
      <Typography
        color="primary"
        size="0.75"
        weight={600}
        onClick={isChatStarted ? handleFinishChat : handleStartChat}
      >
        {isChatStarted ? (
          <span>대화 종료하기</span>
        ) : (
          <span>대화 시작하기</span>
        )}
      </Typography>
    </div>
  );
};

export default ChatBubble;
