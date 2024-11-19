import type { Meta, StoryObj } from '@storybook/react';
import { Chip } from '.';
import { FontWeight, Palette } from '@/shared/model/globalStylesTyes';


const meta = {
  title: 'UI/Components/Chip',
  component: Chip,
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
      description: 'Chip 색상입니다.',
    },
    border: {
      description: '테두리 둥근 정도입니다.\nrem 단위로 입력하면 됩니다.',
    },
    fontSize: {
      description: '적용할 폰트 사이즈입니다.\nrem 단위로 입력하면 됩니다.',
    },
    fontWeight: {
      description: '적용할 폰트 굵기입니다.\nrem 단위로 입력하면 됩니다.',
    },
  },
} satisfies Meta<typeof Chip>;

export default meta;

type Story = StoryObj<typeof Chip>;

export const Primary: Story = {
  args: {
    children: 'Chip',
    color: 'primary',
    border: 1,
    fontSize: 1,
    fontWeight: 700,
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
const sizes: number[] = [0.5, 0.625, 0.75, 0.875, 1, 1.125, 1.25];
const borders: number[] = [0.5, 0.625, 0.875, 1];

export const Colors: Story = {
  args: {
    ...Primary.args,
  },
  render: (args) => (
    <div style={{ display: 'flex', flexWrap: 'wrap', gap: '1rem' }}>
      {colors.map((color) => (
        <Chip key={color} {...args} color={color}>
          {color}
        </Chip>
      ))}
    </div>
  ),
};

export const Borders: Story = {
  args: {
    ...Primary.args,
    color: 'dark',
  },
  render: (args) => (
    <div style={{ display: 'flex', flexWrap: 'wrap', gap: '1rem' }}>
      {borders.map((border) => (
        <Chip key={border} {...args} border={border}>
          {border}rem
        </Chip>
      ))}
    </div>
  ),
}

export const FontSizes: Story = {
  args: {
    ...Primary.args,
  },
  render: (args) => (
    <div style={{ display: 'flex', flexWrap: 'wrap', gap: '1rem' }}>
      {sizes.map((size) => (
        <Chip key={size} {...args} fontSize={size}>
          {size}rem
        </Chip>
      ))}
    </div>
  ),
}

const weights: FontWeight[] = [100, 200, 300, 400, 500, 600, 700, 800, 900];

export const FontWeights: Story = {
  args: {
    ...Primary.args,
    color: 'light',
  },
  render: (args) => (
    <div style={{ display: 'flex', flexWrap: 'wrap', gap: '1rem' }}>
      {weights.map((weight) => (
        <Chip key={weight} {...args} fontWeight={weight}>
          {weight}
        </Chip>
      ))}
    </div>
  ),
}
