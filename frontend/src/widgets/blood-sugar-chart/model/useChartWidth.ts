import { useEffect, useState } from 'react';
import { CHART_CONFIG } from '../config';

export const useChartWidth = (dataLength: number = 0) => {
  const [chartWidth, setChartWidth] = useState<number>(CHART_CONFIG.MIN_WIDTH);

  useEffect(() => {
    setChartWidth(
      Math.max(
        CHART_CONFIG.MIN_WIDTH,
        Math.min(CHART_CONFIG.MAX_WIDTH, dataLength * CHART_CONFIG.POINT_WIDTH)
      )
    );
  }, [dataLength]);

  return chartWidth;
};
