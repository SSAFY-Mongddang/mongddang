import type { Meta, StoryObj } from '@storybook/react';
import { Progress } from './index';
import { ProgressVariant } from './Progress.types';
import { Palette, Size } from '@/shared/model/globalStylesTyes';

const meta = {
  title: 'UI/Components/Progress',
  component: Progress,
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
    size: {
      description: 'progress의 사이즈입니다.',
    },
    variant: {
      description: '테두리의 둥근 정도입니다.',
    },
    color: {
      description: '진행바의 색깔입니다.',
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
    value: {
      description:
        '`0`에서 `max` 사이의 숫자를 의미합니다.\n현재 완료된 양을 나타냅니다.',
    },
    max: {
      description: '최대 `value`를 지정합니다.',
    },
    transparentBackground: {
      description: '배경색이 투명한지 여부입니다.',
    },
  },
} satisfies Meta<typeof Progress>;

export default meta;

type Story = StoryObj<typeof Progress>;

export const Primary: Story = {
  args: {
    size: 'md',
    variant: 'rounded',
    color: 'primary',
    value: 1,
    max: 3,
    transparentBackground: false,
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

export const MainColors: Story = {
  args: {
    ...Primary.args,
  },
  render: (args) => (
    <div style={{ display: 'flex', flexWrap: 'wrap', gap: '1rem' }}>
      {colors.map((color) => (
        <div key={color} style={{ width: '100%' }}>
          <Progress {...args} color={color} />
        </div>
      ))}
    </div>
  ),
};

const sizes: Size[] = ['sm', 'md', 'lg', 'xl'];

export const Sizes: Story = {
  args: {
    ...Primary.args,
  },
  render: (args) => (
    <div style={{ display: 'flex', flexDirection: 'column', gap: '1rem' }}>
      {sizes.map((size) => (
        <div key={size} style={{ width: '100%' }}>
          <Progress {...args} size={size}/>
        </div>
      ))}
    </div>
  ),
};

const variants: ProgressVariant[] = ['rounded', 'rectangle'];

export const Variants: Story = {
  args: {
    ...Primary.args,
  },
  render: (args) => (
    <div style={{ display: 'flex', flexDirection: 'column', gap: '1rem' }}>
      {variants.map((variant) => (
        <div key={variant} style={{ width: '100%' }}>
          <Progress {...args} variant={variant} size={'xl'}/>
          <span>{variant}</span>
        </div>
      ))}
    </div>
  ),
};

const transparentBackgrounds: boolean[] = [false, true];

export const TransparentBackground: Story = {
  args: {
    ...Primary.args,
  },
  render: (args) => (
    <div style={{ display: 'flex', flexDirection: 'column', gap: '1rem' }}>
      {transparentBackgrounds.map((transparentBackground) => (
        <div key={transparentBackground.toString()} style={{ width: '100%' }}>
          <Progress {...args} transparentBackground={transparentBackground} size={'md'} />
          <span>{transparentBackground.toString()}</span>
        </div>
      ))}
    </div>
  ),
};