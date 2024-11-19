import { css } from '@emotion/react';
import { Colors } from '../styles/globalStyles';

export const styles = {
  dropdownContainer: css({
    position: 'relative',
    width: '12.5rem',
    height: '2.5rem',
  }),
  button: css({
    backgroundColor: Colors['primary']['200'],
    width: '100%',
    padding: '0.625rem',
    cursor: 'pointer',
    color: '#000',
    display: 'flex',
    justifyContent: 'space-between',
    alignItems: 'center',
    border: 'none',
    outline: 'none',
    position: 'relative',
    zIndex: 1,
  }),
  dropdownPortal: css({
    position: 'fixed', // fixed로 변경
    backgroundColor: Colors['primary']['200'],
    borderRadius: '0.625rem',
    boxShadow: '0 0.25rem 0.625rem rgba(0, 0, 0, 0.1)',
    zIndex: 1000, // 높은 z-index 값
    width: '12.5rem',
    padding: '0.5rem 0',
  }),
  option: css({
    padding: '0.625rem',
    cursor: 'pointer',
    color: '#000',
    '&:hover': {
      backgroundColor: Colors['primary']['500'],
    },
    '&.selected': {
      fontWeight: 'bold',
      backgroundColor: Colors['primary']['500'],
    },
  }),
  iconOpen: css({
    transition: 'transform 0.3s ease',
    transform: 'rotate(90deg)',
  }),
  iconClose: css({
    transition: 'transform 0.3s ease',
  }),
};