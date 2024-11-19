import type { Meta, StoryObj } from '@storybook/react';
import { TopBar } from '.';
import { TTopBar } from './TopBar.types';

const meta = {
  title: 'UI/Components/TopBar',
  component: TopBar,
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
    children: {
      description: 'text를 입력해주세요',
    },
    type: {
      description: 'TopBar의 타입입니다.',
    },
    iconHandler: {
      description: '아이콘 핸들러 함수입니다.'
    }
  },
} satisfies Meta<typeof TopBar>;

export default meta;

type Story = StoryObj<typeof TopBar>;

export const Primary: Story = {
  args: { children: '테스트', type: 'page' },
};

const pages: TTopBar[] = ['page', 'iconpage', 'modal'];

export const typeTopBar: Story = {
  args: {
    ...Primary.args,
  },
  render: (args) => (
    <div style={{ display: 'flex', flexWrap: 'wrap', gap: '1rem' }}>
      {pages.map((page) => (
        <TopBar {...args} type={page} key={page}>
          {page}
        </TopBar>
      ))}
    </div>
  ),
};
