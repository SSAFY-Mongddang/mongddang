export const handleDateChange = (
  value: string,
  type: 'year' | 'month' | 'day',
  setState: (value: string) => void
): string => {
  let onlyNumber = value.replace(/[^0-9]/g, '');

  // 연도 처리
  if (type === 'year') {
    if (onlyNumber.length > 4) {
      onlyNumber = onlyNumber.slice(0, 4);
    }

    if (onlyNumber.length === 4) {
      const year = parseInt(onlyNumber, 10);
      if (year < 1924) {
        onlyNumber = '1924';
      } else if (year > 2024) {
        onlyNumber = '2024';
      }
    }
  }

  // 월 처리
  else if (type === 'month') {
    if (onlyNumber.length > 2) {
      onlyNumber = onlyNumber.slice(0, 2);
    }

    if (onlyNumber.length === 2) {
      const month = parseInt(onlyNumber, 10);
      if (month < 1) {
        onlyNumber = '01';
      } else if (month > 12) {
        onlyNumber = '12';
      } else if (month < 10) {
        onlyNumber = `0${month}`;
      }
    }
  }

  // 일 처리
  else if (type === 'day') {
    if (onlyNumber.length > 2) {
      onlyNumber = onlyNumber.slice(0, 2);
    }

    if (onlyNumber.length === 2) {
      const day = parseInt(onlyNumber, 10);
      if (day < 1) {
        onlyNumber = '01';
      } else if (day > 31) {
        onlyNumber = '31';
      } else if (day < 10) {
        onlyNumber = `0${day}`;
      }
    }
  }

  // 최종 상태 업데이트
  setState(onlyNumber);
  return onlyNumber; // 모든 경우에서 값을 반환하도록 수정
};
