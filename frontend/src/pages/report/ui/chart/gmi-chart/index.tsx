import React from 'react';
import { Progress, Paper, Text, Stack } from '@mantine/core';

interface DiabetesRange {
  min: number;
  max: number;
  status: string;
  color: string;
}

interface StatusInfo {
  status: string;
  color: string;
}

interface DiabetesIndicatorProps {
  /** 당화혈색소 수치 (4.0 ~ 10.0 사이의 값) */
  currentValue: number;
}

const RANGES: DiabetesRange[] = [
  { min: 4.0, max: 5.6, status: '건강한 상태', color: '#4DABF7' },
  { min: 5.7, max: 6.4, status: '당뇨 전단계', color: '#FFA94D' },
  { min: 6.5, max: 10.0, status: '당뇨병', color: '#FF6B6B' }
];

const MIN_VALUE = 4.0;
const MAX_VALUE = 10.0;

const DiabetesIndicator: React.FC<DiabetesIndicatorProps> = ({ currentValue = 5.0 }) => {
  // 현재 값의 위치를 백분율로 계산 (0-100%)
  const getPercentage = (value: number): number => {
    const percentage = ((value - MIN_VALUE) / (MAX_VALUE - MIN_VALUE)) * 100;
    return Math.min(Math.max(percentage, 0), 100); // 0-100 사이 값으로 제한
  };

  // 현재 상태 확인
  const getCurrentStatus = (value: number): StatusInfo => {
    const range = RANGES.find(r => value >= r.min && value <= r.max);
    return range 
      ? { status: range.status, color: range.color }
      : { status: '범위 초과', color: '#868E96' };
  };

  const currentStatus = getCurrentStatus(currentValue);
  const currentPosition = getPercentage(currentValue);

  return (
    <Paper p="md" className="w-full max-w-xl mx-auto">
      <Stack gap="md">
        <Text c="dark" fz="xl" fw={700} ta="center">
          당뇨병 진단 기준
        </Text>

        <div className="relative">
          {/* Progress bar */}
          <Progress.Root size="xl">
            <Progress.Section value={26.7} color="#4DABF7" />
            <Progress.Section value={11.7} color="#FFA94D" />
            <Progress.Section value={61.6} color="#FF6B6B" />
          </Progress.Root>
          
          {/* 현재 값 마커 */}
          <div 
            className="absolute top-0 -mt-2 transform -translate-x-1/2"
            style={{ left: `${currentPosition}%` }}
          >
            <div className="w-4 h-4 bg-black rounded-full" />
          </div>
        </div>

        {/* 범위 레이블 */}
        {/* <div className="flex justify-between mt-4">
          {RANGES.map((range, index) => (
            <div key={index} className="text-center">
              <Text fw={700} style={{ color: range.color }}>
                {range.status}
              </Text>
              <Text fz="sm">
                {range.min}~{range.max}%
              </Text>
            </div>
          ))}
        </div> */}

        {/* 현재 상태 표시 */}
        <Text ta="center" fz="lg" style={{ color: currentStatus.color }}>
          현재 수치: <Text span fw={700}>{currentValue.toFixed(1)}%</Text>
          <br />
          상태: <Text span fw={700}>{currentStatus.status}</Text>
        </Text>
      </Stack>
    </Paper>
  );
};

export default DiabetesIndicator;