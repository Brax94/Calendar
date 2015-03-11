package controllers;

import ch.qos.logback.core.net.SyslogOutputStream;
import models.Bruker;
import models.Gruppe;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;
import java.util.List;
import models.HttpRequestData;

import static play.data.Form.form;

/**
 * Created by eliasbragstadhagen on 11.03.15.
 */
public class GruppeController extends Controller{


    public static Result renderGruppe(String gruppeID) {
        return Bruker.signedIn(ok(views.html.layoutHtml.render("Gruppe", views.html.Gruppe.gruppe.render(getGruppe(gruppeID),getGroupMembers(gruppeID)))));
    }

    public static models.Gruppe getGruppe(String gruppeID) {
        long gruppeId = Long.valueOf(gruppeID);
        models.Gruppe gruppeModel = models.Gruppe.find.byId(gruppeId);
        return gruppeModel;
    }

    public static Result inviteUser(String groupID){
        if (Bruker.find.byId(new HttpRequestData().get("invUser")) != null) {
            System.out.println("ikke null");
            Bruker bruker = Bruker.find.byId(new HttpRequestData().get("invUser"));
            models.Gruppe gruppe = Gruppe.find.byId(Long.parseLong(groupID));
            gruppe.addMember(bruker);
            gruppe.update();
            bruker.addGroup(gruppe);
            bruker.update();
            return redirect(routes.GruppeController.renderGruppe(groupID).absoluteURL(request()));
        }else{
            System.out.println("ERROR");
            return redirect(routes.GruppeController.renderGruppe(groupID).absoluteURL(request()));
        }
    }

    public static Result mineGrupper(){
        return Bruker.signedIn(ok(views.html.layoutHtml.render("Groups", views.html.Gruppe.mineGrupper.render(Bruker.find.byId(session("User"))))));
    }

    public static Result newGruppe(){
        return Bruker.signedIn(ok(layoutHtml.render("new Gruppe", views.html.Gruppe.newGruppe.render(null))));
    }

    public static List<Bruker> getGroupMembers(String groupId){
        Long groupID = Long.parseLong(groupId);
        Gruppe gruppe = Gruppe.find.byId(groupID);
        List<Bruker> members = gruppe.getMembers();
        return members;

    }

    public static Result createGruppe(){
        Form<Gruppe> gruppeForm = form(models.Gruppe.class).bindFromRequest();
        Gruppe gruppeModel = gruppeForm.get();
        if(Gruppe.find.where().eq("groupName", gruppeModel.getGroupName()).findUnique() != null){
            System.out.println("sjekk" + Gruppe.find.where().eq("groupName", gruppeModel.getGroupName()) + "name");
            return Bruker.signedIn(ok(layoutHtml.render("new Gruppe", views.html.Gruppe.newGruppe.render("Group Name Already Exists"))));
        }
        gruppeModel.setCreator(Bruker.find.byId(session("User")));
        gruppeModel.addBruker(gruppeModel.getCreator());
        gruppeModel.save();
        return Bruker.signedIn(redirect(routes.GruppeController.renderGruppe(""+gruppeModel.getGroupID()).absoluteURL(request())));


    }

}
