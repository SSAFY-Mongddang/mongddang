import { BloodsugarService } from '@/shared/api/blood-sugar';
import { queryOptions } from '@tanstack/react-query';
// import { BloodsugarFilter } from '../model';
import dayjs from 'dayjs';

export class BloodsugarQueries {
  static readonly queryKeys = {
    all: ['bloodSugar'] as const,
    // lists: () => [...this.queryKeys.all, 'list'] as const,
    // filtered: (filters: BloodsugarFilter) =>
    //   [...this.queryKeys.lists(), { filters }] as const,
  };

  static todayBloodSugarQuery(nickname: string, date: string) {
    return queryOptions({
      queryKey: [...this.queryKeys.all, date],
      queryFn: async () => {
        const { data } = await BloodsugarService.bloodSugarQuery(
          nickname,
          date
        );
        const formattedData = data.data.bloodSugar.map((item) => ({
          ...item,
          measurementTime: dayjs(item.measurementTime).format('HH:mm'),
        }));
        return formattedData;
      },
      enabled: !!nickname,
    });
  }
}
