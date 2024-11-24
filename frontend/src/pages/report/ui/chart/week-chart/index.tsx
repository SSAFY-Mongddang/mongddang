import { LineChart } from '@mantine/charts';
import { Text } from '@mantine/core';

interface IData {
  measurementTime: string;
  bloodSugarLevel: number;
}

interface WeekChartProps {
  data: IData[];
}

export const WeekChart = ({ data }: WeekChartProps) => {
  const HIGH_THRESHOLD = 250;
  const LOW_THRESHOLD = 90;

  const calculatePercentages = () => {
    const total = data.length;
    const highCount = data.filter(
      (item) => item.bloodSugarLevel > HIGH_THRESHOLD
    ).length;
    const lowCount = data.filter(
      (item) => item.bloodSugarLevel < LOW_THRESHOLD
    ).length;
    const normalCount = total - highCount - lowCount;

    return {
      high: Math.round((highCount / total) * 100),
      normal: Math.round((normalCount / total) * 100),
      low: Math.round((lowCount / total) * 100),
    };
  };

  const percentages = calculatePercentages();

  const processedData = data.map((item) => {
    const value = item.bloodSugarLevel;
    const date =
      new Date(item.measurementTime)
        .toLocaleDateString('ko-KR', {
          month: '2-digit',
          day: '2-digit',
        })
        .replace(/\./g, '')
        .split(' ')
        .join('.') + '.';

    return {
      date,
      value,
      normal: value >= LOW_THRESHOLD && value <= HIGH_THRESHOLD ? value : null,
      high: value > HIGH_THRESHOLD ? value : null,
      low: value < LOW_THRESHOLD ? value : null,
      highThreshold: HIGH_THRESHOLD,
      lowThreshold: LOW_THRESHOLD,
    };
  });

  const chartHeight = 300;
  const yAxisMax = HIGH_THRESHOLD + 20;

  return (
    <div style={{ position: 'relative', margin: '1rem' }}>
      {/* Y축 레이블 */}
      <div
        style={{
          position: 'absolute',
          left: '0',
          top: '0',
          height: '100%',
          pointerEvents: 'none',
          zIndex: 1,
        }}
      >
        {/* 고혈당 영역 레이블 */}
        <div
          style={{
            marginTop: '6vh', // 고혈당 위치를 조금 더 위로
          }}
        >
          <Text fw={500} size="0.9rem" style={{ color: '#ff9800' }}>
            고혈당
          </Text>
          <Text fw={500} size="0.9rem" style={{ color: '#ff9800' }}>
            {percentages.high}%
          </Text>
        </div>

        {/* 범위 내 영역 레이블 */}
        <div
          style={{
            marginTop: '13vh',
          }}
        >
          <Text fw={500} size="0.9rem" style={{ color: '#2196f3' }}>
            범위 내
          </Text>
          <Text
            fw={500}
            size="0.9rem"
            style={{ color: '#2196f3', marginLeft: '4px' }}
          >
            {percentages.normal}%
          </Text>
        </div>

        {/* 저혈당 영역 레이블 */}
        <div
          style={{
            marginTop: '10vh', // 저혈당 위치를 조금 더 아래로
          }}
        >
          <Text fw={500} size="0.9rem" style={{ color: '#f44336' }}>
            저혈당
          </Text>
          <Text
            fw={500}
            size="0.9rem"
            style={{ color: '#f44336', marginLeft: '4px' }}
          >
            {percentages.low}%
          </Text>
        </div>
      </div>

      <LineChart
        h={chartHeight}
        data={processedData}
        dataKey="date"
        series={[
          { name: 'high', color: '#ff9800' },
          { name: 'highThreshold', color: '#ff9800', strokeDasharray: '5 5' },
          { name: 'lowThreshold', color: '#f44336', strokeDasharray: '5 5' },
          { name: 'normal', color: '#2196f3' },
          { name: 'low', color: '#f44336' },
        ]}
        withDots={false}
        connectNulls={false}
        curveType="linear"
        withLegend={false}
        gridAxis="xy"
        tooltipAnimationDuration={200}
        withTooltip
        withXAxis={false}
        // xAxisProps={{
        //   tickMargin: 15,
        //   interval: Math.floor(processedData.length / 10),
        //   style: { fontSize: '12px' },
        // }}
        yAxisProps={{
          tickMargin: 1,
          domain: [0, yAxisMax],
          ticks: [LOW_THRESHOLD, HIGH_THRESHOLD],
          style: { fontSize: '12px' },
        }}
        style={{
          backgroundColor: 'white',
        }}
      />
    </div>
  );
};

export default WeekChart;
