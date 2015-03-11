package controllers;

import models.Bruker;
import models.Gruppe;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

import static play.data.Form.form;

/**
 * Created by eliasbragstadhagen on 11.03.15.
 */
public class GruppeController extends Controller{

    public static Result mineGrupper(){
        return Bruker.signedIn(ok(views.html.layoutHtml.render("Groups", views.html.Gruppe.mineGrupper.render(Bruker.find.byId(session("User"))))));
    }

    public static Result newGruppe(){
        return Bruker.signedIn(ok(layoutHtml.render("new Gruppe", views.html.Gruppe.newGruppe.render(null))));
    }

    public static Result createGruppe(){
        Form<Gruppe> gruppeForm = form(models.Gruppe.class).bindFromRequest();
        Gruppe gruppeModel = gruppeForm.get();
        if(Gruppe.find.where().eq("groupName", gruppeModel.getGroupName()).findUnique() != null){
            System.out.println("sjekk" + Gruppe.find.where().eq("groupName", gruppeModel.getGroupName()) + "name");
            System.out.println("halla");
            return Bruker.signedIn(ok(layoutHtml.render("new Gruppe", views.html.Gruppe.newGruppe.render("Group Name Already Exists"))));
        }
        gruppeModel.setCreator(Bruker.find.byId(session("User")));
        gruppeModel.addBruker(gruppeModel.getCreator());
        gruppeModel.save();
        return Bruker.signedIn(ok(layoutHtml.render("Gruppe", views.html.Gruppe.gruppe.render())));
    }

}
