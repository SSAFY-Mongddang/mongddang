/** @jsxImportSource @emotion/react */
import { TopBar } from '@/shared/ui/TopBar';
import { useState } from 'react';
import { DataForm } from './ui/dataform';
import { Transition } from './ui/transition';
import { RoleSelector } from './ui/role-selector';
import { useNavigate } from 'react-router-dom';

export type UserRole = 'child' | 'protector' | undefined;

export const SignUp = () => {
  const [step, setStep] = useState<number>(0);
  const [selectedRole, setSelectedRole] = useState<UserRole>(undefined);
  const nav = useNavigate();
  return (
    <div>
      <TopBar
        type="iconpage"
        iconHandler={() => {
          nav('/');
        }}
      >
        회원가입
      </TopBar>
      <Transition data-key={step.toString()}>
        <RoleSelector
          key={0}
          onSubmit={(role: UserRole) => {
            setSelectedRole(role);
            setStep(1);
          }}
        />
        <DataForm key={1} role={selectedRole} />
      </Transition>
    </div>
  );
};
