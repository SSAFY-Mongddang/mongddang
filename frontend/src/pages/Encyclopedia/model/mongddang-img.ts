import mongddang1 from '../../../assets/img/mongddang/mongddang01.png';
import mongddang2 from '../../../assets/img/mongddang/mongddang02.png';
import mongddang3 from '../../../assets/img/mongddang/mongddang03.png';
import mongddang4 from '../../../assets/img/mongddang/mongddang04.png';
import mongddang5 from '../../../assets/img/mongddang/mongddang05.png';
import mongddang6 from '../../../assets/img/mongddang/mongddang06.png';
import mongddang7 from '../../../assets/img/mongddang/mongddang07.png';
import mongddang8 from '../../../assets/img/mongddang/mongddang08.png';
import mongddang9 from '../../../assets/img/mongddang/mongddang09.png';
import mongddang10 from '../../../assets/img/mongddang/mongddang10.png';
import mongddang11 from '../../../assets/img/mongddang/mongddang11.png';
import mongddang12 from '../../../assets/img/mongddang/mongddang12.png';
import mongddang13 from '../../../assets/img/mongddang/mongddang13.png';
import mongddang14 from '../../../assets/img/mongddang/mongddang14.png';
import mongddang15 from '../../../assets/img/mongddang/mongddang15.png';
import mongddang16 from '../../../assets/img/mongddang/mongddang16.png';
import mongddang17 from '../../../assets/img/mongddang/mongddang17.png';
import mongddang18 from '../../../assets/img/mongddang/mongddang18.png';

export const characterImages: { [key: string]: string } = {
  '01': mongddang1,
  '02': mongddang2,
  '03': mongddang3,
  '04': mongddang4,
  '05': mongddang5,
  '06': mongddang6,
  '07': mongddang7,
  '08': mongddang8,
  '09': mongddang9,
  '10': mongddang10,
  '11': mongddang11,
  '12': mongddang12,
  '13': mongddang13,
  '14': mongddang14,
  '15': mongddang15,
  '16': mongddang16,
  '17': mongddang17,
  '18': mongddang18,
};

export const formatId = (id: number): string => {
  return id.toString().padStart(2, '0');
};