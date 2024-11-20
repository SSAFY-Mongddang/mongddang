/** @jsxImportSource @emotion/react */

import { TopBar } from '@/shared/ui/TopBar';
import {
  container,
  content,
  inputContentCss,
  inputContentItemCss,
  medicationAddBtnCss,
  medicationTypeGroupCss,
  medicationTypeItemCss,
  textFieldCss,
  titieCss,
  topbarCss,
} from './MedicationAdd.styles';
import { useNavigate } from 'react-router-dom';
import { TextField } from '@/shared/ui/TextField';
import { Typography } from '@/shared/ui/Typography';
import ToggleNone from '@/assets/img/medication_none.svg.svg';
import ToggleSelected from '@/assets/img/medication_selected.svg.svg';
import { useCallback, useState } from 'react';
import MedicationStandard from './ui/MedicationStandard/MedicationStandard';
import MedicationPeriod from './ui/MedicationPeriod/MedicationPeriod';
import MedicationTime from './ui/MedicationTime/MedicationTime';
import { Button } from '@/shared/ui/Button';
import MedicationOnce from './ui/MedicationOnce/MedicationOnce';
import { debounce } from 'lodash';
import { useMedicationAddStore } from './model/useMedicationAddStore';
import { saveMedication } from './api/saveMedicationApi';
import { useMedicationStandardStore } from './model/useMedicationStandardStore';
import { useMedicationTimeStore } from './model/useMedicationTimeStore';
import { useQueryClient } from '@tanstack/react-query';

const MedicationAdd = () => {
  const navigate = useNavigate();
  const queryClient = useQueryClient();
  // FIXME: login 시 setUserInfo로 닉네임을 할당해야 이곳에서 활용 가능하다.
  const {
    name,
    nickname,
    image,
    repeatStartTime,
    repeatEndTime,
    repeatTimes,
    standards,
    setMedicationInfo,
    resetStandard,
    initializeAdd,
  } = useMedicationAddStore();
  const { resetStandardField } = useMedicationStandardStore();
  const { resetTimeField } = useMedicationTimeStore();
  const [isFast, setIsFast] = useState(false);
  const handleToggle = () => {
    setIsFast(!isFast);
    resetStandard();
    resetStandardField();
    resetTimeField();
  };

  const debounceInput = useCallback(
    debounce((value: string) => {
      setMedicationInfo(value);
    }, 500),
    []
  );

  const handleInput = (e: React.ChangeEvent<HTMLInputElement>) => {
    debounceInput(e.target.value);
  };

  const handleSaveMed = async () => {
    const medicationData = {
      name,
      nickname,
      image,
      repeatStartTime,
      repeatEndTime,
      isFast,
      repeatTimes,
      standards,
    };
    console.log("handleSaveMed's medicationData");
    console.log(medicationData);
    await saveMedication(medicationData);
    queryClient.invalidateQueries({
      queryKey: ['medicationList', nickname ?? ''],
    });
  };
  return (
    <div css={container} className='medication-add'>
      <TopBar
        type="iconpage"
        css={topbarCss}
        iconHandler={() => {
          navigate('/medication');
        }}
      >
        약 등록
      </TopBar>
      <div css={content}>
        <div css={medicationTypeGroupCss} className='medication-type'>
          <div css={titieCss}>
            <Typography color="dark" size="1" weight={600}>
              약 종류
            </Typography>
          </div>
          <div css={medicationTypeItemCss} onClick={handleToggle}>
            <img src={isFast ? ToggleNone : ToggleSelected} alt="toggle" />
            <Typography color="dark" size="1" weight={500}>
              하루 인슐린
            </Typography>
          </div>
          <div css={medicationTypeItemCss} onClick={handleToggle}>
            <img src={isFast ? ToggleSelected : ToggleNone} alt="toggle" />
            <Typography color="dark" size="1" weight={500}>
              식사 인슐린
            </Typography>
          </div>
        </div>
        <div css={inputContentCss} className='medication-input'>
          <div css={inputContentItemCss} className='medication-input-items'>
            <Typography color="dark" size="1" weight={600}>
              약 이름
            </Typography>
            <TextField
              color="primary"
              defaultValue=""
              label=""
              placeholder="약 이름이 뭐야? ex) 리조텍"
              type="text"
              variant="standard"
              css={textFieldCss}
              onChange={handleInput}
            />
          </div>
          <MedicationPeriod />
          <MedicationTime />
          {isFast ? <MedicationStandard /> : <MedicationOnce />}
        </div>
        <Button
          color="primary"
          fontSize="1.25"
          isShadow
          variant="contained"
          handler={async () => {
            await handleSaveMed();
            initializeAdd();
            resetStandardField();
            resetTimeField();
            navigate('/medication');
          }}
          css={medicationAddBtnCss}
        >
          약 저장 하기
        </Button>
      </div>
    </div>
  );
};

export default MedicationAdd;
