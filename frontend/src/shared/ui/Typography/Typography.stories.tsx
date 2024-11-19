import type { Meta, StoryObj } from '@storybook/react';
import { Typography } from '.';
import {
  ColorScale,
  FontWeight,
  Palette,
} from '@/shared/model/globalStylesTyes';
import { Tsize } from './Typography.types';

const meta = {
  title: 'UI/Components/Typography',
  component: Typography,
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
    scale: {
      description:
        '적용할 컬러스케일입니다.\n입력되지 않으면 MainColor가 적용됩니다.',
      options: ['100', '200', '300', '400', '500', '600', '700', '800', '900', 'A100', 'A200', 'A400', 'A700'],
      control: 'select',
    },
    size: {
      description: '적용할 사이즈입니다.\nrem단위입니다..',
    },
    weight: {
      description: '적용할 굵기입니다.',
      options: [400, 500, 600, 700],
      control: 'select',
    },
  },
} satisfies Meta<typeof Typography>;

export default meta;

type Story = StoryObj<typeof Typography>;

export const Primary: Story = {
  args: {
    children: 'Typography',
    color: 'dark',
    size: '1',
    weight: 700,
  },
};

const colors: Palette[] = [
  'primary',
  'secondary',
  'danger',
  'success',
  'indigo',
  'blue',
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
const sizes: Tsize[] = ['0.75', '1', '1.25', '1.5','1.75'];
const weights: FontWeight[] = [400, 500, 600, 700];

export const MainColors: Story = {
  args: {
    ...Primary.args,
  },
  render: (args) => (
    <div style={{ display: 'flex', flexWrap: 'wrap', gap: '1rem' }}>
      {colors.map((color) => (
        <Typography key={color} {...args} color={color}>
          {color}
        </Typography>
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
          style={{ display: 'flex', flexWrap: 'wrap', gap: '1rem' }}
        >
          {color != 'dark' &&
            color != 'light' &&
            scales.map((scale) => (
              <Typography key={scale} {...args} color={color} scale={scale}>
                {scale}
              </Typography>
            ))}
        </div>
      ))}
    </div>
  ),
};

export const Sizes: Story = {
  args: {
    ...Primary.args,
  },
  render: (args) => (
    <div style={{ display: 'flex', flexWrap: 'wrap', gap: '1rem' }}>
      {sizes.map((size) => (
        <Typography key={size} {...args} size={size}>
          {size}rem
        </Typography>
      ))}
    </div>
  ),
};

export const Weights: Story = {
  args: {
    ...Primary.args,
  },
  render: (args) => (
    <div style={{ display: 'flex', flexWrap: 'wrap', gap: '1rem' }}>
      {weights.map((weight) => (
        <Typography key={weight} {...args} weight={weight}>
          {weight}
        </Typography>
      ))}
    </div>
  ),
};
