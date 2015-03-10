package models;



import play.db.ebean.Model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import models.Room;


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

    public static Finder<Long, RomBooking> find = new Finder<Long, RomBooking> (
            Long.class, RomBooking.class
    );

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

    public static List<Room> availableRooms(Calendar eventStarts, Calendar eventEnds){
        List<RomBooking> romBookingList = RomBooking.find.all();
        List<Room> availableRooms = Room.find.all();;
        for(RomBooking romBooking : romBookingList){
            if(isColliding(eventStarts, eventEnds, romBooking)){
                availableRooms.remove(romBooking.getRoom());
            }
        }
        return availableRooms;
    }

    public static boolean isColliding(Calendar start,Calendar end,RomBooking book){
        if((start.getTimeInMillis()-book.getEventStarts().getTimeInMillis())<0 && (end.getTimeInMillis()-book.getEventEnds().getTimeInMillis())>0){
            return true;
        }else if((start.getTimeInMillis()-book.getEventStarts().getTimeInMillis())<0 && (end.getTimeInMillis()-book.getEventStarts().getTimeInMillis()>0)){
            return true;
        }else if((start.getTimeInMillis()-book.getEventStarts().getTimeInMillis())>=0 && (end.getTimeInMillis()-book.getEventStarts().getTimeInMillis()<=0)){
            return true;
        }else if((start.getTimeInMillis()-book.getEventStarts().getTimeInMillis())>=0 && (end.getTimeInMillis()-book.getEventStarts().getTimeInMillis()>=0)){
            return true;
        }
        return false;
    }

}
