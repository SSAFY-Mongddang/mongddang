import { css } from "@emotion/react";

export const containerCss = css`
  position: fixed;
  top: 55px; /* 화면 상단에 위치 */
  left: 65%; /* 수평 중앙 정렬 */
  transform: translateX(-50%); /* 중앙 정렬을 위한 변환 */
  z-index: 1000;
  text-align: center;
  width: 100%; /* 자동 너비 */
  padding: 20px; /* 패딩 추가 */
  transition: transform 0.5s ease-in-out, opacity 0.5s ease-in-out; /* 애니메이션 효과 */
`;

export const toastCss = css`
  position: fixed;
  top: 4.6875rem;
  left: 1.5625rem;
  transform: translateX(-40%);
  z-index: 100;
  background-color: #fff;
  border: 0.0625rem solid #ccc;
  border-radius: 0.5rem;
  padding: 1.25rem;
  opacity: 0;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  animation: fadeIn 0.5s forwards, fadeOut 0.5s forwards 4.5s; 
  
  @keyframes fadeIn {
    from {
      opacity: 0;
      transform: translateY(-20px);
    }
    to {
      opacity: 1;
      transform: translateY(0);
    }
  }

  @keyframes fadeOut {
    from {
      opacity: 1;
      transform: translateY(0);
    }
    to {
      opacity: 0;
      transform: translateY(-20px);
    }
  }
`