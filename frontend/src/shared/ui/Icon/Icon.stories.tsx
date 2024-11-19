import type { Meta, StoryObj } from '@storybook/react';
import { Icon } from '.';
import img1 from '/img/말랑1.png';
import img2 from '/img/말랑2.png';
import img3 from '/img/말랑3.png';

const meta = {
  title: 'UI/Components/Icon',
  component: Icon,
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
      description: '아이콘 크기입니다. rem 단위로 측정됩니다.',
    },
    children: {
      control: false,
      description: '이미지입니다.',
    },
  },
} satisfies Meta<typeof Icon>;

export default meta;

type Story = StoryObj<typeof Icon>;

const iconList = [img1, img2, img3];

export const Primary: Story = {
  args: {
    size: 3,
  },
  render: (args) => (
    <div style={{ display: 'flex', flexWrap: 'wrap', gap: '1rem' }}>
      {iconList.map((icon, index) => (
        <Icon {...args} key={index}>
          <img src={icon} alt={`icon-${index}`} />
        </Icon>
      ))}
    </div>
  ),
};
