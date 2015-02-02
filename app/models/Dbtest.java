package models;

import play.db.ebean.Model;

import javax.persistence.*;

/**
 * Created by eliasbragstadhagen on 30.01.15.
 */
@Entity
public class Dbtest extends Model {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String text;

}
