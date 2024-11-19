/** @jsxImportSource @emotion/react */

import {
  alertImgCss,
  alertItemCss,
  medicineChipCss,
  medicineContentCss,
  medicineInfoCss,
  medicineItemCss,
  medicineTextCss,
  medicineTitleCss,
} from './MedicationItem.styles';
import { Typography } from '@/shared/ui/Typography';
import { mainIcons } from '@/pages/MainPage/constants/iconsData';
import { Chip } from '@/shared/ui/Chip';
import { MedicationItemType } from '../../types';

type MedicationItemProps = {
  medication: MedicationItemType;
};
const MedicationItem = (props: MedicationItemProps) => {
  const { medication } = props;
  const handleTimeStamp = (timestamp: Date) => {
    const date = new Date(timestamp); // timestamp를 Date 객체로 변환
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0'); // 월은 0부터 시작하므로 +1
    const day = String(date.getDate()).padStart(2, '0');

    return `${year}-${month}-${day}`;
  };
  return (
    <div css={medicineItemCss}>
      <div css={medicineTextCss}>
        <div css={medicineTitleCss}>
          <Typography color="dark" size="1" weight={600}>
            {medication.name}
          </Typography>
          <Chip
            border={1}
            color="primary"
            fontSize={0.75}
            fontWeight={600}
            css={medicineChipCss}
          >
            {medication.isFast === true ? '식사 인슐린' : '하루 인슐린'}
          </Chip>
        </div>
        <div css={medicineContentCss}>
          <Typography
            color="dark"
            size="0.75"
            weight={600}
            css={medicineInfoCss}
          >
            {medication.standards?.map((standard, index) => {
              // medication.standards가 null이 아닌 경우에만 map을 실행
              return <span key={index}>{standard.volume} 단위(U)</span>;
            })}
          </Typography>
          <Typography color="dark" size="0.75" weight={600}>
            {`${handleTimeStamp(medication.repeatStartTime!)} ~ ${handleTimeStamp(medication.repeatEndTime!)}`}
          </Typography>
          <div css={alertItemCss}>
            <img src={mainIcons.notification} alt="alert" css={alertImgCss} />
            <Typography color="dark" size="0.75" weight={600}>
              {medication.repeatTimes.map((time, index) => {
                return (
                  <span key={index}>
                    {time}
                    {index === medication.repeatTimes.length - 1 ? '' : ' | '}
                  </span>
                );
              })}
            </Typography>
          </div>
        </div>
      </div>
    </div>
  );
};

export default MedicationItem;
