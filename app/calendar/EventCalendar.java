package calendar;

import models.Event;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by valdemarrolfsen on 24.02.15.
 */

public class EventCalendar {

    List<models.Event> events;
    Month this_month;


    public EventCalendar(List<models.Event> events) {
        this.events = new ArrayList<models.Event>();

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Calendar cal = Calendar.getInstance();
        this.this_month = new Month(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), events);
    }

    public String getMonthName() {
        return this.monthName(this_month.month);
    }

    public String getPrevMonthName() {
        return this.monthName((this_month.month - 1));
    }

    public String getNextMonthName() {
        return this.monthName((this_month.month + 1));
    }

    public void next() {
        this_month = this_month.next();
    }

    public void prev() {
        this_month = this_month.prev();
    }

    @Override
    public String toString() {
        return "" + this_month;
    }

    private String monthName(int i) {
        switch (i) {
            case 0: return "Januar";
            case 1: return "Februar";
            case 2: return "Mars";
            case 3: return "April";
            case 4: return "Mai";
            case 5: return "Juni";
            case 6: return "Juli";
            case 7: return "August";
            case 8: return "September";
            case 9: return "Oktober";
            case 10: return "November";
            case 11: return "Desember";
            default: return "Ikke gyldig måte";
        }
    }


}
