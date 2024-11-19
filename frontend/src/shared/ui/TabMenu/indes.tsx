/** @jsxImportSource @emotion/react */
import React, { useState } from 'react';
import { TabMenuProps } from './TabMenu.types';
import { Icon } from '../Icon';
import { Typography } from '../Typography';
import { containerCss, contentCss, labelCss, tabListCss } from './styles';
import meal from '../../../assets/img/icon/meal_icon.png'
import exercise from '../../../assets/img/icon/exercise_icon.png'
import medication from '../../../assets/img/icon/medication_icon.png'
import sleep from '../../../assets/img/icon/sleep_icon.png'

const data = [
  {
    id: 'food',
    label: (
      <div style={{ display: 'flex', alignItems: 'center', gap: '0.5rem' }}>
        <Icon size={1.5}>
          <img alt="icon-food" src={meal} />
        </Icon>
        <Typography color="dark" size="1" weight={700}>
          밥
        </Typography>
      </div>
    ),
  },
  {
    id: 'exercise',
    label: (
      <div style={{ display: 'flex', alignItems: 'center', gap: '0.5rem' }}>
        <Icon size={1.5}>
          <img alt="icon-exercise" src={exercise} />
        </Icon>
        <Typography color="dark" size="1" weight={700}>
          운동
        </Typography>
      </div>
    ),
  },
  {
    id: 'sleep',
    label: (
      <div style={{ display: 'flex', alignItems: 'center', gap: '0.5rem' }}>
        <Icon size={1.5}>
          <img alt="icon-sleep" src={sleep} />
        </Icon>
        <Typography color="dark" size="1" weight={700}>
          수면
        </Typography>
      </div>
    ),
  },
  {
    id: 'medicine',
    label: (
      <div style={{ display: 'flex', alignItems: 'center', gap: '0.5rem' }}>
        <Icon size={1.5}>
          <img alt="icon-medicine" src={medication} />
        </Icon>
        <Typography color="dark" size="1" weight={700}>
          복약
        </Typography>
      </div>
    ),
  },
];

export const TabMenu: React.FC<TabMenuProps> = ({ 
  contents,
  activeTab: externalActiveTab,
  onTabChange
}) => {
  const [internalActiveTab, setInternalActiveTab] = useState(data[0].id);
  const activeTab = externalActiveTab || internalActiveTab;

  const items = data.map((label, index) => ({
    ...label,
    content: contents[index]
  }));

  const handleTabClick = (tabId: string) => {
    setInternalActiveTab(tabId);
    onTabChange?.(tabId);
  };

  return (
    <div css={containerCss}>
      <div css={tabListCss}>
        {items.map((item) => (
          <button
            key={item.id}
            onClick={() => handleTabClick(item.id)}
            css={labelCss(item.id, activeTab)}
          >
            {item.label}
          </button>
        ))}
      </div>
      <div css={contentCss}>
        {items.find(item => item.id === activeTab)?.content}
      </div>
    </div>
  );
};