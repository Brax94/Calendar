package models;

import play.db.ebean.Model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by carlandreasjulsvoll on 11.03.15.
 */

@Entity
public class Gruppe extends Model{

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long groupID;
    @Column(nullable = false)
    private String groupName;
    @Column(nullable = false)
    @OneToOne
    private Bruker creator;
    @ManyToMany(mappedBy = "gruppeList")
    private List<Bruker> brukerList = new ArrayList<Bruker>();
    @ManyToOne(cascade = CascadeType.ALL)
    private Gruppe motherGruppe = null;
    @OneToMany
    private List<Gruppe> gruppeList = new ArrayList<Gruppe>();

    public List<Gruppe> getGruppeList() {
        return gruppeList;
    }

    public static Model.Finder<Long, Gruppe> find = new Model.Finder<Long, Gruppe> (
            Long.class, Gruppe.class
    );

    public void addBruker(Bruker bruker){
        brukerList.add(bruker);
    }

    public void removeBruker(Bruker bruker){
        brukerList.remove(bruker);
    }


    public Long getGroupID() {
        return groupID;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Bruker getCreator() {
        return creator;
    }

    public void setCreator(Bruker creator) {
        this.creator = creator;
    }

    public Gruppe getMotherGroup(){
        return this.motherGruppe;
    }
    public void setMotherGroup(Gruppe g){
        this.motherGruppe=g;
    }

    public void addMember(Bruker bruker){
        brukerList.add(bruker);
    }

    public List<Bruker> getMembers(){
        return this.brukerList;
    }
}
