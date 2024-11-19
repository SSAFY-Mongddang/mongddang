export class CalendarService {
  static getNextMonth(date: Date): Date {
    const nextMonth = new Date(date);
    nextMonth.setMonth(date.getMonth() + 1);
    return nextMonth;
  }

  static getPreviousMonth(date: Date): Date {
    const previousMonth = new Date(date);
    previousMonth.setMonth(date.getMonth() - 1);
    return previousMonth;
  }

  static isCurrentMonth(date: Date | null, compareDate: Date | null): boolean {
    if (!compareDate || !date) return false;
    return date.getMonth() === compareDate.getMonth();
  }

  static isSaturday(date: Date): boolean {
    return date.getDay() === 6;
  }

  static isToday(date: Date): boolean {
    const today = new Date();
    return (
      date.getDate() === today.getDate() &&
      date.getMonth() === today.getMonth() &&
      date.getFullYear() === today.getFullYear()
    );
  }

  static isSameDate(date: Date | null, compareDate: Date | null): boolean {
    if (!date || !compareDate) return false;
    return (
      date.getFullYear() === compareDate.getFullYear() &&
      date.getMonth() === compareDate.getMonth() &&
      date.getDate() === compareDate.getDate()
    );
  }
}
