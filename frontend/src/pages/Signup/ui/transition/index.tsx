/** @jsxImportSource @emotion/react */
import React, { Key, ReactElement, useEffect, useState } from 'react';

interface TransitionProps {
  ['data-key']: Key;
  wrapperCss?: React.CSSProperties;
  children: ReactElement[];
}

export const Transition = ({
  'data-key': dataKey,
  children,
  wrapperCss,
}: TransitionProps) => {
  const [currentItem, setCurrentItem] = useState<ReactElement | null>(null);

  useEffect(() => {
    const matchedChild = children.find((child) => child.key === dataKey);
    if (matchedChild) {
      setCurrentItem(matchedChild);
    }
  }, [dataKey, children]);

  return <div style={wrapperCss}>{currentItem}</div>;
};
