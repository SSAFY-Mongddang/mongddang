import React from 'react'
import { Toggle } from '@/shared/ui/Toggle';

const PermissionListItem: React.FC<{
  keyName: string;
  value: boolean;
  onToggle: () => void;
}> = ({ keyName, value, onToggle }) => {
  return (
    <li key={keyName} style={styles.listItem}>
      <PermissionLabel keyName={keyName}>
        <Toggle
          color="primary"
          size={2.5}
          isOn={value}
          onChange={onToggle} // Toggle state on change
        />
      </PermissionLabel>
    </li>
  );
};

// Reusable label component
const PermissionLabel: React.FC<{ keyName: string; children: React.ReactNode }> = ({
  keyName,
  children,
}) => (
  <div style={styles.labelWrapper}>
    <span>{keyName}</span>
    {children}
  </div>
);

// Style object
const styles = {
  listItem: {
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'space-between', // Adjust spacing between label and toggle
    marginBottom: '1rem',
    listStyle: 'none', // Hide bullet points
  },
  labelWrapper: {
    display: 'flex',
    alignItems: 'center',
    gap: '0.5rem', // Space between label and toggle
  },
};

export default PermissionListItem;
