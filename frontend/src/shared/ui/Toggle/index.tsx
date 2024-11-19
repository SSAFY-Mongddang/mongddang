/** @jsxImportSource @emotion/react */
import { useState, useEffect } from 'react';
import { toggle } from './Toggle.styles';
import { ToggleProps } from './Toggle.types';

export const Toggle = ({
  isOn: externalIsOn = true,
  color = 'primary',
  scale,
  size = 2.5,
  onToggle,
  ...props
}: ToggleProps) => {
  const [isOn, setIsOn] = useState<boolean>(externalIsOn);

  useEffect(() => {
    setIsOn(externalIsOn);
  }, [externalIsOn]);

  const handleClick = () => {
    const newIson = !isOn;
    setIsOn(newIson);
    if (onToggle) {
      onToggle(newIson)
    }
  }

  return (
    <div css={toggle(isOn, color, scale, size)} onClick={handleClick} {...props}>

    </div>
    
  );
};