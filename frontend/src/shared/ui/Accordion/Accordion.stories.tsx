/** @jsxImportSource @emotion/react */
import type { Meta, StoryObj } from '@storybook/react';
import { Accordion } from '.';

const meta = {
  title: 'UI/Components/Accordion',
  component: Accordion,
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
    title: {
      description: '닫혀있을 때의 보이는 타이틀입니다.',
    },
    content: {
      description: '아코디언을 열었을 때 보이는 내용입니다.',
    },
    onHandler: {
      description: '아코디언 열고 닫는 토글의 역할핸들러입니다.',
    },
    icon: {
      description: '제목 관련 아이콘 자리입니다.',
    },
    imgiconsize: {
      description: '`이미지`아이콘 크기입니다. rem기준입니다.',
    },
    togglesize: {
      description: '`>`아이콘 크기입니다. rem기준입니다.',
    },
    titleSize: {
      description: 'title 폰트 사이즈입니다.',
      options: ['0.75', '1', '1.25', '1.5', '1.75'],
    },
    titleWeight: {
      description: 'title에 적용할 굵기입니다.',
      options: [400, 500, 600, 700],
      control: 'select',
    },
    contentSize: {
      description: 'content 폰트 사이즈입니다.',
      options: ['0.75', '1', '1.25', '1.5', '1.75'],
    },
    contentWeight: {
      description: 'content에 적용할 굵기입니다.',
      options: [400, 500, 600, 700],
      control: 'select',
    },
  },
} satisfies Meta<typeof Accordion>;

export default meta;

type Story = StoryObj<typeof Accordion>;

export const Primary: Story = {
  args: {
    title: '식단',
    content: '내용',
    onHandler: () => {},
    icon: '/img/말랑1.png',
    imgiconsize: 3,
    togglesize: 1,
    titleSize: '1.5',
    titleWeight: 500,
    contentSize: '1.25',
    contentWeight: 400,
  },
};
