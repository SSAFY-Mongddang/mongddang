// 닉네임 유효성 검사
export const validateNickname = (nickname:string) => {
  const nicknameRegex = /^[a-zA-Z가-힣ㄱ-ㅎㅏ-ㅣ0-9\s]{1,10}$/;

  
  if (!nickname) {
    return '닉네임을 입력해주세요.';
  } else if (!nicknameRegex.test(nickname)) {
    return '닉네임은 10글자 이내로 작성해주세요.';
  }
  return '';
};