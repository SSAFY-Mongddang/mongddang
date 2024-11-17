/** @jsxImportSource @emotion/react */
import { IconTypo } from '@/shared/ui/IconTypo';
import { containerCss, newCss } from './styles';
import { Typography } from '@/shared/ui/Typography';
import { ICharacterData } from '@/pages/Encyclopedia/model/types';
import { characterImages, formatId } from '@/pages/Encyclopedia/model/mongddang-img';

interface OwnCharacterProps {
  data: ICharacterData;
}

export const Newcharacter = ({ data }: OwnCharacterProps) => {
  const imageKey = formatId(data.id);
  const imagePath = characterImages[imageKey];
  return (
    <div css={containerCss}>
      <div style={{ position: 'relative', width: '100%', height: '100%' }}>
        <IconTypo
          fontSize="1"
          icon={imagePath}
          menu={data.name}
          size={5}
        />
        <Typography style={{background:'#fff', borderRadius:'1rem'}} color="primary" size="1" weight={600} css={newCss}>
          New!!
        </Typography>
      </div>
    </div>
  );
};
