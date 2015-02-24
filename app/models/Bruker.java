package models;

import play.db.ebean.Model;

import javax.persistence.*;

/**
 * Created by carlandreasjulsvoll on 24.02.15.
 */
@Entity
public class Bruker extends Model {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(nullable = false)

    private long userId;
    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String passord;
    private String Fornavn;
    private String Etternavn;



    public long getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String brukernavn) {
        this.username = brukernavn;
    }

    public String getPassord() {
        return passord;
    }

    public void setPassord(String passord) {
        this.passord = passord;
    }

    public String getFornavn() {
        return Fornavn;
    }

    public void setFornavn(String fornavn) {
        Fornavn = fornavn;
    }

    public String getEtternavn() {
        return Etternavn;
    }

    public void setEtternavn(String etternavn) {
        Etternavn = etternavn;
    }
}
