package models;

import com.avaje.ebean.annotation.CreatedTimestamp;
import play.db.ebean.Model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Calendar;

/**
 * Created by Elias Bragstad Hagen on 23.02.2015.
 */
@Entity
public class Event extends Model{

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(nullable = false)
    private long eventId;

    private String title;
    private String text;
    private String place;

    @Column(nullable = false)
    private Calendar eventStarts;
    @Column(nullable = false)
    private Calendar eventEnds;
    @CreatedTimestamp
    private Timestamp dateMade;

    public static Finder<Long, Event> find = new Finder<Long, Event> (
            Long.class, Event.class
    );

    public String getTitle() {
        return title;
    }

    public long getEventId() {
        return eventId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Calendar getEventStarts() {
        return eventStarts;
    }

    public void setEventStarts(Calendar eventStarts) {
        this.eventStarts = eventStarts;
    }

    public Calendar getEventEnds() {
        return eventEnds;
    }

    public void setEventEnds(Calendar eventEnds) {
        this.eventEnds = eventEnds;
    }


}
