/** @jsxImportSource @emotion/react */
import type { Meta, StoryObj } from '@storybook/react';
import { Notification } from '.';

const meta = {
  title: 'UI/Components/Notification',
  component: Notification,
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
    ment: {
      description: '설명할 멘트입니다',
    },
    type: {
      description: 'Notification 의 종류입니다',
    },
    twoBtn: {
      description: '버튼 2개 유무',
    },
    bluehandler: {
      description: '2번째 버튼에 대한 핸들러 함수입니다',
    },
    redHandler: {
      description: '2번째 버튼에 대한 핸들러 함수입니다',
    },
    width: {
      description: '공지의 너비를 입력해주세요.',
    },
    height: {
      description: '공지의 높이를 입력해주세요.',
    },
    children: {
      description: '버튼이 2개일 때 버튼 안의 내용입니다. 순서대로 2개를 작성하면 적용이 됩니다.',
    },
  },
} satisfies Meta<typeof Notification>;

export default meta;

type Story = StoryObj<typeof Notification>;

export const Primary: Story = {
  args: {
    ment: '이건 notification 입니다',
    type: 'confirm',
    twoBtn: false,
    bluehandler: () => console.log('btn press'),
  },
  render: (args) => (
    <div
      style={{
        width: '300px',
        height: '300px',
      }}
    >
      <Notification
        ment={args.ment}
        type={args.type}
        twoBtn={args.twoBtn}
        bluehandler={() => console.log('btn press')}
      />
    </div>
  ),
};

// const types = ['confirm','primary','alert'];

export const Types: Story = {
  args: {},
  render: () => (
    <div
      style={{
        display: 'flex',
        flexWrap: 'wrap',
        gap: '1rem',
        width: '1000px',
        height: '1000px',
      }}
    >
      <div
        style={{
          width: '360px',
          height: '800px',
          position: 'relative',
        }}
      >
        <Notification
          ment="이건 notification 입니다"
          type="confirm"
          twoBtn={false}
          bluehandler={() => console.log('btn press')}
        />
      </div>
      <div
        style={{
          width: '360px',
          height: '800px',
          position: 'relative',
        }}
      >
        <Notification
          ment="이건 notification 입니다"
          type="alert"
          twoBtn={false}
          bluehandler={() => console.log('btn press')}
        />
      </div>
    </div>
  ),
};
