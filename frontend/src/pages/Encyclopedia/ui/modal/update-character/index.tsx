/** @jsxImportSource @emotion/react */
import { Notification } from '@/shared/ui/Notification';
import { Typography } from '@/shared/ui/Typography';

interface OwnModalProps {
  bluehandler: () => void;
  redhandler: () => void;
}

export const UpdateCharacter = ({bluehandler, redhandler}:OwnModalProps) => {
  return (
    <div>
      <Notification
        style={{boxSizing:'content-box'}}
        redHandler={redhandler}
        bluehandler={bluehandler}
        ment={
          <div>
            <Typography color="dark" size="1" weight={600}>
              하트 몽땅을 대장으로 설정할거야?
            </Typography>
            <Typography color="dark" size="0.75" weight={500}>
              *대장으로 설정하면 메인에서 볼 수 있어
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
