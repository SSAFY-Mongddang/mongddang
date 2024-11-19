import { Card, Text, Stack } from '@mantine/core';
import { LineChart } from '@mantine/charts';
import useBloodSugarStore from "@/pages/report/store/bloodSugarStore";

interface WeeklyData {
  date: string;
  average: number;
}

type ChartDataType = {
  date: string;
  value: number;
};

export function DetailAverageChart() {
  const weeklyData = useBloodSugarStore((state) => state.weeklyData as WeeklyData[]);
  
  if (!weeklyData) return <div>데이터가 없습니다.</div>;

  const formatDate = (dateString: string): string => {
    const date = new Date(dateString);
    return `${date.getMonth() + 1}.${date.getDate()}`; // MM.DD 형식
  };
  
  const chartData: ChartDataType[] = weeklyData.map((item) => ({
    date: formatDate(item.date),
    value: item.average
  }));

  return (
    <Card p="md" radius="md" withBorder>
      <Stack gap="md">
        <Text size="lg" fw={500}>주간 평균 혈당</Text>
        
        <div style={{ height: '300px' }}>
          <LineChart
            h={300}
            data={chartData}
            dataKey="date"
            series={[
              { name: 'value', color: '#2196f3' }
            ]}
            gridAxis="xy"
            strokeWidth={2}
            dotProps={{
              r: 4,
              stroke: '#2196f3',
              strokeWidth: 2,
              fill: '#2196f3'
            }}
            referenceLines={[
              {
                y: 100,
                color: '#4A4A4A',
                strokeDasharray: '5 5',
                label: '100 mg/dl 이하 권장'
              }
            ]}
            curveType="linear"
            tickLine="none"
            withLegend={false}
          />
        </div>
      </Stack>
    </Card>
  );
}