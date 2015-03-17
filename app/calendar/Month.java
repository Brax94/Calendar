package calendar;

import models.Bruker;
import models.Event;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by valdemarrolfsen on 24.02.15.
 */
public class Month {

    int year;
    int month;
    int numb_push;

    Bruker bruker;

    ArrayList<Day> days;
    List<Event> events;

    public Month(int year, int month, List<models.Event> events, Bruker bruker) {

        this.year = year;
        this.month = month;
        this.events = events;
        this.bruker = bruker;
        this.days = new ArrayList<Day>();

        int Y = Calendar.YEAR, M = Calendar.MONTH, D = Calendar.DAY_OF_MONTH;

        for (int i = 1; i <= Month.numbDays(month); i++) {
            Calendar this_day = new GregorianCalendar();
            this_day.set(Y, year);
            this_day.set(M, month);
            this_day.set(D, i);
            if (i==1)
                this.numb_push = this.getNumberOfPushes(this_day.get(Calendar.DAY_OF_WEEK));

            ArrayList<models.Event> this_events = new ArrayList<models.Event>();


            for (models.Event event : events) {
                if (event.getEventStarts().get(Y) == this_day.get(Y)
                        && event.getEventStarts().get(M) == this_day.get(M)
                        && event.getEventStarts().get(D) == this_day.get(D)) {System.out.println("he"); this_events.add(event);}
            }

            this.days.add(new Day(this_day, this_events, bruker));
        }

        System.out.println(numb_push);

        if (month > 12) {
            this.month = 1;
            this.year++;
        } else if (month < 1) {
            this.month = 12;
            this.year--;
        }
    }

    public Month next() {
        return new Month(this.year, this.month + 1, this.events, bruker);
    }

    public Month prev() {
        return new Month(this.year, this.month - 1, this.events, bruker);
    }


    public static int getNumberOfPushes(int dayOfWeek){

        switch(dayOfWeek) {
            case 2:return 0;
            case 3: return 1;
            case 4: return 2;
            case 5: return 3;
            case 6: return 4;
            case 7: return 5;
            case 1: return 6; }
        return -1; //-1 er error
    }

    @Override
    public String toString() {
        String out = "<tr><div class = \"g-calendar\">";

        for (int i = 0; i<numb_push; i++)
            out += "<div class = \"emptyDate\"></div>";

        for (Day day : this.days) {
            out += day;
        }

        out += "</div></tr>";

        return out;
    }

    public static int numbDays(int month) {
        switch (month) {
            case 0:case 2:case 4:case 6:case 7:case 9:case 11: return 31;
            case 3:case 5:case 8:case 10: return 30;
            case 1: return 28;
            default: return 0;
        }
    }
}
