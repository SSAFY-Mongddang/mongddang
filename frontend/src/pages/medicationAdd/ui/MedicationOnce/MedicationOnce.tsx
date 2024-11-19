/** @jsxImportSource @emotion/react */

import { TextField } from '@/shared/ui/TextField';
import { Typography } from '@/shared/ui/Typography';
import { amountCss, amountOnce, onceFieldCss } from './MedicationOnce.styles';
import { toNumber } from 'lodash';
import { useMedicationAddStore } from '../../model/useMedicationAddStore';

const MedicationOnce = () => {
  const { setStandard } = useMedicationAddStore();
  return (
    <div css={amountOnce}>
      <Typography color="dark" size="1" weight={600}>
        약 용량
      </Typography>
      <div css={amountCss}>
        <TextField
          color="primary"
          defaultValue=""
          label=""
          placeholder="ex) 2"
          type="text"
          variant="standard"
          css={onceFieldCss}
          onChange={(e: React.ChangeEvent<HTMLInputElement>) =>
            setStandard([
              {
                volume: toNumber(e.target.value),
                minGlucose: 0,
                maxGlucose: 0,
              },
            ])
          }
        />
        <Typography color="dark" size="1" weight={500}>
          단위(U) / 1회
        </Typography>
      </div>
    </div>
  );
};

export default MedicationOnce;
