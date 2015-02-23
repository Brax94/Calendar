package controllers;

import play.mvc.Result;

import static play.mvc.Results.ok;

/**
 * Created by Elias Bragstad Hagen on 23.02.2015.
 */
public class Event {

    public static Result newEvent(){
        return ok(views.html.layoutHtml.render("New Event", views.html.Event.newEvent.render()));
    }
}
