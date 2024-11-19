import type { Meta, StoryObj } from '@storybook/react';
import { TabMenu } from './indes';

const meta = {
  title: 'UI/Components/TabMenu',
  component: TabMenu,
  parameters: {
    layout: 'fullscreen',
    docs: {
      description: {
        component:
          '고정된 라벨을 가진 탭 메뉴 컴포넌트입니다. 4개의 컨텐츠를 props로 받습니다.',
      },
    },
  },
  decorators: [
    (Story) => (
      <div style={{ padding: '1rem', maxWidth: '800px', margin: '0 auto' }}>
        <Story />
      </div>
    ),
  ],
  argTypes: {
    contents: {
      description: '각 탭에 표시될 4개의 컨텐츠 요소들',
      control: 'object',
    },
    activeTab: {
      description: '현재 활성화된 탭의 ID',
      control: 'text',
    },
    onTabChange: {
      description: '탭 변경 시 호출되는 콜백 함수',
      action: 'changed',
    },
  },
} satisfies Meta<typeof TabMenu>;

export default meta;
type Story = StoryObj<typeof TabMenu>;

export const Default: Story = {
  args: {
    contents: [
      <div>밥 탭의 내용입니다.</div>,
      <div>운동 탭의 내용입니다.</div>,
      <div>수면 탭의 내용입니다.</div>,
      <div>복약 탭의 내용입니다.</div>,
    ],
  },
};
