import { LineChart } from '@mantine/charts';
import { useRef } from 'react';
import { Center, ScrollArea } from '@mantine/core';
import { useChartScroll } from '../model';
import { useChartWidth } from '../model/useChartWidth';
import { CHART_CONFIG } from '../config';
import { Bloodsugar } from '@/shared/api/blood-sugar';
import chartStyle from './style.module.css';
import { timeConverter } from '../lib';

interface BloodSugarChartProps {
  data: Bloodsugar[] | undefined;
}

export const BloodSugarChart = ({ data }: BloodSugarChartProps) => {
  const viewportRef = useRef<HTMLDivElement | null>(null);

//   // mount 시 linechart의 가장 오른쪽으로 이동
  useChartScroll(viewportRef, !!data);

  // 그래프 너비값 동적 할당
  const chartWidth = useChartWidth(data?.length);

  return (
    <>
      {data && (
        <Center classNames={{ root: chartStyle.root }}>
          <ScrollArea
            w={CHART_CONFIG.SCROLL_WIDTH}
            h={CHART_CONFIG.SCROLL_HEIGHT}
            viewportRef={viewportRef}
          >
            <LineChart
              h={CHART_CONFIG.DEFAULT_HEIGHT}
              w={chartWidth}
              data={timeConverter(data)}
              dataKey="measurementTime"
              curveType="bump"
              strokeWidth={3}
              withLegend
              legendProps={{ verticalAlign: 'bottom', height: 50 }}
              series={[
                {
                  name: 'bloodSugarLevel',
                  label: '혈당 수치',
                  color: 'indigo.6',
                },
              ]}
              dotProps={{ r: 4, strokeWidth: 1.5, stroke: '#fff' }}
              activeDotProps={{ r: 6, strokeWidth: 1.5, fill: '#fff' }}
              yAxisProps={{ domain: [30, 250] }}
              strokeDasharray="10 10"
              referenceLines={[
                { y: 50, label: '저혈당 위험', color: 'red.6' },
                { y: 140, label: '고혈당 위험', color: 'yellow.5' },
              ]}
              style={{ margin: '30px' }}
              tooltipProps={{
                active: undefined,
                isAnimationActive: true,
                animationEasing: 'ease-in-out',
                animationDuration: 300,
              }}
            />
          </ScrollArea>
        </Center>
      )}
    </>
  );
};
