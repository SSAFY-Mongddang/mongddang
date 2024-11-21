/** @jsxImportSource @emotion/react */
import { Notification } from '@/shared/ui/Notification';
import { Typography } from '@/shared/ui/Typography';

interface OwnModalProps {
  bluehandler: () => void;
  redhandler: () => void;
}

export const BuyModal = ({ bluehandler, redhandler }: OwnModalProps) => {
  return (
    <div>
      <Notification
        style={{ boxSizing: 'content-box' }}
        redHandler={redhandler}
        bluehandler={bluehandler}
        ment={
          <div>
            <Typography color="dark" size="1" weight={600}>
              정말 몽땅을 찾을 거야?
            </Typography>
          </div>
        }
        width={18}
        twoBtn
        children={['아니', '응']}
        type="confirm"
      />
    </div>
  );
};
