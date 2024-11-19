/** @jsxImportSource @emotion/react */
import { useState, useRef, useEffect } from 'react';
import { createPortal } from 'react-dom';
import { DropdownProps } from './Dropdown.types';
import { styles } from './Dropdown.styles';
import { RiArrowRightSFill } from 'react-icons/ri';
import { Icon } from '../Icon';
import { Button } from '../Button';

export const Dropdown = ({
  options,
  onSelect,
  selectedValue,
  buttonLabel = '아이를 선택하세요',
}: DropdownProps) => {
  const [isOpen, setIsOpen] = useState(false);
  const [dropdownPosition, setDropdownPosition] = useState({ top: 0, left: 0 });
  const buttonRef = useRef<HTMLDivElement>(null);

  useEffect(() => {
    if (isOpen && buttonRef.current) {
      const rect = buttonRef.current.getBoundingClientRect();
      setDropdownPosition({
        top: rect.bottom + 2,
        left: rect.left,
      });
    }
  }, [isOpen]);

  const handleButtonClick = () => {
    setIsOpen((prev) => !prev);
    console.log(options);
  };

  useEffect(() => {
    const handleClickOutside = (event: MouseEvent) => {
      if (
        buttonRef.current &&
        !buttonRef.current.contains(event.target as Node) &&
        !(event.target as Element).closest('[data-dropdown-portal]')
      ) {
        setIsOpen(false);
      }
    };

    if (isOpen) {
      document.addEventListener('mousedown', handleClickOutside);
    }

    return () => {
      document.removeEventListener('mousedown', handleClickOutside);
    };
  }, [isOpen]);

  return (
    <div css={styles.dropdownContainer}>
      <div ref={buttonRef}>
        <Button
          css={styles.button}
          fontSize="1"
          handler={handleButtonClick}
          // disabled={options.length === 0}
        >
          <span>{selectedValue || buttonLabel}</span>
          <Icon size={2} css={isOpen ? styles.iconOpen : styles.iconClose}>
            <RiArrowRightSFill />
          </Icon>
        </Button>
      </div>

      {isOpen &&
        createPortal(
          <div
            data-dropdown-portal
            css={styles.dropdownPortal}
            style={{
              top: `${dropdownPosition.top}px`,
              left: `${dropdownPosition.left}px`,
            }}
          >
            {options.map((option) => (
              <div
                key={option.name}
                css={styles.option}
                className={selectedValue === option.name ? 'selected' : ''}
                onClick={() => {
                  onSelect(option);
                  setIsOpen(false);
                }}
              >
                {option.name}
              </div>
            ))}
          </div>,
          document.body
        )}
    </div>
  );
};
