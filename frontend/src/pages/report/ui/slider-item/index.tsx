/** @jsxImportSource @emotion/react */
import { Typography } from '@/shared/ui/Typography';
import { base } from '../item/stylest';
import { Slider } from '@/shared/ui/Slider';
import { SliderCss, containerCss } from './styles';
import { Icon } from '@/shared/ui/Icon';
import { HiOutlineChevronRight } from 'react-icons/hi';
import { useNavigate } from 'react-router-dom';

interface ItemProps {
  title: string;
  ment: string;
  unit: string;
  url: string;
}

export const SliderItem = ({ title, ment, unit, url }: ItemProps) => {
  const nav = useNavigate();
  return (
    <div css={base}>
      <div
        style={{
          display: 'flex',
          justifyContent: 'space-between',
          alignItems: 'center',
          width: '100%',
        }}
        onClick={() => nav(url)}
      >
        <Typography color="dark" size="1" weight={500}>
          {title}
        </Typography>
        <div
          style={{
            display: 'flex',
            alignItems: 'center',
          }}
        >
          <Typography color="dark" size="1" weight={500}>
            더보기
          </Typography>
          <Icon size={1.5}>
            <HiOutlineChevronRight />
          </Icon>
        </div>
      </div>
      <div css={containerCss}>
        <div style={{ display: 'flex', alignItems: 'flex-end' }}>
          <Typography color="dark" size="1.25" weight={600}>
            {ment}
          </Typography>
          <Typography
            color="dark"
            size="0.75"
            weight={500}
            style={{ marginLeft: '4px', marginBottom: '2px' }}
          >
            {unit}
          </Typography>
        </div>
        <Slider
          css={SliderCss}
          blueMent="권장"
          max={50}
          redMent="주의"
          standard={30}
          value={30}
        />
      </div>
    </div>
  );
};
