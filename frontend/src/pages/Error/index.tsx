/** @jsxImportSource @emotion/react */
import { containerCss, descriptionCss } from './styles';
import { FallbackProps } from 'react-error-boundary';
import { useQueryClient } from '@tanstack/react-query';
import { Icon } from '@/shared/ui/Icon';
import { Typography } from '@/shared/ui/Typography';
import { Button } from '@/shared/ui/Button';
import surprised from '../../assets/img/fox_and_capybara/mongddang14_surprised.png';

interface ErrorProps extends FallbackProps {
  height?: string;
}

const Error = ({ height = '100%', error, resetErrorBoundary }: ErrorProps) => {
  const queryClient = useQueryClient();

  const handleRetryClick = () => {
    queryClient.clear();
    resetErrorBoundary();
  };

  return (
    <div css={containerCss} style={{ height }}>
      <Icon size={8} color="danger">
        <img src={surprised} alt="배경 이미지" />
      </Icon>
      <Typography weight={400} color="danger" css={descriptionCss}>
        {error.message}
      </Typography>
      <Button color="danger" handler={handleRetryClick}>
        재시도
      </Button>
    </div>
  );
};

export default Error;
