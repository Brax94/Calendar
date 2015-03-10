package models;



import play.db.ebean.Model;

import javax.persistence.*;
import java.util.Calendar;

/**
 * Created by carlandreasjulsvoll on 05.03.15.
 */

@Entity
public class RomBooking extends Model{

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(nullable = false)

    private long roomBookingID;
    @OneToOne
    @Column(nullable = false)
    private Room room;
    @OneToOne
    @Column(nullable = false)
    private Event event;
    @Column(nullable = false)
    private Calendar eventStarts;
    @Column(nullable = false)
    private Calendar eventEnds;

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
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
