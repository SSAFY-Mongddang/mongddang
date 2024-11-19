import { MenuBtnItem } from './model/types';
import medication from '@/assets/img/icon/medication_icon.png';
import meal from '@/assets/img/icon/meal_icon.png';
import record from '@/assets/img/icon/record_icon.png';
import report from '@/assets/img/icon/report_icon.png';
import profile from '@/assets/img/icon/profile_icon.png';
import setting from '@/assets/img/icon/setting_icon.png';

export const menuBtnData: MenuBtnItem[] = [
  {
    icon: medication,
    category: 'medication',
    menu: {
      child: '약 챙기기',
      protector: '복약관리',
    },
    urlTo: {
      child: '/medication',
      protector: '/medication',
    },
  },
  {
    icon: meal,
    category: 'meal',
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
    icon: report,
    category: 'report',
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
    icon: record,
    category: 'record',
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
    icon: profile,
    category: 'profile',
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
    icon: setting,
    category: 'setting',
    menu: {
      child: '설정',
      protector: '설정',
    },
    urlTo: {
      child: '/setting',
      protector: '/invitecode',
    },
  },
  // {
  //   icon: '/img/%EB%A7%90%EB%9E%911.png',
  //   menu: {
  //     child: '권한설정',
  //     protector: '권한설정',
  //   },
  //   urlTo: '/samsungsetting',
  // },
  // {
  //   icon: '/img/%EB%A7%90%EB%9E%911.png',
  //   menu: {
  //     child: ' 삼성 데이터 조회',
  //     protector: '삼성 데이터 조회',
  //   },
  //   urlTo: '/checksamsungdata',
  // }
];
