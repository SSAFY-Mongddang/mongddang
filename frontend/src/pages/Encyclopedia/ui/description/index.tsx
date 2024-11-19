/** @jsxImportSource @emotion/react */
import { Icon } from '@/shared/ui/Icon';
import { base, containerCss } from './styles';
import { Typography } from '@/shared/ui/Typography';
import { ReactNode } from 'react';
import collection from '../../../../assets/img/page/collection.png'

interface DescriptionProps {
  children: ReactNode;
}

export const Description = ({ children }: DescriptionProps) => {
  return (
    <div css={base}>
      <div css={containerCss}>
        <Typography color="dark" size="0.75" weight={600}>
          {children}
        </Typography>
      </div>
      <Icon size={7}>
        <img alt="icon-1" src={collection} />
      </Icon>
    </div>
  );
};
