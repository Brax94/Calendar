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
        this.this_month = new Month(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1, events);
    }

    @Override
    public String toString() {
        return "" + this_month;
    }


}
