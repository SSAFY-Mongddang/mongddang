/** @jsxImportSource @emotion/react */

import { Modal } from '@/shared/ui/Modal';
import {
  inputContainer,
  modalContainer,
  modalContent,
  modalTopBar,
} from './DietModal.styles';
import { TopBar } from '@/shared/ui/TopBar';
import DietModalBtnGroup from '../DietModalBtnGroup/DietModalBtnGroup';
import DietImage from '../DietImage/DietImage';
import { TextField } from '@/shared/ui/TextField';
import { Button } from '@/shared/ui/Button';
import { useCallback, useEffect, useState } from 'react';
import { debounce } from 'lodash';
import { getTodayMeal, saveDiet } from '../../api/dietApi';
import { useStopwatchStore } from '../../model/useStopwatchStore';
import { useUserStore } from '@/entities/user/model';

type DietModalProps = {
  closeDietModal: () => void;
  changeRoutine: (currentRoutine: string) => void;
  handleAlert: (status: string) => void;
  handleBloodSugar: (bloodSugar: number) => void;
};

const DietModal = (props: DietModalProps) => {
  const [selectedMealTime, setSelectedMealTime] = useState('breakfast');
  const [isDisabled, setIsDisabled] = useState(true);
  const [diet, setDiet] = useState('');
  const [dietImgFile, setDietImgFile] = useState<File | null>(null);

  const nickname = useUserStore((state) => state.user?.nickname);

  const { startStopwatch } = useStopwatchStore();

  // 식사 타임 선택
  const handleBtnClick = async (info: string) => {
    setSelectedMealTime(info);
    const todayMeal = await getTodayMeal(info, nickname);
    if (todayMeal) {
      setDiet(todayMeal.content.join(', '));
    }
  };

  // 식단 텍스트 등록
  const debounceSaveDiet = useCallback(
    debounce((value: string) => {
      setDiet(value);
    }, 500),
    []
  );

  const handleInputDiet = (e: React.ChangeEvent<HTMLInputElement>) => {
    debounceSaveDiet(e.target.value);
  };

  // 식단 이미지 등록
  const handleDietImg = (file: File | null) => {
    if (file) {
      setDietImgFile(file);
    }
  };

  // 식단 저장 버튼 활성화
  useEffect(() => {
    const handleDisabledBtn = () => {
      if (diet !== '' || dietImgFile) {
        setIsDisabled(false);
      } else if (diet === '' && !dietImgFile) {
        setIsDisabled(true);
      }
    };
    return handleDisabledBtn();
  }, [diet, dietImgFile]);

  // 식단 저장
  const handleSaveDiet = async (
    selectedMealTime: string,
    dietImgFile: File | null,
    diet: string
  ) => {
    try {
      const response = await saveDiet(selectedMealTime, dietImgFile, diet);
      if (response.code === 200) {
        props.closeDietModal();
        props.changeRoutine('먹는 중');
        props.handleAlert('startRoutine');
        props.handleBloodSugar(response.data.bloodSugarLevel);
        startStopwatch();
      }
    } catch {
      console.log('에러');
    }
  };

  return (
    <div>
      <Modal css={modalContainer}>
        {/* 탑 바 */}
        <TopBar
          type="modal"
          css={modalTopBar}
          iconHandler={() => {
            props.closeDietModal();
          }}
        >
          지금 뭐 먹어?
        </TopBar>

        <div css={modalContent}>
          {/* 시간 버튼 4종 */}
          <DietModalBtnGroup
            selectedMealTime={selectedMealTime}
            handleBtnClick={handleBtnClick}
          />

          {/* 식단 이미지 삽입 */}
          <DietImage handleDietImg={handleDietImg} />

          {/* 식단 텍스트 입력 */}
          <TextField
            color="dark"
            defaultValue=""
            label=""
            maxRows={10}
            multiLine
            placeholder="뭐 먹는지 적어줘! (✨사진, 글 둘 중 하나는 꼭 해줘:))"
            type="text"
            variant="outlined"
            css={inputContainer}
            value={diet}
            onChange={handleInputDiet}
          />
          {/* 저장 버튼 */}
          <Button
            color="primary"
            fontSize="1.25"
            fullwidth
            isShadow
            scale="A200"
            variant="contained"
            handler={() => {
              handleSaveDiet(selectedMealTime, dietImgFile, diet);
            }}
            disabled={isDisabled}
          >
            저장하고 밥 먹기 시작
          </Button>
        </div>
      </Modal>
    </div>
  );
};

export default DietModal;
