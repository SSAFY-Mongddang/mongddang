/** @jsxImportSource @emotion/react */
import { containerCss } from './style';
import { Spinner } from '../Spinner';
import { Icon } from '../Icon';
import imgpath from '@/assets/img/page/not_found.png'

interface LoadingProps {
  height?: string;
}

const Loading = ({ height = '100%' }: LoadingProps) => {
  return (
    <div css={containerCss} style={{ height }}>
      <Icon size={10}>
        <img alt="icon-0" src={imgpath} />
      </Icon>
      <Spinner color="primary" size="lg" />
    </div>
  );
};

export default Loading;
