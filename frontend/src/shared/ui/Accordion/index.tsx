/** @jsxImportSource @emotion/react */
import { useState, useRef, useEffect } from 'react';
import { AccordionProps } from './Accordion.types';
import {
  accordionContainer,
  accordionContent,
  accordionTitle,
  iconRotation,
  animatedContent,
  titleContent,
  arrowIcon,
} from './Accordion.styles';
import { SlArrowRight } from 'react-icons/sl';
import { Icon } from '../Icon';
import { Typography } from '../Typography';

export const Accordion = ({
  title = '식단',
  content = '식단내용',
  icon,
  imgiconsize = 3,
  togglesize = 1,
  titleSize,
  titleWeight,
  contentSize,
  contentWeight,
  onHandler = () => {},
}: AccordionProps) => {
  const [isOpen, setIsOpen] = useState(false);
  const contentRef = useRef<HTMLDivElement>(null);

  const handleToggle = () => {
    setIsOpen(!isOpen);
    onHandler();
  };

  useEffect(() => {
    if (contentRef.current) {
      contentRef.current.style.maxHeight = isOpen
        ? `${contentRef.current.scrollHeight + 16}px`
        : '0';
    }
  }, [isOpen]);

  return (
    <div css={accordionContainer}>
      <div css={accordionTitle} onClick={handleToggle}>
        <Typography
          css={titleContent}
          color="dark"
          size={titleSize}
          weight={titleWeight}
        >
          <Icon size={imgiconsize}>
            <img alt="icon-0" src={icon} />
          </Icon>
          {title}
        </Typography>
        <Icon css={[arrowIcon, iconRotation(isOpen)]} size={togglesize}>
          <SlArrowRight />
        </Icon>
      </div>
      <div css={[accordionContent, animatedContent]} ref={contentRef}>
        <Typography color="dark" size={contentSize} weight={contentWeight}>
          {content}
        </Typography>
      </div>
    </div>
  );
};
