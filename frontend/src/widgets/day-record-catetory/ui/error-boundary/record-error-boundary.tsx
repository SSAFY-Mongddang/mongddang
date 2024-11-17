import { RecordCategory } from '@/entities/day-record/api';
import { ErrorMessage } from './error-message';
import Loading from '@/shared/ui/Loading';

interface RecordErrorBoundaryProps {
  isError: boolean;
  error: Error | null;
  isLoading: boolean;
  category: RecordCategory;
  children: React.ReactNode;
}

export const RecordErrorBoundary = ({
  isError,
  error,
  isLoading,
  category,
  children,
}: RecordErrorBoundaryProps) => {

  if (isLoading) {
    return <Loading />;
  }

  if (isError) {
    return <ErrorMessage error={error} category={category} />;
  }

  return <>{children}</>;
};
