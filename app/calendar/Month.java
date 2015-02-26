package calendar;

import models.Event;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by valdemarrolfsen on 24.02.15.
 */
public class Month {

    int year;
    int month;

    ArrayList<Day> days;

    public Month(int year, int month, List<models.Event> events) {

        this.year = year;
        this.month = month;
        this.days = new ArrayList<Day>();

        int Y = Calendar.YEAR, M = Calendar.MONTH, D = Calendar.DAY_OF_MONTH;

        for (int i = 1; i <= Month.numbDays(month); i++) {
            Calendar this_day = new GregorianCalendar();
            this_day.set(Y, year);
            this_day.set(M, month);
            this_day.set(D, i);

            ArrayList<models.Event> this_events = new ArrayList<models.Event>();


            for (models.Event event : events) {
                if (event.getEventStarts().get(Y) == this_day.get(Y)
                        && event.getEventStarts().get(M) == this_day.get(M)
                        && event.getEventStarts().get(D) == this_day.get(D)) { this_events.add(event);}
            }

            this.days.add(new Day(this_day, this_events));
        }

        if (month > 12) {
            this.month = 1;
            this.year++;
        } else if (month < 1) {
            this.month = 12;
            this.year--;
        }
    }

    public Month next() {
        return new Month(this.year, this.month++, new ArrayList<models.Event>());
    }

    public Month prev() {
        return new Month(this.year, this.month--, new ArrayList<models.Event>());
    }

    @Override
    public String toString() {
        String out = "<tr><div class = \"g-calendar\">";

        for (Day day : this.days) {
            out += day;
        }

        out += "</div></tr>";

        return out;
    }

    public static int numbDays(int month) {
        switch (month) {
            case 1:case 3:case 5:case 7:case 8:case 10:case 12: return 31;
            case 4:case 6:case 9:case 11: return 30;
            case 2: return 28;
            default: return 0;
        }
    }
}
