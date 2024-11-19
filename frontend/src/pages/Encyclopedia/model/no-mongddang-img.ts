import noMongddang1 from '../../../assets/img/no_mongddang/no_mongddang01.png'
import noMongddang2 from '../../../assets/img/no_mongddang/no_mongddang02.png';
import noMongddang3 from '../../../assets/img/no_mongddang/no_mongddang03.png';
import noMongddang4 from '../../../assets/img/no_mongddang/no_mongddang04.png';
import noMongddang5 from '../../../assets/img/no_mongddang/no_mongddang05.png';
import noMongddang6 from '../../../assets/img/no_mongddang/no_mongddang06.png';
import noMongddang7 from '../../../assets/img/no_mongddang/no_mongddang07.png';
import noMongddang8 from '../../../assets/img/no_mongddang/no_mongddang08.png';
import noMongddang9 from '../../../assets/img/no_mongddang/no_mongddang09.png';
import noMongddang10 from '../../../assets/img/no_mongddang/no_mongddang10.png';
import noMongddang11 from '../../../assets/img/no_mongddang/no_mongddang11.png';
import noMongddang12 from '../../../assets/img/no_mongddang/no_mongddang12.png';
import noMongddang13 from '../../../assets/img/no_mongddang/no_mongddang13.png';
import noMongddang14 from '../../../assets/img/no_mongddang/no_mongddang14.png';
import noMongddang15 from '../../../assets/img/no_mongddang/no_mongddang15.png';
import noMongddang16 from '../../../assets/img/no_mongddang/no_mongddang16.png';
import noMongddang17 from '../../../assets/img/no_mongddang/no_mongddang17.png';
import noMongddang18 from '../../../assets/img/no_mongddang/no_mongddang18.png';

export const noCharacterImages: { [key: string]: string } = {
  '01': noMongddang1,
  '02': noMongddang2,
  '03': noMongddang3,
  '04': noMongddang4,
  '05': noMongddang5,
  '06': noMongddang6,
  '07': noMongddang7,
  '08': noMongddang8,
  '09': noMongddang9,
  '10': noMongddang10,
  '11': noMongddang11,
  '12': noMongddang12,
  '13': noMongddang13,
  '14': noMongddang14,
  '15': noMongddang15,
  '16': noMongddang16,
  '17': noMongddang17,
  '18': noMongddang18,
};

export const formatId = (id: number): string => {
  return id.toString().padStart(2, '0');
};
