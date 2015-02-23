package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import play.data.Form;

import java.util.List;

import static play.data.Form.form;

import static play.mvc.Results.ok;

/**
 * Created by Elias Bragstad Hagen on 23.02.2015.
 */
public class Event extends Controller{

    public static Result renderEvent(String eventID){
        return ok(views.html.layoutHtml.render("Event", views.html.Event.event.render(getEvent(eventID))));
    }

    public static Result newEvent(){
        return ok(views.html.layoutHtml.render("New Event", views.html.Event.newEvent.render()));
    }
    public static Result saveNewEvent(){
        Form<models.Event> eventForm = form(models.Event.class).bindFromRequest();
        models.Event eventModel = eventForm.get();
        eventModel.save();
        return redirect(routes.Event.renderEvent("" + eventModel.getEventId()).absoluteURL(request()));
    }

    public static models.Event getEvent(String eventID){
        long eventId = Long.valueOf(eventID);
        models.Event eventModel = models.Event.find.byId(eventId);
        return eventModel;
    }
    public static Result getEvents(){
        List<models.Event> eventList = models.Event.find.all();
        return ok(views.html.layoutHtml.render("MyEvents", views.html.Event.myEvents.render(eventList)));
    }
}
