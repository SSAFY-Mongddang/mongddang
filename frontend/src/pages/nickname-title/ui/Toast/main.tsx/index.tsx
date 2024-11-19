/** @jsxImportSource @emotion/react */

import { toastCss } from "../styles";


const MainToast = () => {
  return (
    <div css={toastCss}>
      <div>🎉메인 칭호 설정에 성공하셨습니다.🎉</div>
    </div>
  );
};

export default MainToast;