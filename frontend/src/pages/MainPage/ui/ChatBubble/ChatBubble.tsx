/** @jsxImportSource @emotion/react */

import { Typography } from '@/shared/ui/Typography';
import { container } from './ChatBubble.styles';
import { useChatWithMongddangStore } from '../../model/useChatWithMongddangStore';
import { useEffect, useState } from 'react';
import {
  getRandomDefaultMessage,
  mongddangStatusMessages,
} from '../../constants/mongddangDefaultMessages';

interface ChatBubbleProps {
  status: string;
}

const ChatBubble = ({ status }: ChatBubbleProps) => {
  const { displayMessage } = useChatWithMongddangStore();

  const [defaultMessage, setDefaultMessage] = useState<string>(
    getRandomDefaultMessage()
  );

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
      <Typography color="dark" size="1" weight={600}>
        <span>
          {displayMessage || filteredMongddangStatusMessage || defaultMessage}
        </span>
      </Typography>
    </div>
  );
};

export default ChatBubble;
