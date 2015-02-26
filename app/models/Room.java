package models;

import play.db.ebean.Model;

import javax.persistence.*;

/**
 * Created by carlandreasjulsvoll on 24.02.15.
 */
@Entity
public class Room extends Model{

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(nullable = false)

    private long roomId;
    @Column(nullable = false)
    private String name;
    private String text;
    @Column(nullable = false)
    private int roomSize;

    public static Finder<Long, Room> find = new Finder<Long, Room> (
            Long.class, Room.class
    );

    public long getRoomId() {
        return roomId;
    }

    public void setRoomId(long roomId) {
        this.roomId = roomId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getRoomSize() {
        return roomSize;
    }

    public void setRoomSize(int roomSize) {
        this.roomSize = roomSize;
    }
}
