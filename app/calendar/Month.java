package calendar;

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

    ArrayList<Day> days;
    List<Event> events;

    public Month(int year, int month, List<models.Event> events) {

        this.year = year;
        this.month = month;
        this.events = events;
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
        return new Month(this.year, this.month + 1, this.events);
    }

    public Month prev() {
        return new Month(this.year, this.month - 1, this.events);
    }


    public static int getNumberOfPushes(int year, int month) throws ParseException{
        String input_date = "";
        if(month<10){
            input_date="01/0"+month+"/"+year;
        }else{
            input_date="01/"+month+"/"+year;
        }
        SimpleDateFormat format1=new SimpleDateFormat("dd/MM/yyyy");
        Date dt1=format1.parse(input_date);
        DateFormat format2=new SimpleDateFormat("EEEE");
        String finalDay=format2.format(dt1);
        switch(finalDay) {
            case "mandag":return 0;
            case "tirsdag": return 1;
            case "onsdag": return 2;
            case "torsdag": return 3;
            case "fredag": return 4;
            case "lørdag": return 5;
            case "søndag": return 6; }
        return -1; //-1 er error
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
            case 0:case 2:case 4:case 6:case 7:case 9:case 11: return 31;
            case 3:case 5:case 8:case 10: return 30;
            case 1: return 28;
            default: return 0;
        }
    }
}
