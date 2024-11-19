/** @jsxImportSource @emotion/react */
import React, { forwardRef, useState } from 'react';
import { TextFieldProps } from './TextField.types';
import {
  base,
  inputField,
  inputVariants,
  labelField,
  labelVariants,
} from './TextField.styles';

export const TextField = forwardRef<HTMLInputElement | HTMLTextAreaElement, TextFieldProps>(
  (
    {
      color = 'primary',
      defaultValue = '',
      label = 'label',
      maxRows = 10,
      multiLine = false,
      placeholder = '',
      readOnly = false,
      type = 'text',
      variant = 'outlined',
      ...props
    },
    ref
  ) => {
    const [isFocused, setIsFocused] = useState<boolean>(false);
    const [inputValue, setInputValue] = useState<string>(defaultValue || '');

    return (
      <div css={base(multiLine, isFocused, color, variant)} {...props}>
        <div
          css={[
            labelField(color, placeholder, defaultValue, inputValue, isFocused),
            labelVariants[variant](color, placeholder, defaultValue, inputValue, isFocused),
          ]}
        >
          {label}
        </div>
        {multiLine ? (
          <textarea
            css={[inputField(multiLine), inputVariants[variant]()]}
            placeholder={placeholder}
            readOnly={readOnly}
            rows={maxRows}
            onFocus={() => !readOnly && setIsFocused(true)}
            onBlur={() => !readOnly && setIsFocused(false)}
            onChange={(e) => setInputValue(e.target.value)}
            ref={ref as React.Ref<HTMLTextAreaElement>}
            defaultValue={defaultValue}
          />
        ) : (
          <input
            type={type}
            css={[inputField(multiLine), inputVariants[variant]()]}
            placeholder={placeholder}
            readOnly={readOnly}
            defaultValue={defaultValue}
            value={props.value} // 중요
            onFocus={() => !readOnly && setIsFocused(true)}
            onBlur={() => !readOnly && setIsFocused(false)}
            onChange={(e) => setInputValue(e.target.value)}
            ref={ref as React.Ref<HTMLInputElement>}
          />
        )}
      </div>
    );
  }
);

TextField.displayName = 'TextField';
