import { MenuBtnItem } from './model/types';

export const menuBtnData: MenuBtnItem[] = [
  {
    icon: '/img/%EB%A7%90%EB%9E%911.png',
    menu: {
      child: '약 챙기기',
      protector: '복약관리',
    },
    urlTo: {
      child: '',
      protector: '',
    },
  },
  {
    icon: '/img/%EB%A7%90%EB%9E%911.png',
    menu: {
      child: '급식표 등록',
      protector: '급식표 등록',
    },
    urlTo: {
      child: '',
      protector: '',
    },
  },
  {
    icon: '/img/%EB%A7%90%EB%9E%911.png',
    menu: {
      child: '주간 리포트',
      protector: '주간 리포트',
    },
    urlTo: {
      child: '/report',
      protector: '/report',
    },
  },
  {
    icon: '/img/%EB%A7%90%EB%9E%911.png',
    menu: {
      child: '내 기록',
      protector: '어린이 기록',
    },
    urlTo: {
      child: '/record',
      protector: '/record',
    },
  },
  {
    icon: '/img/%EB%A7%90%EB%9E%911.png',
    menu: {
      child: '프로필',
      protector: '프로필',
    },
    urlTo: {
      child: '/profile',
      protector: '/profile',
    },
  },
  {
    icon: '/img/%EB%A7%90%EB%9E%911.png',
    menu: {
      child: '설정',
      protector: '설정',
    },
    urlTo: {
      child: '/setting',
      protector: '/invitecode',
    },
  },
  {
    icon: '/img/%EB%A7%90%EB%9E%911.png',
    menu: {
      child: '삼성설정',
      protector: '삼성설정',
    },
    urlTo: {
      child: '/samsungsetting',
      protector: '',
    },
  },
  {
    icon: '/img/%EB%A7%90%EB%9E%911.png',
    menu: {
      child: '포그라운드 설정',
      protector: '포그라운드설정',
    },
    urlTo: {
      child: '/foregroundsetting',
      protector: '',
    },
  },
];
