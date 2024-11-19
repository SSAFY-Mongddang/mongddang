import { create } from 'zustand';

interface BloodSugarData {
  measurementTime: string;
  bloodSugarLevel: number;
}

interface BloodSugarStore {
  weeklyData: { date: string; average: number }[] | null;
  setWeeklyData: (data: BloodSugarData[]) => void;
}

const useBloodSugarStore = create<BloodSugarStore>((set) => ({
  weeklyData: null,
  setWeeklyData: (data: BloodSugarData[]) => {
    console.log('Incoming data to store:', data);

    if (!data || !Array.isArray(data) || data.length === 0) {
      console.log('No data available');
      return;
    }

    try {
      const dailyAverages = data.reduce((acc, measurement) => {
        // measurementTime에서 날짜만 추출 (YYYY-MM-DD)
        const date = measurement.measurementTime.split('T')[0];
        
        if (!acc[date]) {
          acc[date] = {
            sum: 0,
            count: 0
          };
        }

        acc[date].sum += measurement.bloodSugarLevel;
        acc[date].count += 1;

        console.log(`Processing date: ${date}, value: ${measurement.bloodSugarLevel}`);
        return acc;
      }, {} as Record<string, { sum: number; count: number }>);

      console.log('Daily averages:', dailyAverages);

      const processedData = Object.entries(dailyAverages)
        .map(([date, values]) => ({
          date,
          average: Number((values.sum / values.count).toFixed(1))
        }))
        .sort((a, b) => new Date(a.date).getTime() - new Date(b.date).getTime());

      console.log('Processed data:', processedData);
      set({ weeklyData: processedData });
    } catch (error) {
      console.error('Error processing data:', error);
      set({ weeklyData: [] });
    }
  }
}));

export default useBloodSugarStore;