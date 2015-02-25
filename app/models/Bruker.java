package models;

import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.*;

/**
 * Created by carlandreasjulsvoll on 24.02.15.
 */
@Entity
public class Bruker extends Model {

    @Id
    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    @Constraints.Email
    @Column(nullable = false)
    private String email;
    private String firstName;
    private String lastName;


    public static Finder<String, Bruker> find = new Finder<String, Bruker> (
            String.class, Bruker.class
    );

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
