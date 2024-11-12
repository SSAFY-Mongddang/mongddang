import { Preferences } from '@capacitor/preferences';

// 루틴 상태 저장
export const setRoutine = async (routine: string) => {
  await Preferences.set({
    key: 'routine',
    value: routine,
  });
  console.log('저장됨',routine)
};

// 루틴 상태 조회
export const getRoutine = async ():Promise<string> => {
  const { value } = await Preferences.get({ key: 'routine' });
  return value || '';
};
