/** @jsxImportSource @emotion/react */

import { TextField } from '@/shared/ui/TextField';
import { Typography } from '@/shared/ui/Typography';
import {
  amountCss,
  amountFieldCss,
  amountGroup,
  amountNumCss,
} from './MedicationStandard.styles';
import { useMedicationStandardStore } from '../../model/useMedicationStandardStore';
import { useMedicationAddStore } from '../../model/useMedicationAddStore';
import { useCallback, useEffect } from 'react';
import { debounce, toNumber } from 'lodash';

const MedicationStandard = () => {
  const {
    standardFields,
    addStandardField,
    deleteStandardField,
    setStandardField,
  } = useMedicationStandardStore();

  const { setStandard } = useMedicationAddStore();

  useEffect(() => {
    debounceStandardInput([...standardFields]);
  }, [standardFields]);

  const debounceStandardInput = useCallback(
    debounce((fields) => {
      setStandard(fields);
    }, 500),
    []
  );

  const handleStandardInput = (
    id: number,
    field: 'volume' | 'minGlucose' | 'maxGlucose',
    value: number
  ) => {
    const currentField = standardFields.find((item) => item.id === id);
    if (!currentField) return;
    if (value !== 0) {
      setStandardField(id, field, value);
      debounceStandardInput([...standardFields]);
    }
  };

  const handleStandardField = () => {
    addStandardField();
  };

  const removeStandardField = () => {
    deleteStandardField();
  };

  return (
    <div css={amountGroup} className='medication-standard'>
      <div css={amountCss}>
        <Typography color="dark" size="1" weight={600}>
          약 용량
        </Typography>
        <Typography
          color="dark"
          size="1.25"
          weight={600}
          onClick={handleStandardField}
        >
          +
        </Typography>
      </div>
      {standardFields.map((item, i) => {
        return (
          <div css={amountNumCss} key={`standard-${i}`}>
            <TextField
              color="primary"
              defaultValue=""
              label=""
              placeholder="최소 혈당"
              type="text"
              variant="standard"
              css={amountFieldCss}
              onChange={(e: React.ChangeEvent<HTMLInputElement>) =>
                handleStandardInput(
                  item.id,
                  'minGlucose',
                  toNumber(e.target.value)
                )
              }
            />
            <Typography color="dark" size="1.25" weight={600}>
              ~
            </Typography>
            <TextField
              color="primary"
              defaultValue=""
              label=""
              placeholder="최대 혈당"
              type="text"
              variant="standard"
              css={amountFieldCss}
              onChange={(e: React.ChangeEvent<HTMLInputElement>) =>
                handleStandardInput(
                  item.id,
                  'maxGlucose',
                  toNumber(e.target.value)
                )
              }
            />
            <Typography color="dark" size="1.25" weight={600}>
              =
            </Typography>
            <TextField
              color="primary"
              defaultValue=""
              label=""
              placeholder="단위(U)"
              type="text"
              variant="standard"
              css={amountFieldCss}
              onChange={(e: React.ChangeEvent<HTMLInputElement>) =>
                handleStandardInput(item.id, 'volume', toNumber(e.target.value))
              }
            />
            {standardFields.length === item.id ? (
              <Typography
                color="dark"
                size="1.25"
                weight={600}
                onClick={removeStandardField}
              >
                -
              </Typography>
            ) : (
              <Typography
                color="dark"
                size="1.25"
                weight={600}
                style={{ opacity: 0 }}
              >
                -
              </Typography>
            )}
          </div>
        );
      })}
    </div>
  );
};

export default MedicationStandard;
