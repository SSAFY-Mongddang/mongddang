/** @jsxImportSource @emotion/react */
import { Notification } from '@/shared/ui/Notification';
import { Typography } from '@/shared/ui/Typography';

interface OwnModalProps {
  bluehandler: () => void;
  redhandler: () => void;
}

export const BuyModal = ({bluehandler, redhandler}:OwnModalProps) => {
  return (
    <div>
      <Notification
        redHandler={redhandler}
        bluehandler={bluehandler}
        ment={
          <div>
            <Typography color="dark" size="1" weight={600}>
              정말 몰라요 몽땅을 찾을 거야?
            </Typography>
            <Typography color="dark" size="0.75" weight={500}>
              400
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
