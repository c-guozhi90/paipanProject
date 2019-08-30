package com.chenyue.allpaipan.calendar;

// new Date type that are designated for calendar calculation
class NewDate {
    private int year;
    private int month;
    private int day;
    private int hour;
    private int min;
    private String ganzhi;
    private String yinli;

    public NewDate(int year, int month, int day, int hour, int min) throws InvalidDateException {
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.min = min;
        if (year == 1582 && day > 4 && day < 15) {
            throw new InvalidDateException();
        }
    }

    public NewDate(int year, int month, int day) throws InvalidDateException {
        new NewDate(year, month, day, 0, 0);
    }

    /**
     * * this function calculate the difference in days between two arbitrary date
     * * @param laterDate
     * * @param earlierDate
     * * @return days difference
     */
    public static int calDaysDiff(NewDate earlierDate, NewDate laterDate) {
        int daysDiff = 0;
        int startYear = earlierDate.getYear();
        int endYear = laterDate.getYear();
        int startMon = earlierDate.getMonth() + 1;
        int endMon = laterDate.getYear() != earlierDate.getYear() ? 12 : laterDate.getMonth();
        boolean calRest = false;

        if (startYear == endYear && startMon == endMon) {
            daysDiff = laterDate.getDay() - earlierDate.getDay();
        } else {
            if (startYear != endYear) {
                calRest = true;
            }
            daysDiff = getMonthDays(startYear, startMon) - earlierDate.getDay();
        }

        for (int monthThrough = startMon; monthThrough < endMon; monthThrough++) {
            daysDiff += getMonthDays(earlierDate.getYear(), monthThrough);
        }

        for (int yearThrough = startYear; yearThrough < endYear; yearThrough++) {
            daysDiff += getYearDays(yearThrough);
        }

        if (calRest) {
            for (int monthThrough = 1; monthThrough < endMon; monthThrough++) {
                daysDiff += getMonthDays(laterDate);
            }
        }

        daysDiff += laterDate.getDay();

        if (isBeforP(earlierDate) && !isBeforP(laterDate)) {
            daysDiff -= 10; // to deal with the special time in Oct 1582
        }

        return daysDiff;
    }

    public static int getYearDays(int yearIn) {
        if (yearIn == 1582) {
            return 355;
        } else if (Calender.isLeap(yearIn)) {
            return 366;
        } else {
            return 365;
        }
    }

    public static int getMonthDays(NewDate dateIn) {
        switch (dateIn.getMonth()) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 12:
                return 31;
            case 2:
                if (Calender.isLeap(dateIn.getYear()))
                    return 29;
                else
                    return 28;
            default:
                return 30;
        }
    }

    public static int getMonthDays(int year, int month) {
        NewDate dateIns = null;
        try {
            dateIns = new NewDate(year, month, 1);
        } catch (InvalidDateException e) {

        }
        return getMonthDays(dateIns);
    }

    public static boolean isBeforP(NewDate dateIn) {
        if (dateIn.getYear() == 1582) {
            if (dateIn.getMonth() < 10) {
                return true;
            } else if (dateIn.getMonth() == 10 && dateIn.getDay() <= 4) {
                return true;
            }
        } else if (dateIn.getYear() < 1582) {
            return true;
        }
        return false;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public int getHour() {
        return hour;
    }

    public int getMin() {
        return min;
    }

    public String parseDate() {
        String result = "";
        String strDay = "";
        if (this.day < 10) {
            strDay = "0" + this.day;
        } else {
            strDay = String.valueOf(this.day);
        }
        result = result + this.year + this.month + strDay + this.min;
        return result;
    }

    public int parseDate(boolean parseToInt) {
        return Integer.parseInt(this.parseDate());
    }
}
