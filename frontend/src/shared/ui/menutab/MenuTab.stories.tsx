import { Meta, StoryObj } from '@storybook/react';
import { MenuTab } from '.';
import { MenuTabProps } from './MenuTab.types';
import { FontWeight, Palette } from '@/shared/model/globalStylesTyes';


const meta = {
  title: 'UI/Components/MenuTab',
  component: MenuTab,
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
      description: '선택된 버튼의 색입니다.',
      options: ['primary', 'secondary','success', 'danger','indigo','blue', 'dark', 'light'],
      control: 'select',
    },
    children: {
      description: 'Tab의 갯수입니다.',
      control: { type: 'object' },
    },
    fontSize: {
      description: '폰트에 적용할 사이즈입니다.\nrem 단위로 입력하면 됩니다.',
    },
    fontWeight: {
      description: '폰트에 적용할 굵기입니다.',
      options: [100, 200, 300, 400, 500, 600, 700, 800, 900],
      control: 'select'

    },
  },
} satisfies Meta<typeof MenuTab>;

export default meta;

type Story = StoryObj<typeof MenuTab>;

const Template: StoryObj<MenuTabProps> = {
  render: (args) => (
    <MenuTab {...args}>
      {args.children.map((child, index) => (
        <div key={index}>{child}</div>
      ))}
    </MenuTab>
  ),
};

export const Primary = {
  ...Template,
  args: {
    color: 'primary',
    children: ['Tab1', 'Tab2', 'Tab3'],
    fontSize: 1,
    fontWeight: 500,
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

export const Outlined: Story = {
  ...Template,
  args: {
    color: 'primary',
    children: ['Tab1', 'Tab2', 'Tab3'],
  },
  render: (args) => (
    <div style={{ display: 'flex', flexWrap: 'wrap', gap: '1rem' }}>
      {colors.map((color) => (
        <MenuTab {...args} color={color}></MenuTab>
      ))}
    </div>
  ),
};

const sizes: number[] = [0.5, 0.625, 0.75, 0.875, 1, 1.125, 1.25];
const weights: FontWeight[] = [100, 200, 300, 400, 500, 600, 700, 800, 900];

export const fontSize: Story = {
  ...Template,
  args: {
    color: 'primary',
    children: ['Tab1', 'Tab2', 'Tab3'],
  },
  render: (args) => (
    <div style={{ display: 'flex', flexWrap: 'wrap', gap: '1rem' }}>
      {sizes.map((size) => (
        <div key={size}>
          <MenuTab {...args} fontSize={size} />
        </div>
      ))}
    </div>
  ),
};

export const fontWeight: Story = {
  ...Template,
  args: {
    color: 'danger',
    children: ['Tab1', 'Tab2', 'Tab3'],
  },
  render: (args) => (
    <div style={{ display: 'flex', flexWrap: 'wrap', gap: '1rem' }}>
      {weights.map((weight) => (
        <div key={weight}>
          <MenuTab {...args} fontWeight={weight} />
        </div>
      ))}
    </div>
  ),
};
