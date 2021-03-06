package controllers;

import models.Bruker;
import models.HttpRequestData;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.layoutHtml;

import java.util.HashMap;
import java.util.Map;

import static play.data.Form.form;

/**
 * Created by eliasbragstadhagen on 21.02.15.
 */
public class LogIn extends Controller {

    public static Result index(){
        return ok(layoutHtml.render("SignIn", views.html.Login.login.render(null)));
    }

    public static Result indexError(){
        return ok(layoutHtml.render("SignIn", views.html.Login.login.render("Wrong username/password")));
    }

    public static Result signUpError(){
        return ok(layoutHtml.render("SignUp", views.html.Login.signUp.render("Username Already Exists")));
    }
    public static Result signUp(){
        return ok(layoutHtml.render("SignUp", views.html.Login.signUp.render(null)));
    }

    public static Result logIn(){
        HashMap map = new HttpRequestData();
        if(Bruker.find.byId(map.get("user").toString()) != null) {
            Bruker bruker = Bruker.find.byId(map.get("user").toString());

            if(!bruker.getPassword().equals(map.get("password"))) {
                return redirect(routes.LogIn.indexError().absoluteURL(request()));
            }
            else{
                session().clear();
                session("User", bruker.getUsername());
            }
        }
        else{
            return redirect(routes.LogIn.indexError().absoluteURL(request()));
        }
        return redirect(routes.Application.index().absoluteURL(request()));
    }

    public static Result registerUser(){
        Form<Bruker> brukerForm = form(Bruker.class).bindFromRequest();
        if(brukerForm.hasErrors()){
            System.out.println("has error");
        }
        if(Bruker.find.byId(brukerForm.data().get("username")) != null){
            return redirect(routes.LogIn.signUpError().absoluteURL(request()));
        }
        else{
        Bruker bruker = new Bruker();
        bruker.create(brukerForm.data());
        bruker.save();
        return redirect(routes.Application.index().absoluteURL(request()));}
    }

    public static Result logOut(){
        session().clear();
        return redirect(routes.LogIn.index().absoluteURL(request()));
    }


}
