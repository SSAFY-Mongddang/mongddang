import { DatePicker, DatePickerProps } from '@mantine/dates';
import { useEffect, useState } from 'react';
import { formatTwoDigit } from '@/shared/lib/date';
import { CalendarService } from '../model';
import calendarStyle from './style.module.css';
import { useNavigate } from 'react-router-dom';
import dayjs from 'dayjs';

export const RecordCalendar = () => {
  const [dateValue, setDateValue] = useState<Date | null>(null);
  const [currentMonth, setCurrentMonth] = useState<Date | null>(null);
  const [lastTappedDate, setLastTappedDate] = useState<Date | null>(null);
  const nav = useNavigate();

  const setDayProps: DatePickerProps['getDayProps'] = (date) => {
    // 현재 날짜에 밑줄 표시
    if (CalendarService.isToday(date)) {
      return {
        style: { textDecoration: 'underline' },
      };
    }

    // 토요일의 경우 파란색 글자
    if (
      CalendarService.isCurrentMonth(date, currentMonth) &&
      CalendarService.isSaturday(date)
    ) {
      return {
        style: { color: 'blue' },
      };
    }

    return {};
  };

  const handleDateTap = (tappedDate: Date | null) => {
    if (
      tappedDate &&
      lastTappedDate &&
      CalendarService.isSameDate(lastTappedDate, tappedDate)
    ) {
      setDateValue(null);
      nav(`/record/${dayjs(tappedDate).format('YYYY-MM-DD')}`);
    } else {
      setLastTappedDate(tappedDate);
      setDateValue(tappedDate);
    }
  };

  const handleNextMonth = () => {
    if (!currentMonth) {
      throw new Error('Current Month not assigned');
    }
    // 다음 달을 클릭하면 현재 월 갱신
    setCurrentMonth(CalendarService.getNextMonth(currentMonth));
    // 월이 변경될 때 마지막 클릭 날짜, 현재 날짜 초기화
    setDateValue(null);
    setLastTappedDate(null);
  };

  const handlePreviousMonth = () => {
    if (!currentMonth) {
      throw new Error('Current Month not assigned');
    }
    // 이전 달을 클릭하면 현재 월 갱신
    setCurrentMonth(CalendarService.getPreviousMonth(currentMonth));
    // 월이 변경될 때 마지막 클릭 날짜, 현재 날짜 초기화
    setDateValue(null);
    setLastTappedDate(null);
  };

  // 일자를 두자리 포맷으로 설정
  const renderDay = (date: Date) => {
    const day = date.getDate();
    return <div>{formatTwoDigit(day)}</div>;
  };

  useEffect(() => {
    setCurrentMonth(new Date());
    return () => {
      setDateValue(null);
      setLastTappedDate(null);
      setCurrentMonth(null);
    };
  }, []);

  return (
    <>
      <DatePicker
        value={dateValue}
        onChange={(date) => handleDateTap(date)}
        type="default"
        size="lg"
        getDayProps={setDayProps}
        renderDay={renderDay}
        onNextMonth={handleNextMonth}
        onPreviousMonth={handlePreviousMonth}
        classNames={{
          day: calendarStyle.day,
          weekday: calendarStyle.weekday,
          calendarHeader: calendarStyle.calendarHeader,
        }}
        monthLabelFormat="YYYY년 MM월"
      />
    </>
  );
};
