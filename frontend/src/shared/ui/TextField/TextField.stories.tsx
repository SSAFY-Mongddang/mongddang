import type { Meta, StoryObj } from '@storybook/react';
import { TextField } from '.';
import { Palette } from '@/shared/model/globalStylesTyes';

const meta = {
  title: 'UI/Components/TextField',
  component: TextField,
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
    color: {
      description: '적용할 컬러 팔레트입니다.',
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
    defaultValue: {
      description: 'input 의 기본값입니다.',
    },
    disabled: {
      description: 'input 의 사용 가능 여부를 판단합니다.',
    },
    label: {
      description: 'input 의 label 입니다.',
    },
    maxRows: {
      description: 'multiLine 의 경우에 최대 라인 수 입니다.',
    },
    multiLine: {
      description: 'multiLine 으로 작성할 지 여부를 판단합니다.',
    },
    placeholder: {
      description: 'input 의 힌트입니다.',
    },
    readOnly: {
      description: '읽기만 가능합니다.',
    },
    type: {
      description: 'input 의 입력 type 을 지정합니다.',
    },
    variant: {
      description: 'input 의 스타일입니다.',
    },
  },
} satisfies Meta<typeof TextField>;

export default meta;

type Story = StoryObj<typeof TextField>;

export const Primary: Story = {
  args: {
    color: 'primary',
    defaultValue: '',
    disabled: false,
    label: 'label',
    maxRows: 10,
    multiLine: false,
    placeholder: 'this is placeholder',
    readOnly: false,
    type: 'text',
    variant: 'outlined',
  },
};

const colorList: Palette[] = [
  'primary',
  'secondary',
  'success',
  'danger',
  'indigo',
  'blue',
  'dark',
  'light',
];

export const Outlined: Story = {
  args: {
    ...Primary.args,
    variant: 'outlined',
  },
  render: (args) => (
    <div style={{ display: 'flex', flexWrap: 'wrap', gap: '1rem' }}>
      {colorList.map((color) => (
        <TextField {...args} color={color}>
          {color}
        </TextField>
      ))}
    </div>
  ),
};

export const Standard: Story = {
  args: {
    ...Primary.args,
    variant: 'standard',
  },
  render: (args) => (
    <div style={{ display: 'flex', flexWrap: 'wrap', gap: '1rem' }}>
      {colorList.map((color) => (
        <TextField {...args} color={color}>
          {color}
        </TextField>
      ))}
    </div>
  ),
};