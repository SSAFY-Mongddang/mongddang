export interface BaseApiResponse<T> {
  code: number;
  message: string;
  data: T;
}