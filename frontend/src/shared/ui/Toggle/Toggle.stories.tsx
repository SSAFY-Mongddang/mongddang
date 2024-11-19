import type { Meta, StoryObj } from '@storybook/react';
import { Toggle } from '.';
import { ColorScale, Palette } from '@/shared/model/globalStylesTyes';


const meta = {
  title: 'UI/Components/Toggle',
  component: Toggle,
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
    },
    scale: {
      description:
        '적용할 컬러스케일입니다.\n입력되지 않으면 MainColor가 적용됩니다.',
    },
    size: {
      description: '적용할 사이즈입니다.\nrem 단위로 입력하면 됩니다.',
    },
    isOn: {
      description: 'on/off 선택여부입니다.'
    }
  },
} satisfies Meta<typeof Toggle>;

export default meta;

type Story = StoryObj<typeof Toggle>;

export const Primary: Story = {
  args: {
    children: '',
    color: 'primary',
    size: 2.5,
    
  },
};

const colors: Palette[] = [
  'primary',
  'secondary',
  'success',
  'danger',
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
const sizes: number[] = [2.5, 2.625, 2.75, 2.875, 3, 3.125, 3.25];

export const MainColors: Story = {
  args: {
    ...Primary.args,
  },
  render: (args) => (
    <div style={{ display: 'flex', flexWrap: 'wrap', gap: '1rem' }}>
      {colors.map((color) => (
        <Toggle key={color} {...args} color={color}>
          {color}
        </Toggle>
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
              <Toggle key={scale} style={{ marginBottom: '1rem' }} {...args} color={color} scale={scale}>
                {scale}
              </Toggle>
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
        <Toggle key={size} {...args} size={size}>
          {size}rem
        </Toggle>
      ))}
    </div>
  ),
};