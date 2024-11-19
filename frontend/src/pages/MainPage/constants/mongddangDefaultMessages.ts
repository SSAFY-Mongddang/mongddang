const mongddangBaseMessages = [
  '그거 알아? 어... 어 뭐라고 말하려 했더라?',
  '오늘은 무엇을 찾아 탐험을 떠나 볼까~',
  '귀여운게 정말 좋아.',
  '너와 함께하는 순간마다 행복해!',
  '사랑스러운 하루가 되길 바래~',
  '내 마음 속 보물들을 찾아보자!',
  '미소가 세상을 밝히는 마법이야!',
  '내가 고양이였다면, 하루 종일 햇볕에서 뒹굴고 싶어!',
  '내 마음 속에는 항상 초콜릿 폭풍이 휘몰아쳐!',
  '내 꿈은 언젠가 피자와 친구가 되는 거야!',
  '너와 함께라면 양치기 개구리도 콘서트를 열 수 있어!',
  '내 친구 몽땅을 찾아줘! 별가루를 모아서 해낼 수 있을 거야!',
];

// 랜덤 메시지 선택
export const getRandomDefaultMessage = () => {
  const randomIndex = Math.floor(Math.random() * mongddangBaseMessages.length);
  return mongddangBaseMessages[randomIndex];
};

export const mongddangStatusMessages = [
  { status: '먹는 중', message: '얌냠냠... 얌냠냠...' },
  { status: '운동 중', message: '으쌰! 으쌰! 후! 후!' },
  { status: '자는 중', message: 'z z z . . .' },
];

export type MongddangStatusMessages =
  (typeof mongddangStatusMessages)[number]['status'];
