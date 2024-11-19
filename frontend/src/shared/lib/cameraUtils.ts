import { Camera, CameraResultType, PermissionStatus } from '@capacitor/camera';
import { Filesystem } from '@capacitor/filesystem';

export const useCamera = () => {
  let file: File | null = null;
  let imageUrl: string | '' = '';

  // 카메라 권한 확인 함수
  const checkCamera = async (): Promise<boolean> => {
    const permissions: PermissionStatus = await Camera.checkPermissions();
    // 권한 승인 시
    if (permissions.camera === 'granted') {
      return true;
    }
    // 권한 승인 안됐을 시 요청
    const requestPermissions = await Camera.requestPermissions();
    return requestPermissions.camera === 'granted';
  };

  const openCamera = async () => {
    // 1. 카메라 권한 확인
    const hasPermission = await checkCamera();

    // 2. 권한이 있다면 사진 촬영 & 갤러리 조회
    if (hasPermission) {
      const image = await Camera.getPhoto({
        quality: 50,
        resultType: CameraResultType.Uri,
      });

      imageUrl = image.webPath ? image.webPath : '';

      //3.  파일 형식으로 변환
      const readFile = await Filesystem.readFile({
        path: imageUrl.replace(/_capacitor_file_/, ''),
      });

      file = new File([readFile.data as Blob], `photo_${Date.now()}.jpeg`, {
        type: 'image/jpeg',
      });

      // 파일과 url을 반환
      const returnData = { file, imageUrl };
      return returnData;
    }

    // 권한이 없을 때
    return { file: null, imageUrl: '' };
  };

  // 4. 사진 촬영 함수 반환
  return { openCamera };
};
