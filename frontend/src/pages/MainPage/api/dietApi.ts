import { api } from '@/shared/api/interceptors';

// 식사 종료하기
export const endEating = async () => {
  return await api({
    method: 'PATCH',
    url: '/api/record/meal/end',
  })
    .then((res) => {
      console.log(res.data);
      return res.data;
    })
    .catch((err) => {
      console.log(err);
    });
};

// 이미지 파일을 Base64로 변환하는 함수
const convertToBase64 = (file: File) => {
  return new Promise((resolve, reject) => {
    const reader = new FileReader();
    reader.onloadend = () => resolve(reader.result);
    reader.onerror = reject;
    reader.readAsDataURL(file);
  });
};

// 식단 저장 & 식사 시작하기
export const saveDiet = async (
  mealTime: string,
  image: File | null,
  content: string
) => {
  const diet = JSON.stringify(content.split(','));

  // 이미지가 있으면 Base64로 변환
  let base64Image = null;
  if (image) {
    base64Image = (await convertToBase64(image)) as string;
  }
  console.log('base64Image:', base64Image);

  const data = {
    mealTime,
    image: base64Image,
    content: diet,
  };

  console.log('전송할 데이터', data);

  return api({
    method: 'POST',
    url: '/api/record/meal/start',
    data,
  })
    .then((res) => {
      console.log('식사 시작됨', res.data);
      return res.data;
    })
    .catch((err) => {
      console.log('식사 실패함', err);
    });
};
