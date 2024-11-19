import type { Meta, StoryObj } from '@storybook/react';
import img1 from '/img/말랑1.png';
import img2 from '/img/말랑2.png';
import { IconTypo } from '.';

const meta = {
  title: 'UI/Components/IconTypo',
  component: IconTypo,
  parameters: {
    layout: 'fullscreen',
  },
  decorators: [
    (Story) => (
      <div style={{ padding: '1rem' }}>
        <Story />
      </div>
    ),
  ],
  tags: ['!autodocs'],
  argTypes: {
    icon: {
      description: '메뉴의 아이콘입니다',
    },
    menu: {
      description: '메뉴입니다',
      control: 'text',
    },
    selected: {
      description: '메뉴의 선택 여부입니다',
      control: 'boolean',
    },
    size: {
      description: '아이콘의 크기입니다. rem 기준입니다.'
    }
  },
} satisfies Meta<typeof IconTypo>;

export default meta;

type Story = StoryObj<typeof IconTypo>;

export const Primary: Story = {
  args: {
    icon: img1,  // 직접 이미지 경로 전달
    menu: '메뉴',
    selected: false,
  },
};

export const Selection: Story = {
  render: () => (
    <div style={{ display: 'flex', flexWrap: 'wrap', gap: '1rem' }}>
      <IconTypo 
        icon={img1}
        menu="메뉴"
        selected={false} 
      />
      <IconTypo 
        icon={img2}
        menu="메뉴"
        selected={true} 
      />
    </div>
  ),
};