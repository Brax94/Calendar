package controllers;

import models.Bruker;
import play.data.Form;
import play.mvc.Result;
import views.html.layoutHtml;

import static play.data.Form.form;
import static play.mvc.Http.Context.Implicit.request;
import static play.mvc.Results.ok;
import static play.mvc.Results.redirect;

/**
 * Created by eliasbragstadhagen on 21.02.15.
 */
public class LogIn {

    public static Result index(){
        return ok(layoutHtml.render("SignIn", views.html.Login.login.render()));
    }

    public static Result signUp(){
        return ok(layoutHtml.render("SignUp", views.html.Login.signUp.render()));
    }

    public static Result registerUser() {
        Form<Bruker> userForm = form(Bruker.class).bindFromRequest();
        Bruker userModel = userForm.get();
        userModel.save();
        return redirect(routes.Application.index().absoluteURL(request()));
    }


}
