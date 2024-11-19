// 타입스크립트에서 import.meta.env를 제대로 쓰기 위해서는 다음과 같이 ImportMetaEnv와 ImportMeta 인터페이스를 정의해주어야 합니다.
// https://khj0426.tistory.com/238
// https://vitejs.dev/guide/env-and-mode
/// <reference types="vite/client" />

interface ImportMetaEnv {
  readonly VITE_GOOGLE_WEB_CLIENT_ID: string;
  readonly VITE_TEST_USER_ACCESS_TOKEN: string;
  readonly VITE_TEST_PROTECTOR_ACCESS_TOKEN: string;
}

interface ImportMeta {
  readonly env: ImportMetaEnv;
}
