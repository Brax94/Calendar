package models;

import play.db.ebean.Model;

import javax.persistence.*;
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
    private String creator;
    @ManyToMany
    private List<Bruker> brukerList;
    @OneToOne
    private Gruppe motherGruppe;


    public Long getGroupID() {
        return groupID;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }
}
