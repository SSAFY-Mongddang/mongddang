/** @jsxImportSource @emotion/react */

import { TextField } from '@/shared/ui/TextField';
import { Typography } from '@/shared/ui/Typography';
import {
  amountCss,
  amountGroup,
  amountNumCss,
  timeFieldCss,
} from './MedicationTime.styles';
import { useMedicationTimeStore } from '../../model/useMedicationTimeStore';
import { useCallback, useEffect } from 'react';
import { debounce, toNumber } from 'lodash';
import { useMedicationAddStore } from '../../model/useMedicationAddStore';

const MedicationTime = () => {
  const { timeFields, addTimeField, deleteTimeField, setTimeField } =
    useMedicationTimeStore();

  const { setMedicationTime } = useMedicationAddStore();

  useEffect(() => {
    debounceMedTimeInput([...timeFields]);
  }, [timeFields]);

  const debounceMedTimeInput = useCallback(
    debounce((fields) => {
      setMedicationTime(fields);
    }, 500),
    []
  );

  const handleMedTimeInput = (
    id: number,
    field: 'hour' | 'minute',
    value: number
  ) => {
    const currentField = timeFields.find((item) => item.id === id);
    if (!currentField) return;

    setTimeField(id, field, value);
    debounceMedTimeInput([...timeFields]);
  };

  const handleTimeField = () => {
    addTimeField();
  };

  const removeTimeField = () => {
    deleteTimeField();
  };

  return (
    <div css={amountGroup}>
      <div css={amountCss}>
        <Typography color="dark" size="1" weight={600}>
          약 먹는 시간
        </Typography>
        <Typography
          color="dark"
          size="1.25"
          weight={600}
          onClick={handleTimeField}
        >
          +
        </Typography>
      </div>
      {timeFields.map((item) => {
        return (
          <div css={amountNumCss}>
            <TextField
              color="primary"
              defaultValue=""
              label=""
              placeholder="ex) 17"
              type="text"
              variant="standard"
              css={timeFieldCss}
              onChange={(e: React.ChangeEvent<HTMLInputElement>) =>
                handleMedTimeInput(item.id, 'hour', toNumber(e.target.value))
              }
            />
            <Typography color="dark" size="1" weight={500}>
              시
            </Typography>
            <TextField
              color="primary"
              defaultValue=""
              label=""
              placeholder="ex) 30"
              type="text"
              variant="standard"
              css={timeFieldCss}
              onChange={(e: React.ChangeEvent<HTMLInputElement>) =>
                handleMedTimeInput(item.id, 'minute', toNumber(e.target.value))
              }
            />
            <Typography color="dark" size="1" weight={500}>
              분
            </Typography>
            {timeFields.length === item.id ? (
              <Typography
                color="dark"
                size="1.5"
                weight={600}
                onClick={() => removeTimeField()}
              >
                -
              </Typography>
            ) : (
              <Typography
                color="dark"
                size="1.5"
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

export default MedicationTime;
