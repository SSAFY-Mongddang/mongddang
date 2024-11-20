/** @jsxImportSource @emotion/react */

import { TextField } from '@/shared/ui/TextField';
import { Typography } from '@/shared/ui/Typography';
import {
  inputPeriodCss,
  periodCss,
  periodFieldCss,
  periodItemCss,
} from './MedicationPeriod.styles';
import { useCallback, useState } from 'react';
import { debounce } from 'lodash';
import { useMedicationAddStore } from '../../model/useMedicationAddStore';

const MedicationPeriod = () => {
  const { setMedicationPeriod } = useMedicationAddStore();
  const [period, setPeriod] = useState({
    startYear: 0,
    startMonth: 0,
    startDate: 0,
    endYear: 0,
    endMonth: 0,
    endDate: 0,
  });
  const debouncePeriodInput = useCallback(
    debounce((updatedPeriod) => {
      const startDate = new Date(
        `${updatedPeriod.startYear}-${updatedPeriod.startMonth}-${updatedPeriod.startDate}`
      );
      const endDate = new Date(
        `${updatedPeriod.endYear}-${updatedPeriod.endMonth}-${updatedPeriod.endDate}`
      );
      setMedicationPeriod(startDate, endDate);
    }, 500),
    []
  );

  const handlePeriodInput = (key: string, value: string) => {
    const updatedPeriod = { ...period, [key]: value };
    setPeriod(updatedPeriod);
    debouncePeriodInput(updatedPeriod);
  };

  return (
    <div css={inputPeriodCss} className='medication-period'>
      <Typography color="dark" size="1" weight={600}>
        약 먹는 기간
      </Typography>
      <div css={periodCss}>
        <div css={periodItemCss}>
          <TextField
            color="primary"
            defaultValue=""
            label="시작)"
            placeholder="YYYY"
            type="text"
            variant="standard"
            css={periodFieldCss}
            onChange={(e: React.ChangeEvent<HTMLInputElement>) =>
              handlePeriodInput('startYear', e.target.value)
            }
          />
          <Typography color="dark" size="1" weight={500}>
            년
          </Typography>
          <TextField
            color="primary"
            defaultValue=""
            label=""
            placeholder="MM"
            type="text"
            variant="standard"
            css={periodFieldCss}
            onChange={(e: React.ChangeEvent<HTMLInputElement>) =>
              handlePeriodInput('startMonth', e.target.value)
            }
          />
          <Typography color="dark" size="1" weight={500}>
            월
          </Typography>
          <TextField
            color="primary"
            defaultValue=""
            label=""
            placeholder="DD"
            type="text"
            variant="standard"
            css={periodFieldCss}
            onChange={(e: React.ChangeEvent<HTMLInputElement>) =>
              handlePeriodInput('startDate', e.target.value)
            }
          />
          <Typography color="dark" size="1" weight={500}>
            일
          </Typography>
        </div>
        <div css={periodItemCss}>
          <TextField
            color="primary"
            defaultValue=""
            label="끝)"
            placeholder="YYYY"
            type="text"
            variant="standard"
            css={periodFieldCss}
            onChange={(e: React.ChangeEvent<HTMLInputElement>) =>
              handlePeriodInput('endYear', e.target.value)
            }
          />
          <Typography color="dark" size="1" weight={500}>
            년
          </Typography>
          <TextField
            color="primary"
            defaultValue=""
            label=""
            placeholder="MM"
            type="text"
            variant="standard"
            css={periodFieldCss}
            onChange={(e: React.ChangeEvent<HTMLInputElement>) =>
              handlePeriodInput('endMonth', e.target.value)
            }
          />
          <Typography color="dark" size="1" weight={500}>
            월
          </Typography>
          <TextField
            color="primary"
            defaultValue=""
            label=""
            placeholder="DD"
            type="text"
            variant="standard"
            css={periodFieldCss}
            onChange={(e: React.ChangeEvent<HTMLInputElement>) =>
              handlePeriodInput('endDate', e.target.value)
            }
          />
          <Typography color="dark" size="1" weight={500}>
            일
          </Typography>
        </div>
      </div>
    </div>
  );
};

export default MedicationPeriod;
