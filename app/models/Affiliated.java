package models;

import play.db.ebean.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.Calendar;

/**
 * Created by carlandreasjulsvoll on 24.02.15.
 */
@Entity
public class Affiliated extends Model{

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

    public Affiliated(){
        this.status=Status.UNDECIDED;
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
