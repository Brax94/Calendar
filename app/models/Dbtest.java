package models;

import play.db.ebean.Model;

/**
 * Created by eliasbragstadhagen on 30.01.15.
 */
@entity
public class Dbtest extends Model {

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
