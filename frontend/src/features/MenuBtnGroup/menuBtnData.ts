import { MenuBtnItem } from './model/types';

export const menuBtnData: MenuBtnItem[] = [
  {
    icon: '/img/%EB%A7%90%EB%9E%911.png',
    menu: {
      child: '약 챙기기',
      protector: '복약관리',
    },
    urlTo: '',
  },
  {
    icon: '/img/%EB%A7%90%EB%9E%911.png',
    menu: {
      child: '급식표 등록',
      protector: '급식표 등록',
    },
    urlTo: '',
  },
  {
    icon: '/img/%EB%A7%90%EB%9E%911.png',
    menu: {
      child: '주간 리포트',
      protector: '주간 리포트',
    },
    urlTo: '/report',
  },
  {
    icon: '/img/%EB%A7%90%EB%9E%911.png',
    menu: {
      child: '내 기록',
      protector: '어린이 기록',
    },
    urlTo: '/record',
  },
  {
    icon: '/img/%EB%A7%90%EB%9E%911.png',
    menu: {
      child: '프로필',
      protector: '프로필',
    },
    urlTo: '/profile',
  },
  {
    icon: '/img/%EB%A7%90%EB%9E%911.png',
    menu: {
      child: '설정',
      protector: '설정',
    },
    urlTo: '',
  },
  {
    icon: '/img/%EB%A7%90%EB%9E%911.png',
    menu: {
      child: '권한설정',
      protector: '권한설정',
    },
    urlTo: '/samsungsetting',
  },
  {
    icon: '/img/%EB%A7%90%EB%9E%911.png',
    menu: {
      child: ' 삼성 데이터 조회',
      protector: '삼성 데이터 조회',
    },
    urlTo: '/checksamsungdata',
  },
];
