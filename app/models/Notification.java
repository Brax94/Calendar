package models;

import com.avaje.ebean.annotation.CreatedTimestamp;
import play.db.ebean.Model;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by eliasbragstadhagen on 11.03.15.
 */
@Entity
public class Notification extends Model {

    public Notification(Bruker bruker, String notification){
        this.bruker = bruker;
        this.notification = notification;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private long notifID;

    @ManyToOne(cascade = CascadeType.ALL)
    private Bruker bruker;

    private String notification;

    @CreatedTimestamp
    private Timestamp dateMade;

    public Timestamp getDateMade(){
        return dateMade;
    }

    public String getNotification(){
        return notification;
    }
}
