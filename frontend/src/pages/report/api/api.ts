import { api } from '@/shared/api/interceptors';

export const getreport = () => {
  return api({
    url: '/api/vital/bloodsugar/report?nickname=어린이 서원&startDate=2024-02-26&endDate=2024-03-03',
    method: 'get',
  });
};

interface GptAnalysisData {
  gmi: number;
  abg: number;
  cv: number;
  tir: number;
}

interface GptResponse {
  message: string;
  data: string;
}
export const postgpt = async (data: GptAnalysisData) => {
  const response = await api.post<GptResponse>(
    '/api/vital/bloodsugar/report/gpt?nickname=어린이 서원&startDate=2024-02-26&endDate=2024-03-03',
    {
      message: `gmi: ${data.gmi}, abg: ${data.abg}, cv: ${data.cv}, tir: ${data.tir}`,
    }
  );
  return response.data;
};
