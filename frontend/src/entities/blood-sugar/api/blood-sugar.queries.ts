import { Bloodsugar, BloodsugarService } from '@/shared/api/blood-sugar';
import { queryOptions } from '@tanstack/react-query';
import dayjs from 'dayjs';
// import { BloodsugarFilter } from '../model';
// import dayjs from 'dayjs';
import { sample } from 'lodash';

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
      queryFn: async (): Promise<Bloodsugar[]> => {
        console.log(nickname, date);
        const randomDate = sample([
          '2024-02-26',
          '2024-02-27',
          '2024-02-28',
          '2024-02-29',
          '2024-03-01',
          '2024-03-02',
          '2024-03-03',
        ]);

        const { data } = await BloodsugarService.bloodSugarQuery({
          params: {
            nickname: '어린이 서원',
            date: randomDate,
          },
        });

        // console.log('********************************');
        // console.log('********************************');
        // console.log(data);
        // console.log(data);
        // console.log(data);
        // console.log(data);
        // console.log('********************************');
        // console.log('********************************');

        // const formattedData = data.data.bloodSugar.map((item) => ({
        //   ...item,
        //   measurementTime: dayjs(item.measurementTime).format('HH:mm'),
        // }));
        // return formattedData;

        const filteredData = data.data.filter((item) => {
          const itemHour = dayjs(item.measurementTime).format('HH:mm');
          const currentHour = dayjs().subtract(10, 'hour').format('HH:mm');
          return itemHour < currentHour;
        });

        return filteredData;
      },
      enabled: !!nickname,
    });
  }
}
