/** @jsxImportSource @emotion/react */

import { TopBar } from '@/shared/ui/TopBar';
import { Item } from './ui/item';
import { SliderItem } from './ui/slider-item';
import { GptContent } from './ui/gpt-content';
import { useNavigate } from 'react-router-dom';

export const Report = () => {
  const nav = useNavigate();
  return (
    <div>
      <TopBar type="iconpage" iconHandler={()=>nav('/menu')}>주간 리포트</TopBar>
      <Item title="혈당관리지표(GMI)" ment="내용" unit="%" />
      <SliderItem title="이번주 평균 혈당" ment="내용" unit="%" url = '/report/detail/mean'/>
      <SliderItem title="혈당 변동성" ment="내용" unit="mg/dl" url = '/report/detail/gv'/>
      <SliderItem title="목표 범위 내 비율" ment="내용" unit="%" url = '/report/detail/tir'/>
      <GptContent />
    </div>
  );
};
