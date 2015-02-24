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

    @OneToMany
    private long userId;
    @OneToOne
    private long eventId;
    @Column(nullable = false)
    private Calendar alarmTime = null;

    public enum Status{
        ATTENDING, MAYBE, NOT_ATTENDING, UNDECIDED
    }

    private Status status;

    public Affiliated(){
        this.status=Status.UNDECIDED;
    }


}
