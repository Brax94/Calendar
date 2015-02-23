package controllers;

import play.mvc.Result;
import views.html.layoutHtml;

import static play.mvc.Results.ok;

/**
 * Created by eliasbragstadhagen on 21.02.15.
 */
public class LogIn {

    public static Result index(){
        return ok(layoutHtml.render("SignIn", views.html.Login.login.render()));
    }
}
