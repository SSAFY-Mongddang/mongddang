import type { Meta, StoryObj } from '@storybook/react';
import { Spinner } from '.';
import { ColorScale, Palette, Size } from '@/shared/model/globalStylesTyes';


const meta = {
  title: 'UI/Components/Spinner',
  component: Spinner,
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
      description: '스피너의 크기입니다.',
    },
    color: {
      description: '스피너의 메인 색상입니다.',
    },
    scale: {
      description: '스피너의 색상 스케일입니다. \n설정하지 않으면 메인컬러로 지정됩니다.',
    }
  },
} satisfies Meta<typeof Spinner>;

export default meta;

type Story = StoryObj<typeof Spinner>;

export const Primary: Story = {
  args: {
    size: 'sm',
    color: 'primary',
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
const sizes: Size[] = ['sm', 'md', 'lg', 'xl'];

export const MainColors: Story = {
  args: {
    ...Primary.args,
  },
  render: (args) => (
    <div style={{ display: 'flex', flexWrap: 'wrap', gap: '1rem' }}>
      {colors.map((color) => (
        <Spinner key={color} {...args} color={color}>
        </Spinner>
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
              <Spinner key={scale} {...args} color={color} scale={scale}>
              </Spinner>
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
        <Spinner key={size} {...args} size={size}>
        </Spinner>
      ))}
    </div>
  ),
};