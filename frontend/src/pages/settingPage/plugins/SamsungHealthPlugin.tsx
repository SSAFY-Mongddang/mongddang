import { registerPlugin } from '@capacitor/core';

export interface SamsungHealthPlugin {
  /**
   * 특정 건강 데이터 권한 요청
   * @param options 권한 요청 옵션
   * @returns 권한 부여 상태
   */
  requestHealthPermission(options: HealthPermissionOptions): Promise<HealthPermissionResult>;
  checkHealthPermission(
    options?: { healthDataType?: string }
  ): Promise<{granted: boolean}>;
}

/** 권한 요청 옵션 */
export interface HealthPermissionOptions {
  healthDataType: string; // 요청할 건강 데이터의 타입 (e.g., "blood_glucose", "heart_rate")
}

/** 권한 요청 결과 */
export interface HealthPermissionResult {
  granted: boolean; // 권한 부여 여부
}

const SamsungHealth = registerPlugin<SamsungHealthPlugin>('SamsungHealthPlugin');

export default SamsungHealth;

