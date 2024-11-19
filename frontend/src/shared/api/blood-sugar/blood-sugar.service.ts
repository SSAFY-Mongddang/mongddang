import axios, { AxiosResponse } from 'axios';
import { api } from '../interceptors';
import { BloodsuagrParams, BloodsugarResponse } from './blood-sugar.type';

export class BloodsugarService {
  static bloodSugarQuery(config: {
    params: BloodsuagrParams;
  }): Promise<AxiosResponse<BloodsugarResponse>> {
    return config.params.nickname === 'test'
      ? axios.get('/public/dummy/blood-sugar.json')
      : api.get(`/api/vital/bloodsugar`, config);
  }
}
