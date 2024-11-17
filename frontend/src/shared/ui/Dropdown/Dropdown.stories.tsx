/** @jsxImportSource @emotion/react */
import { useState } from 'react';
import type { Meta } from '@storybook/react';
import { Dropdown } from '.';

const meta = {
  title: 'UI/Components/Dropdown',
  component: Dropdown,
  parameters: {
    layout: 'centered',
    docs: {
      description: {
        component: '바텀시트 스타일의 드롭다운 컴포넌트입니다.',
      },
    },
  },
  argTypes: {
    options: {
      description: '드롭다운에 표시될 옵션 배열',
      control: {
        type: 'select',
        options: ['김싸피', '박싸피', '이싸피'],
      },
    },
    selectedValue: {
      description: '현재 선택된 값',
      control: 'text',
    },
    isOpen: {
      description: '드롭다운의 열림/닫힘 상태',
      control: 'boolean',
    },
    onSelect: {
      description: '옵션 선택 시 호출되는 콜백 함수',
      action: 'selected',
    },
    onClose: {
      description: '드롭다운이 닫힐 때 호출되는 콜백 함수',
      action: 'closed',
    },
  },
} satisfies Meta<typeof Dropdown>;

export default meta;

export const Default = () => {
  const [isOpen, setIsOpen] = useState(false);
  const [selected, setSelected] = useState('김싸피');

  const options = [
    { name: '김싸피', nickname: '도르마무' },
    { name: '김트롤', nickname: '우가우가' },
    { name: '김후루룩', nickname: '짭짭' },
  ];

  return (
    <div style={{ padding: '20px' }}>
      <Dropdown
        options={options}
        onSelect={(value) => {
          setSelected(value.name);
        }}
        isOpen={isOpen}
        onClose={() => setIsOpen(false)}
        selectedValue={selected}
        buttonLabel={selected} // 버튼 라벨을 선택된 값으로 설정
        onOpen={() => setIsOpen(true)} // 드롭다운 열기 기능 추가
      />
    </div>
  );
};
