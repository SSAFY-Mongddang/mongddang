/** @jsxImportSource @emotion/react */
import type { Meta, StoryObj } from '@storybook/react';
import { Modal } from '.';
import { Typography } from '../Typography';

const meta = {
  title: 'UI/Components/Modal',
  component: Modal,
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
      description: '모달의 내용입니다',
    },
    height: {
      description: '모달의 높이입니다(0~100%)',
    },
    width: {
      description: '모달의 넓이입니다.(0~100%)',
    },
  },
} satisfies Meta<typeof Modal>;

export default meta;

type Story = StoryObj<typeof Modal>;

export const Primary: Story = {
  args: {height:60, width:30},
  render: () => (
    <div
      style={{
        display: 'flex',
        flexWrap: 'wrap',
        gap: '1rem',
        width: '500',
        height: '500px',
      }}
    >
      <div
        style={{
          width: '360px',
          height: '700px',
          position: 'relative',
        }}
      >
        <Modal>
          <div
            style={{
              width: '100%',
              height: '100%',
              display: 'flex',
              alignItems: 'center',
              justifyContent: 'center',
            }}
          >
            <Typography color="dark" size={'1.75'} weight={700}>
              이것은 모달입니다
            </Typography>
          </div>
        </Modal>
      </div>
    </div>
  ),
};
