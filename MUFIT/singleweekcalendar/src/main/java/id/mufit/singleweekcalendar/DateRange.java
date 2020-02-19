package id.mufit.singleweekcalendar;

import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

class DateRange {

    private static String TAG = DateRange.class.getSimpleName();

    List<Week> calculateDates(long today, int months){

        List<Week> weeks = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(today);

        Calendar endOfCal = (Calendar) cal.clone();
        endOfCal.add(Calendar.MONTH, months);
        getWeeksFromRange(weeks, cal, endOfCal);
        return weeks;
    }

    /**
     * Logic is as follows:
     *
     * Iterate through each date from start to end, add them to Week object.
     *
     * A Week object encompasses days from Sunday - Saturday, and a Week object is added to List weeks when Saturday is reached,
     * or when the month changes. In this case, the Week object is partially filled and is added right away to List weeks.
     *
     * If the month ends on Saturday, the Week object will be full, and a boolean previousEndOfMonthIsSaturday is set to true
     * to prevent adding an empty Week into List weeks.
     * @param weeks List to be populated
     * @param start start date
     * @param end end date
     */
    private void getWeeksFromRange(List<Week> weeks, Calendar start, Calendar end){
        boolean previousEndOfMonthIsSaturday = false;
        int lastMonth = -1;
        Week week = new Week();
        while(start.getTime().before(end.getTime())){

            int dayOfWeek = start.get(Calendar.DAY_OF_WEEK);
            int day = start.get(Calendar.DAY_OF_MONTH);
            int month = start.get(Calendar.MONTH);
            int year = start.get(Calendar.YEAR);
            long dateInMillis = start.getTimeInMillis();

            if(lastMonth == -1 ) {
                // do nothing
            } else if (lastMonth != month){
                //If currently iterated date is a new month,

                if(!previousEndOfMonthIsSaturday){
                    //If last month
                    //Add the current Week object to the list right away. This will result in a week with partially filled dates
                    weeks.add(week);
                    week = new Week();

                } else {
                    previousEndOfMonthIsSaturday = false;
                    Log.d(TAG, "Last Month end of month was saturday; skip adding an empty getWeek");
                }
                //dont increment calendar
            } else {
                //Responsible for populating full weeks (represented by same month)
                CalendarDay cd = new CalendarDay(day, month, year, dateInMillis);
                switch(dayOfWeek){
                    case Calendar.SUNDAY:
                        week.setSun(cd);
                        break;
                    case Calendar.MONDAY:
                        week.setMon(cd);
                        break;
                    case Calendar.TUESDAY:
                        week.setTue(cd);
                        break;
                    case Calendar.WEDNESDAY:
                        week.setWed(cd);
                        break;
                    case Calendar.THURSDAY:
                        week.setThu(cd);
                        break;
                    case Calendar.FRIDAY:
                        week.setFri(cd);
                        break;
                    case Calendar.SATURDAY:
                        week.setSat(cd);


                        //Every Saturdays the Week object is added to list weeks, and a new week is created
                        weeks.add(week);
                        week = new Week();

                        //Get end of current month
                        int endOfMonth = start.getActualMaximum(Calendar.DAY_OF_MONTH);

                        //If month ends on saturday
                        if(endOfMonth == day){
                            previousEndOfMonthIsSaturday = true;
                        }
                        break;
                    default:
                        Log.e(TAG, "Default");
                }
                start.add(Calendar.DATE, 1);
            }


            lastMonth = month;

        }

        //If last added getWeek is fully filled, don't add
        //Else, add it to calendar (because add getWeek is only executed on Saturdays which can only happen if getWeek is fully filled)
        if(!week.isEmpty()){
            weeks.add(week);
        }
    }

}
