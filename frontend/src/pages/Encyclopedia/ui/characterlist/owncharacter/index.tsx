/** @jsxImportSource @emotion/react */
import { IconTypo } from '@/shared/ui/IconTypo';
import { containerCss } from './styles';
import { ICharacterData } from '@/pages/Encyclopedia/model/types';
import {
  characterImages,
  formatId,
} from '@/pages/Encyclopedia/model/mongddang-img';
import { Icon } from '@/shared/ui/Icon';
import star from '../../../../../assets/img/icon/main_star.png';

interface OwnCharacterProps {
  data: ICharacterData;
}

export const Owncharacter = ({ data }: OwnCharacterProps) => {
  const imageKey = formatId(data.id);
  const imagePath = characterImages[imageKey];

  return (
    <>
      <div css={containerCss}>
        <div style={{ position: 'relative', width: '100%', height: '100%' }}>
          <IconTypo fontSize="1" icon={imagePath} menu={data.name} size={5} />
          {data.isMain ? (
            <Icon size={3} style={{ 
              position: 'absolute', 
              top: '-0.5rem', 
              right: '-0.5rem'
            }}>
              <img alt="icon-0" src={star} />
            </Icon>
          ) : null}
        </div>
      </div>
    </>
  );
};

