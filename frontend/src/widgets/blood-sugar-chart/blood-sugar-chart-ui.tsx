import { BloodsugarQueries } from '@/entities/blood-sugar/api';
import { useQuery } from '@tanstack/react-query';
import { useParams } from 'react-router-dom';
import { LineChart } from 'recharts';

export const BloodSugarChart = () => {
  const { date } = useParams();
  if (typeof date === 'undefined') {
    throw new Error('Impossible date');
  }
  const 

  const { data, isError, isLoading } = useQuery(
    BloodsugarQueries.todayBloodSugarQuery(nickname, date)
  );

  if (isError) {
    console.log('Bloodsugarchart error');
    throw new Error('Bloodsugarchart error');
  }

  if (isLoading) return null;
  return (
    <>
      <LineChart
        h={300}
        w={1000}
        data={data}
        dataKey="measurementTime"
        series={[{ name: 'bloodSugarLevel', color: 'indigo.6' }]}
        dotProps={{ r: 6, strokeWidth: 2, stroke: '#fff' }}
        curveType="linear"
        // lineProps={checkMeal}
        style={{ margin: '10px' }}
      />
    </>
  );
};
