package calendar;

import models.Bruker;
import models.Event;

import java.util.ArrayList;
import java.util.Calendar;


/**
 * Created by valdemarrolfsen on 24.02.15.
 */

public class Day {

    ArrayList<models.Event> events;
    Calendar date;
    Bruker bruker;

    public Day(Calendar date, ArrayList<models.Event> events, Bruker bruker) {
        this.date = date;
        this.events = events;
        this.bruker = bruker;
    }

    @Override
    public String toString() {
        String out = "";
        out += "<td class = \"daySquare\" >";
        out += "<div class = \"todaysDate\" >" + date.get(Calendar.DAY_OF_MONTH);

        for (models.Event event : events ) {
            if (bruker.getUsername().equals(event.getCreator().getUsername()))
                out += "<a href=\"/event/" + event.getEventId() + "\"><div class = \"event blue\" >" + event.getTitle() + "</div></a>";
            else
                out += "<a href=\"/event/" + event.getEventId() + "\"><div class = \"event red\" >" + event.getTitle() + "</div></a>";
        }

        out += "</div></td>";

        return out;
    }

}
