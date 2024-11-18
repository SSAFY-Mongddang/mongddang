import { registerPlugin } from "@capacitor/core";

const payload: DateTimePayload = {
  dateTime: "2024-11-17T15:30:00",
};

console.log(payload)

// ISO 8601 검사 함수 (런타임에서 확인)
function isValidISO8601(dateTime: string): boolean {
  const iso8601Regex = /^\d{4}-\d{2}-\d{2}T\d{2}:\d{2}:\d{2}$/;
  return iso8601Regex.test(dateTime);
}

console.log(isValidISO8601)

// DateTimePayload 인터페이스 정의
export interface DateTimePayload {
  dateTime: string; // ISO 8601 날짜/시간 형식
}

// GetThisTimeBloodGlucoseResponse 인터페이스 정의
export interface GetThisTimeBloodGlucoseResponse {
  status: string; // 성공 또는 실패 상태
  datetime: string; // 요청된 날짜/시간
  [key: string]: any; // 추가적인 응답 속성 허용 (필요에 따라 제거 가능)
}

// BloodGlucosePlugin 인터페이스 정의
export interface BloodGlucosePlugin {
  getThisTimeBloodGlucose(options: { datetime: string }): Promise<{ datetime: string; status: string }>;
}

// BloodGlucosePlugin을 Capacitor에 등록
export const BloodGlucosePlugin = registerPlugin<BloodGlucosePlugin>(
  "BloodGlucosePlugin"
);