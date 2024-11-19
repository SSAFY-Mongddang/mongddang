/** @jsxImportSource @emotion/react */
import { Typography } from '@/shared/ui/Typography';
import { DietModalBtnData } from '../../constants/dietModalBtnData';
import { Button } from '@/shared/ui/Button';
import { btn, btnGroup, btnTextCss } from './DietModalBtnGroup.styles';

type DietModalBtnGroupProps = {
  selectedMealTime: string;
  handleBtnClick: (info: string) => void;
};

const DietModalBtnGroup = (props: DietModalBtnGroupProps) => {
  return (
    <div css={btnGroup}>
      {DietModalBtnData.map((item, btnId) => {
        return (
          <Button
            key={`dietModalBtn-${btnId}`}
            color="light"
            fontSize="1"
            fullwidth
            variant="outlined"
            handler={() => {
              props.handleBtnClick(item.info);
            }}
            css={btn}
            style={{
              backgroundColor:
                props.selectedMealTime === item.info ? '#8FDCFF' : '#fff',
            }}
          >
            <div css={btnTextCss}>
              <img src={item.icon} />
              <Typography color="dark" size="1" weight={500}>
                {item.text}
              </Typography>
            </div>
          </Button>
        );
      })}
    </div>
  );
};

export default DietModalBtnGroup;
