/** @jsxImportSource @emotion/react */
import { HiOutlineChevronRight } from 'react-icons/hi';
import { Typography } from '@/shared/ui/Typography';
import { base } from './stylest';
import { Icon } from '@/shared/ui/Icon';
import { useNavigate } from 'react-router-dom';

interface ItemProps {
  title: string;
  ment: string;
  unit: string;
}

export const Item = ({ title, ment, unit }: ItemProps) => {
  const nav = useNavigate();
  return (
    <div css={base}>
      <div
        style={{
          display: 'flex',
          alignItems: 'center',
          justifyContent: 'space-between',
        }}
        onClick={() => nav('/report/detail/gmi')}
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
      <div style={{ display: 'flex', alignItems: 'flex-end' }}>
        <Typography color="dark" size="1.25" weight={600}>
          {ment}
        </Typography>
        <Typography
          color="dark"
          size="0.75"
          weight={500}
          style={{ marginLeft: '4px', marginBottom: '2px' }} // 아래로 살짝 이동
        >
          {unit}
        </Typography>
      </div>
    </div>
  );
};