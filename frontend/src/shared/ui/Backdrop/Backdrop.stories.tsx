/** @jsxImportSource @emotion/react */
import type { Meta, StoryObj } from '@storybook/react';
import { Backdrop } from '.';

const meta = {
  title: 'UI/Components/Backdrop',
  component: Backdrop,
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
      description: 'Backdrop 위에 표시할 콘텐츠입니다.',
    },
    opacity: {
      description: '투명도입니다. 범위는 0~100(%)입니다.',
      control: {
        type: 'range',
        min: 0,
        max: 100,
        step: 1,
      },
    },
    blur: {
      description: '흐림도입니다. 범위는 0~100(%)입니다.',
      control: {
        type: 'range',
        min: 0,
        max: 100,
        step: 1,
      },
    },
    color: {
      description: '적용할 컬러입니다.\n기본 색상은 primary 입니다.',
      options: [
        'primary',
        'secondary',
        'success',
        'danger',
        'indigo',
        'blue',
        'dark',
        'light',
      ],
      control: 'select',
    },
  },
} satisfies Meta<typeof Backdrop>;

export default meta;

type Story = StoryObj<typeof Backdrop>;

export const Primary: Story = {
  args: {},
  render: (args) => (
    <>
      <div style={{ padding: '1rem', color: 'black' }}>
        <div>this is for test backdrop</div>
        <div>this is for test backdrop</div>
        <div>this is for test backdrop</div>
        <div>this is for test backdrop</div>
        <div>this is for test backdrop</div>
        <div>this is for test backdrop</div>
        <div>this is for test backdrop</div>
        <div>this is for test backdrop</div>
      </div>
      <Backdrop {...args}></Backdrop>
    </>
  ),
};
