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
    private String brukernavn;
    private String passord;
    private String Fornavn;
    private String Etternavn;



    public long getUserId() {
        return userId;
    }

    public String getBrukernavn() {
        return brukernavn;
    }

    public void setBrukernavn(String brukernavn) {
        this.brukernavn = brukernavn;
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
