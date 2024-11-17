/** @jsxImportSource @emotion/react */
import React, { useState, useEffect } from 'react'
import SamsungHealth from '../../plugin/SamsungHealthPlugin';
import { toggleContainerCss } from '../../../nickname-title/ui/styles';
import PermissionListItem from './PermissionListItem';

type PermissionState = {
  [key: string]: boolean; // key: permission name, value: boolean (true for SUCCESS, false for WAITING)
};

const PermissionToggles: React.FC = () => {
  const [permissions, setPermissions] = useState<PermissionState>({});

  const checkAllPermissions = async () => {
    try {
      const result = await SamsungHealth.checkPermissionStatusForHealthData();

      console.log('All Permissions:', result);

      // Convert result to boolean state
      const newPermissions: PermissionState = Object.entries(result).reduce(
        (acc, [key, value]) => {
          acc[key] = value === 'SUCCESS'; // Convert "SUCCESS" to true, "WAITING" to false
          return acc;
        },
        {} as PermissionState
      );

      setPermissions(newPermissions);
    } catch (error) {
      console.error('Error checking permissions:', error);
    }
  };

  // Toggle permission state
  const togglePermission = (key: string) => {
    setPermissions((prev) => ({
      ...prev,
      [key]: !prev[key], // Toggle the value
    }));
  };

  // Fetch permissions on mount
  useEffect(() => {
    checkAllPermissions();
  }, []);

  return (
    <div css={toggleContainerCss}>
                {/* <div css={toggleContainerCss}>
              <Toggle color="primary" size={2.5} isOn={isBloodGlucosePermOn} onClick={()=>{ checkHealthDataPermission("bloodGlucose")}} />
          </div> */}
      <ul style={{listStyle:"none"}}>
        {Object.entries(permissions).map(([key, value]) => (
          <PermissionListItem
            key={key}
            keyName={key}
            value={value}
            onToggle={() => togglePermission(key)}
          />
        ))}
      </ul>
    </div>
  );
};

// Reusable label component
type PermissionLabelProps = {
  keyName: string;
  children: React.ReactNode;
};

const PermissionLabel: React.FC<PermissionLabelProps> = ({ keyName, children }) => (
  <label>
    {keyName}: {children}
  </label>
);

export default PermissionToggles;