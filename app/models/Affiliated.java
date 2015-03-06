package models;

import play.db.ebean.Model;

import javax.persistence.*;
import java.util.Calendar;

/**
 * Created by carlandreasjulsvoll on 24.02.15.
 */
@Entity
public class Affiliated extends Model{

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(nullable = false)
    private long affiliatedId;

    @OneToOne
    private Bruker bruker;
    @OneToOne
    private Event event;
    @Column(nullable = false)
    private Calendar alarmTime = null;

    public static Finder<Bruker, Affiliated> find = new Model.Finder<Bruker, Affiliated> (
            Bruker.class, Affiliated.class
    );

    public enum Status{
        ATTENDING, MAYBE, NOT_ATTENDING, UNDECIDED
    }

    private Status status;

    public Affiliated(Bruker bruker, Event event){
        this.status=Status.UNDECIDED;
        setBruker(bruker);
        setEvent(event);
    }

    public Bruker getBruker() {
        return bruker;
    }

    public void setBruker(Bruker bruker) {
        this.bruker = bruker;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Calendar getAlarmTime() {
        return alarmTime;
    }

    public void setAlarmTime(Calendar alarmTime) {
        this.alarmTime = alarmTime;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
