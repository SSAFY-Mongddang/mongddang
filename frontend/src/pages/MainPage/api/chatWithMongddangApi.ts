import { api } from '@/shared/api/interceptors';
import { AxiosResponse } from 'axios';

const START_GREETING = '안녕 반가워!';

interface StartChatWithMongddangResponse {
  prompt: string;
  message: string;
}

export const startChatWithMongddang = (): Promise<
  AxiosResponse<StartChatWithMongddangResponse>
> => {
  return api({
    method: 'POST',
    data: {
      message: START_GREETING,
    },
    url: '/api/chat/mongddang/start',
  })
    .then((res) => {
      console.log('startChatWithMongddang 응답', res.data);
      return res.data;
    })
    .catch((err) => {
      console.log(err);
      return err;
    });
};

export const chatWithMongddang = (
  message: string
): Promise<AxiosResponse<string>> => {
  return api({
    method: 'POST',
    url: '/api/chat/mongddang',
    data: {
      message,
    },
  })
    .then((res) => {
      console.log('chatWithMongddang 응답', res.data);
      return res.data;
    })
    .catch((err) => {
      console.log(err);
      return err;
    });
};
// curl --location --request POST 'https://clovaspeech-gw.ncloud.com/recog/v1/stt
// ?lang=Eng
// &assessment=true
// &utterance=Nice%20to%20meet%20you.
// &graph=true' \
// --header 'Content-Type: application/octet-stream' \
// --header 'X-CLOVASPEECH-API-KEY: {앱 등록 시 발급받은 Secret Key}' \
// --data '@{data}'

export const talkWithMongddang = (
  jsonData: object
  // file: Blob
): Promise<AxiosResponse<string>> => {
  // FormData 생성
  // const formData = new FormData();
  // formData.append('audioFile', file);
  const accessToken = import.meta.env.VITE_TEST_USER_ACCESS_TOKEN;

  return api({
    method: 'POST',
    url: '/api/chat/mongddang/talk',
    headers: {
      Authorization: `Bearer ${accessToken}`,
    },
    data: jsonData,
    // params: formData,
  })
    .then((res) => {
      console.log('talkWithMongddang 응답', res.data);
      return res.data;
    })
    .catch((err) => {
      console.log(err);
      return err;
    });
};
// export const talkWithMongddang = (file: Blob): Promise<AxiosResponse<any>> => {
//   // FormData 생성
//   const formData = new FormData();
//   formData.append('file', file);

//   return api({
//     method: 'POST',
//     headers: {
//       'Content-Type': 'application/octet-stream',
//       'X-CLOVASPEECH-API-KEY': import.meta.env.REACT_APP_CLOVASPEECH_API_KEY,
//     },
//     url: 'https://clovaspeech-gw.ncloud.com/recog/v1/stt',
//     data: formData,
//   })
//     .then((res) => {
//       console.log('chatWithMongddang 응답', res.data);
//       return res.data;
//     })
//     .catch((err) => {
//       console.log(err);
//       return err;
//     });
// };
