/** @jsxImportSource @emotion/react */

import { Typography } from "@/shared/ui/Typography";
import { base } from "../item/stylest";
import { useQuery } from "@tanstack/react-query";
import { postgpt } from "../../api/api";

interface GptContentProps {
  data: {
    gmi: number;
    abg: number;
    cv: number;
    tir: number;
  };
}

export const GptContent = ({ data }: GptContentProps) => {
  const { data: gptResponse, isLoading, error } = useQuery({
    queryKey: ['gptAnalysis', data],
    queryFn: () => postgpt(data),
    enabled: !!data, // data가 있을 때만 쿼리 실행
  });

  return (
    <div css={base}>
      <Typography color="dark" size="1" weight={600}>
        이번주 혈당 보고서
      </Typography>
      <div style={{ display: 'flex', alignItems: 'flex-end', marginTop: '0.5rem' }}>
        <Typography color="dark" size="1" weight={500}>
          {isLoading ? '분석중...' : 
           error ? '분석 중 오류가 발생했습니다.' : 
           gptResponse?.data || '내용'}
        </Typography>
      </div>
    </div>
  );
};