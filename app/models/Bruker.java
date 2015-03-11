package models;

import play.mvc.Result;
import play.db.ebean.Model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static play.mvc.Controller.request;
import static play.mvc.Controller.session;
import static play.mvc.Results.redirect;

/**
 * Created by carlandreasjulsvoll on 24.02.15.
 */
@Entity
public class Bruker extends Model {

    @Id
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String email;
    private String firstName;
    private String lastName;
    @ManyToMany
    private List<Gruppe> gruppeList = new ArrayList<Gruppe>();


    public static Model.Finder<String, Bruker> find = new Model.Finder<String, Bruker> (
            String.class, Bruker.class
    );

    public Bruker() {
    }

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

    public void create(Map map){
        this.username = map.get("username").toString();
        this.password = map.get("password").toString();
        this.email = map.get("email").toString();
        this.firstName = map.get("firstname").toString();
        this.lastName = map.get("lastname").toString();
    }

    public static boolean signedIn(){
        if(session("User") == null){
            return false;
        }
        if(Bruker.find.byId(session("User")) == null) {
            return false;
        }
        return true;
    }

    public static Result signedIn(Result result){
        if(session("User") == null) {
            return redirect(controllers.routes.LogIn.index().absoluteURL(request()));
        }
        if(Bruker.find.byId(session("User")) == null) {
            return redirect(controllers.routes.LogIn.index().absoluteURL(request()));
        }
        return result;
    }

    public List<Gruppe> getGruppeList() {
        return gruppeList;
    }

    public void setGruppeList(List<Gruppe> gruppeList) {
        this.gruppeList = gruppeList;
    }
}
