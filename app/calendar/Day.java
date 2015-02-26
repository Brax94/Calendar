package calendar;

import models.Event;

import java.util.ArrayList;
import java.util.Calendar;


/**
 * Created by valdemarrolfsen on 24.02.15.
 */

public class Day {

    ArrayList<models.Event> events;
    Calendar date;

    public Day(Calendar date, ArrayList<models.Event> events) {
        this.date = date;
        this.events = events;
    }

    @Override
    public String toString() {
        String out = "";
        out += "<td class = \"daySquare\" >";
        out += "<div class = \"todaysDate\" >" + date.getTime();

        for (models.Event event : events ) {
            out += "<a href=\"/event/" + event.getEventId() + "\"><div class = \"event\" >" + event.getTitle() + "</div></a>";
        }

        out += "</div></td>";

        return out;
    }

}
