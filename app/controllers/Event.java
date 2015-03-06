package controllers;

import models.Affiliated;
import models.Bruker;
import models.HttpRequestData;
import models.Room;
import play.mvc.Controller;
import play.mvc.Result;
import play.data.Form;

import java.util.List;

import static models.HttpRequestData.*;
import static play.data.Form.form;

import static play.mvc.Results.ok;

/**
 * Created by Elias Bragstad Hagen on 23.02.2015.
 */
public class Event extends Controller{

    public static Result renderEvent(String eventID){
        return Bruker.signedIn(ok(views.html.layoutHtml.render("Event", views.html.Event.event.render(getEvent(eventID), getAffiliation(eventID), getAffiliated(eventID)))));
    }

    public static Result newEvent(){
        List<Room> rooms = Room.find.all();
        return Bruker.signedIn(ok(views.html.layoutHtml.render("New Event", views.html.Event.newEvent.render(rooms))));
    }
    public static Result saveNewEvent(){
        Form<models.Event> eventForm = form(models.Event.class).bindFromRequest();
        models.Event eventModel = eventForm.get();
        eventModel.setEventStarts(new HttpRequestData().get("eStarts"));
        eventModel.setEventEnds(new HttpRequestData().get("eEnds"));
        eventModel.setCreator(Bruker.find.byId(session().get("User")));
        eventModel.save();
        Affiliated affiliated = new Affiliated(Bruker.find.byId(session("User")), eventModel);
        affiliated.setStatus(Affiliated.Status.ATTENDING);
        affiliated.save();
        return Bruker.signedIn(redirect(routes.Event.renderEvent("" + eventModel.getEventId()).absoluteURL(request())));
    }

    public static models.Event getEvent(String eventID){
        long eventId = Long.valueOf(eventID);
        models.Event eventModel = models.Event.find.byId(eventId);
        return eventModel;
    }
    public static Result getEvents(){
        List<models.Event> eventList = models.Event.find.where().eq("creator", Bruker.find.byId(session("User"))).orderBy("eventStarts").findList();
        List<Affiliated> affiliatedList = Affiliated.find.where().eq("bruker", Bruker.find.byId(session("User"))).findList();
        for(Affiliated i : affiliatedList){
            eventList.add(i.getEvent());
        }
        return Bruker.signedIn(ok(views.html.layoutHtml.render("MyEvents", views.html.Event.myEvents.render(eventList))));
    }
    public static Result inviteUser(String eventID){
        Bruker bruker = Bruker.find.byId(new HttpRequestData().get("invUser"));
        models.Event event = models.Event.find.byId(Long.parseLong(eventID));
        Affiliated affiliated = new Affiliated(bruker, event);
        affiliated.save();
        return redirect(routes.Event.renderEvent(eventID).absoluteURL(request()));
    }

    public static Affiliated getAffiliation(String eventID){
        return Affiliated.find.where().eq("bruker", Bruker.find.byId(session("User"))).where().eq("event", models.Event.find.byId(Long.parseLong(eventID))).findUnique();
    }

    public static List<Affiliated> getAffiliated(String eventID){
        List<Affiliated> affiliatedList = Affiliated.find.where().eq("event", models.Event.find.byId(Long.parseLong(eventID))).findList();
        return affiliatedList;
    }
}
