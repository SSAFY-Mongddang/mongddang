/** @jsxImportSource @emotion/react */

import { Typography } from '../Typography';
import {
  fillCss,
  remainingFillCss,
  sliderContainerCss,
  thumbCss,
} from './Slider.styles';
import { SliderProps } from './Sllider.types';

export const Slider = ({
  max = 50,
  standard = 30,
  value = 30,
  blueMent = '권장',
  redMent = '주의',
  ...props
}: SliderProps) => {
  return (
    <div css={sliderContainerCss} {...props}>
      <div css={fillCss(standard, max)} />
      <div css={remainingFillCss(standard, max)} />
      <div css={thumbCss(value, standard, max)} />

      <div
        style={{
          position: 'absolute',
          left: '0%',
          bottom: '-25px',
        }}
      >
        <Typography color="primary" scale="400" size="1" weight={600}>
          {blueMent}
        </Typography>
      </div>
      <div
        style={{
          position: 'absolute',
          right: '0%',
          bottom: '-25px',
        }}
      >
        <Typography color="danger" scale="400" size="1" weight={600}>
          {redMent}
        </Typography>
      </div>
    </div>
  );
};
