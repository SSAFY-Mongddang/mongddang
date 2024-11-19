import { useState, useEffect } from 'react';

const useMinimumLoading = (isLoading: boolean, minimumLoadingTime = 350) => {
  const [shouldShowLoading, setShouldShowLoading] = useState(false);

  useEffect(() => {
    let timer: NodeJS.Timeout;

    if (isLoading) {
      setShouldShowLoading(true);
    } else if (shouldShowLoading) {
      // 로딩이 끝났지만 아직 로딩 표시 중일 때
      timer = setTimeout(() => {
        setShouldShowLoading(false);
      }, minimumLoadingTime);
    }

    return () => {
      if (timer) {
        clearTimeout(timer);
      }
    };
  }, [isLoading, minimumLoadingTime, shouldShowLoading]);

  return shouldShowLoading;
};

export default useMinimumLoading;
