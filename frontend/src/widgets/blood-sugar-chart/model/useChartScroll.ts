import { useEffect, RefObject } from 'react';

export const useChartScroll = (
  viewportRef: RefObject<HTMLDivElement>,
  hasData: boolean
) => {
  useEffect(() => {
    const timer = setTimeout(() => {
      if (viewportRef.current) {
        viewportRef.current.scrollTo({
          left: viewportRef.current.scrollWidth,
          behavior: 'smooth',
        });
      }
    }, 50);
    return () => clearTimeout(timer);
    /**
     * viewportRef의 경우 반드시 추가해야 하는 것은 아니다.
     * useRef로 생성된 ref 객체는 컴포넌트의 전체 생명주기 동안 동일한 객체 참조를 유지한다. 
     * 즉, ref 객체 자체는 절대 변경되지 않는다.
     * ref의 .current 값은 변할 수 있지만, 
     * 이는 리액트의 렌더링 시스템 밖에서 일어나는 변경이므로 의존성으로 추적할 필요가 없다.
     */
  }, [hasData, viewportRef]);
};