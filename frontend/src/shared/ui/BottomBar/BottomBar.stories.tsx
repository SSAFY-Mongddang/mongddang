import type { Meta, StoryObj } from '@storybook/react';
import { BottomBar } from '.';
import img1 from '/img/말랑1.png';
import img2 from '/img/말랑2.png';
import img3 from '/img/말랑3.png';

const meta = {
  title: 'UI/Components/BottomBar',
  component: BottomBar,
  parameters: {
    layout: 'fullscreen',
  },
  decorators: [
    (Story) => (
      <div style={{ padding: '2rem', margin:'5rem 0'}}>
        <Story />
      </div>
    ),
  ],
  tags: ['!autodocs'],
  argTypes: {
    icons: {
      description: '메뉴의 아이콘들입니다',
    },
    menus: {
      description: '메뉴들입니다',
    },
    onHandleChange: {
      description: '메뉴 변경 시 핸들러 함수입니다',
    },
  },
} satisfies Meta<typeof BottomBar>;

export default meta;

type Story = StoryObj<typeof BottomBar>;

export const Primary: Story = {
  args: {
    icons: [img1, img2, img3],
    menus: ['도감', '메뉴', '기록'],
    onHandleChange: (menu: number|undefined) => console.log('change to ', menu),
  },
};
