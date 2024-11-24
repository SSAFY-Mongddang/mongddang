/** @jsxImportSource @emotion/react */

import { PropsWithChildren, TouchEvent, useState } from 'react';
import {
  circleEffect,
  effectArea,
  effectCssInitialize,
  imageSize,
} from './TouchEffect.styles';
import touchEffectImage from '@/assets/img/icon/main_star-sm.png';

interface TouchEffect {
  x: number;
  y: number;
  id: number; // 각 터치에 고유 ID를 부여합니다.
}

const TouchEffect = ({ children }: PropsWithChildren) => {
  const [touches, setTouches] = useState<TouchEffect[]>([]);

  const handleTouch = (event: TouchEvent<HTMLDivElement>) => {
    console.log(event);
    console.log('clientX:', event.touches[0].clientX);
    console.log('clientY:', event.touches[0].clientY);
    console.log('pageX:', event.touches[0].pageX);
    console.log('pageY:', event.touches[0].pageY);
    console.log('screenX:', event.touches[0].screenX);
    console.log('screenY:', event.touches[0].screenY);
    console.log('--------------------------------------------');
    const touchX = event.touches[0].clientX;
    const touchY = event.touches[0].clientY;
    const id = Date.now(); // 현재 시간을 ID로 사용

    setTouches((prevTouches) => [...prevTouches, { x: touchX, y: touchY, id }]);
  };

  return (
    <div onTouchStart={handleTouch} css={effectArea}>
      {touches.map((touch) => (
        <div
          key={touch.id} // id를 key로 사용하여 각 터치 효과의 고유성을 유지
          css={circleEffect}
          style={{
            top: touch.y,
            left: touch.x,
          }}
        >
          <img src={touchEffectImage} alt="touch effect" css={imageSize} />
        </div>
      ))}
      <div css={effectCssInitialize}>{children}</div>
    </div>
  );
};

export default TouchEffect;
