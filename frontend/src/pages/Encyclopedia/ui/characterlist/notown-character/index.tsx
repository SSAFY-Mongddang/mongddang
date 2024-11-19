/** @jsxImportSource @emotion/react */
import { IconTypo } from '@/shared/ui/IconTypo';
import { containerCss } from './styles';
import { ICharacterData } from '@/pages/Encyclopedia/model/types';
import { formatId, noCharacterImages } from '@/pages/Encyclopedia/model/no-mongddang-img';
interface OwnCharacterProps {
  data: ICharacterData;
  
}
export const Notowncharacter = ({ data }: OwnCharacterProps) => {
  const imageKey = formatId(data.id);
  const imagePath = noCharacterImages[imageKey];
  return (
    <>
      <div css={containerCss}>
        <IconTypo
          fontSize="1"
          icon={imagePath}
          menu={data.name}
          size={5}
        />
      </div>
    </>
  );
};
