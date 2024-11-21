/** @jsxImportSource @emotion/react */

import { TopBar } from '@/shared/ui/TopBar';
import { Item } from './ui/item';
import { SliderItem } from './ui/slider-item';
import { GptContent } from './ui/gpt-content';
import { useNavigate } from 'react-router-dom';
import { WeekChart } from './ui/chart/week-chart';
import { useQuery } from '@tanstack/react-query';
import { getreport } from './api/api';
import useBloodSugarStore from './store/bloodSugarStore';
import { useEffect } from 'react';
import Loading from '@/shared/ui/Loading';
import { useUserStore } from '@/entities/user/model';

export const Report = () => {
  const nav = useNavigate();
  const setWeeklyData = useBloodSugarStore((state) => state.setWeeklyData);
  const { data, isLoading, error } = useQuery({
    queryKey: ['bloodSugarReport'],
    queryFn: getreport,
  });
  const getUserInfo = useUserStore((state) => state.getUserInfo);

  useEffect(() => {
    console.log(
      'API Response data:',
      data?.data?.data?.glucoseMeasurementItmeList
    );

    const measurements = data?.data?.data?.glucoseMeasurementItmeList;
    if (measurements && Array.isArray(measurements)) {
      // 데이터 타입 변환이 필요한 경우
      const transformedData = measurements.map((item) => ({
        measurementTime: item.measurementTime,
        bloodSugarLevel: Number(item.bloodSugarLevel),
      }));

      setWeeklyData(transformedData);
    }
  }, [data, setWeeklyData]);

  if (isLoading) {
    console.log('Loading data...');
    return <Loading/>;
  }

  if (error) {
    console.log('Error:', error);
    return <div>Error occurred!</div>;
  }

  const measurementList = data?.data?.data?.glucoseMeasurementItmeList || [];
  console.log('Final measurement list:', measurementList);

  
  const userInfo = getUserInfo();
  const navHandler = () => {
    if (userInfo.user?.role === 'child') nav('/menu');
    if (userInfo.user?.role === 'protector') nav('/protector-main');
  };

  return (
    <div>
      <TopBar type="iconpage" iconHandler={navHandler}>
        주간 리포트
      </TopBar>
      <WeekChart data={measurementList} />
      <Item
        title="혈당관리지표(GMI)"
        ment={Number(data?.data?.data?.gmi || 0).toFixed(1)}
        unit="%"
      />
      <SliderItem
        title="이번주 평균 혈당"
        max={400}
        standard={100}
        ment={Number(data?.data?.data?.abg || 0).toFixed(1)}
        unit="mg/dl"
        url="/report/detail/mean"
      />
      <SliderItem
        title="혈당 변동성"
        max={100}
        standard={36}
        ment={Number(data?.data?.data?.cv || 0).toFixed(1)}
        unit="%"
        url="/report/detail/gv"
      />
      <SliderItem
        title="목표 범위 내 비율"
        max={100}
        standard={20}
        ment={Number(data?.data?.data?.tir || 0).toFixed(1)}
        unit="%"
        url="/report/detail/tir"
      />
      <GptContent
        data={{
          gmi: Number(data?.data?.data?.gmi || 0),
          abg: Number(data?.data?.data?.abg || 0),
          cv: Number(data?.data?.data?.cv || 0),
          tir: Number(data?.data?.data?.tir || 0),
        }}
      />
    </div>
  );
};
