import type { Meta, StoryObj } from '@storybook/react';
import { Button } from './index';
import { ColorScale, Palette } from '@/shared/model/globalStylesTyes';

const meta = {
  title: 'UI/Components/Button',
  component: Button,
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
      description: '버튼 안에 표시할 텍스트입니다.',
    },
    variant: {
      description: '버튼의 스타일입니다.',
    },
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
    scale: {
      description:
        '적용할 컬러스케일입니다.\n입력되지 않으면 MainColor가 적용됩니다.',
      options: [
        '100',
        '200',
        '300',
        '400',
        '500',
        '600',
        '700',
        '800',
        '900',
        'A100',
        'A200',
        'A400',
        'A700',
      ],
      control: 'select',
    },
    disabled: {
      description: '버튼의 비활성화 여부입니다.',
    },
    isShadow: {
      description: '버튼 아래의 그림자유무입니다.',
    },
    fullwidth: {
      description: '`width`를 상위 노드의 크기에 맞출지 여부입니다.',
    },
    handler: {
      description: '버튼을 클릭했을 때의 핸들러 함수입니다.',
    },
  },
} satisfies Meta<typeof Button>;

export default meta;

type Story = StoryObj<typeof Button>;

export const Primary: Story = {
  args: {
    children: 'Button',
    color: 'primary',
    variant: 'contained',
    disabled: false,
    fullwidth: false,
    isShadow: false,
    fontSize: '1'
  },
};

const colors: Palette[] = [
  'primary',
  'secondary',
  'danger',
  'indigo',
  'blue',
  'success',
  'dark',
  'light',
];

const scales: ColorScale[] = [
  '50',
  '100',
  '200',
  '300',
  '400',
  '500',
  '600',
  '700',
  '800',
  '900',
  'A100',
  'A200',
  'A400',
  'A700',
];

export const Contained: Story = {
  args: {
    ...Primary.args,
    variant: 'contained',
  },
  render: (args) => (
    <div style={{ display: 'flex', flexWrap: 'wrap', gap: '1rem' }}>
      {colors.map((color) => (
        <Button key={color} {...args} color={color}>
          {color}
        </Button>
      ))}
    </div>
  ),
};

export const Outlined: Story = {
  args: {
    ...Primary.args,
    variant: 'outlined',
  },
  parameters: {
    controls: false,
  },
  render: (args) => (
    <div style={{ display: 'flex', flexWrap: 'wrap', gap: '1rem' }}>
      {colors.map((color) => (
        <Button key={color} {...args} color={color}>
          {color}
        </Button>
      ))}
    </div>
  ),
};

export const ColorsWithScales: Story = {
  args: {
    ...Primary.args,
  },
  render: (args) => (
    <div>
      {colors.map((color) => (
        <div
          key={color}
          style={{ display: 'flex', flexWrap: 'wrap', gap: '0.3rem' }}
        >
          {color != 'light' &&
            scales.map((scale) => (
              <Button key={scale} {...args} color={color} scale={scale}>
                {scale}
              </Button>
            ))}
        </div>
      ))}
    </div>
  ),
};
