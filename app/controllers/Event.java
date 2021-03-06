package controllers;

import controllers.*;
import controllers.routes;
import models.*;
import play.mvc.Controller;
import play.mvc.Result;
import play.data.Form;
import views.html.layoutHtml;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


import static models.HttpRequestData.*;
import static play.data.Form.form;

import static play.mvc.Results.ok;

/**
 * Created by Elias Bragstad Hagen on 23.02.2015.
 */
public class Event extends Controller {

    public static Result renderEvent(String eventID) {
        models.Event event = models.Event.find.byId(Long.parseLong(eventID));
        List<Room> rooms = availableRooms(event.getEventStarts(), event.getEventEnds());

        return Bruker.signedIn(ok(views.html.layoutHtml.render("Event", views.html.Event.event.render(getEvent(eventID), getAffiliation(eventID), getAffiliated(eventID), getStatusNumbers(eventID), getGroups(), rooms))));
    }

    public static List<Gruppe> getGroups() {
        return models.Gruppe.find.all();
    }

    public static Result newEvent() {
        return Bruker.signedIn(ok(views.html.layoutHtml.render("New Event", views.html.Event.newEvent.render(null))));
    }
    public static Result newEventError(){
       return Bruker.signedIn(ok(views.html.layoutHtml.render("New Event", views.html.Event.newEvent.render("Error in input"))));
    }

    public static Result saveNewEvent() {
        try {
            Form<models.Event> eventForm = form(models.Event.class).bindFromRequest();
            models.Event eventModel = eventForm.get();
            if(eventModel.getTitle() == ""){
                throw new Exception();
            }
            try {
                eventModel.setEventStarts(new HttpRequestData().get("eStarts"));
                System.out.println(new HttpRequestData().get("eStarts"));
                eventModel.setEventEnds(new HttpRequestData().get("eEnds"));
                if(eventModel.getEventEnds().before(eventModel.getEventStarts())){
                    return redirect(routes.Event.newEventError().absoluteURL(request()));
                }
            }
            catch(ArrayIndexOutOfBoundsException e){
                return redirect(routes.Event.newEventError().absoluteURL(request()));
            }
            eventModel.setCreator(Bruker.find.byId(session().get("User")));
            eventModel.save();
            Affiliated affiliated = new Affiliated(Bruker.find.byId(session("User")), eventModel);
            affiliated.setStatus(Affiliated.Status.ATTENDING);
            affiliated.save();
            return Bruker.signedIn(redirect(routes.Event.renderEvent("" + eventModel.getEventId()).absoluteURL(request())));
        }
        catch(Exception e){
            return redirect(routes.Event.newEventError().absoluteURL(request()));
        }
    }

    public static models.Event getEvent(String eventID) {
        long eventId = Long.valueOf(eventID);
        models.Event eventModel = models.Event.find.byId(eventId);
        return eventModel;
    }

    public static Result getEvents() {
        List<models.Event> eventList = models.Event.find.where().eq("creator", Bruker.find.byId(session("User"))).orderBy("eventStarts").findList();
        List<Affiliated> affiliatedList = Affiliated.find.where().eq("bruker", Bruker.find.byId(session("User"))).findList();
        for (Affiliated i : affiliatedList) {
            if (!eventList.contains(i.getEvent()))
                eventList.add(i.getEvent());
        }
        return Bruker.signedIn(ok(views.html.layoutHtml.render("MyEvents", views.html.Event.myEvents.render(eventList))));
    }

    public static Result inviteUser(String eventID) {
        if (Bruker.find.byId(new HttpRequestData().get("invUser")) != null) {
            Bruker bruker = Bruker.find.byId(new HttpRequestData().get("invUser"));
            if (Affiliated.find.where().eq("bruker", bruker).where().eq("event", models.Event.find.byId(Long.parseLong(eventID))).findUnique() == null) {
                models.Event event = models.Event.find.byId(Long.parseLong(eventID));
                Affiliated affiliated = new Affiliated(bruker, event);
                affiliated.save();
                new Notification(bruker, "Invited to new <a href=\"/event/"+event.getEventId()+"\"> EVENT </a>" ).save();
            }
            return redirect(routes.Event.renderEvent(eventID).absoluteURL(request()));
        } else {
            System.out.println("ERROR");
            return redirect(routes.Event.renderEvent(eventID).absoluteURL(request()));
        }
    }

    public static void inviteUser(Bruker bruker, models.Event event) {
        if (bruker != null && event != null) {
            if (Affiliated.find.where().eq("bruker", Bruker.find.byId(bruker.getUsername())).where().eq("event", models.Event.find.byId(event.getEventId())).findUnique() == null) {
                Affiliated affiliated = new Affiliated(bruker, event);
                affiliated.save();
                new Notification(bruker, "Invited to new <a href=\"/event/"+event.getEventId()+"\"> EVENT </a>" ).save();
            } else {
                System.out.println("ERROR");
            }
        } else {
            System.out.println("ERROR");
        }
    }

    public static Result inviteGroup(String eventID) {
        Long id = Long.parseLong(eventID);
        models.Event event = models.Event.find.byId(id);
        if (Gruppe.find.byId(Long.parseLong(new HttpRequestData().get("groupID"))) != null) {
            Gruppe gruppe = Gruppe.find.byId(Long.parseLong(new HttpRequestData().get("groupID")));
            for (Bruker bruker : gruppe.getMembers()) {
                inviteUser(bruker, event);
            }

            return redirect(routes.Event.renderEvent(eventID).absoluteURL(request()));
        }
        return redirect(routes.Event.renderEvent(eventID).absoluteURL(request()));
    }

    public static Affiliated getAffiliation(String eventID) {
        return Affiliated.find.where().eq("bruker", Bruker.find.byId(session("User"))).where().eq("event", models.Event.find.byId(Long.parseLong(eventID))).findUnique();
    }

    public static List<Affiliated> getAffiliated(String eventID) {
        List<Affiliated> affiliatedList = Affiliated.find.where().eq("event", models.Event.find.byId(Long.parseLong(eventID))).findList();
        return affiliatedList;
    }

    public enum Status {
        ATTENDING, MAYBE, NOT_ATTENDING, UNDECIDED
    }

    public static List<Integer> getStatusNumbers(String eventID) {
        List<Affiliated> liste = getAffiliated(eventID);
        List<Integer> retur = new ArrayList<Integer>();
        int numberOfAttending = 0;
        int numberOfMaybe = 0;
        int numberOfNotAttendig = 0;
        int numberOfUndecided = 0;
        for (Affiliated aff : liste) {
            if (aff.getStatus().toString().equals(Status.ATTENDING.toString())) {
                numberOfAttending++;
            } else if (aff.getStatus().toString().equals(Status.MAYBE.toString())) {
                numberOfMaybe++;
            } else if (aff.getStatus().toString().equals(Status.NOT_ATTENDING.toString())) {
                numberOfNotAttendig++;
            } else {
                numberOfUndecided++;
            }
        }
        retur.add(numberOfAttending);
        retur.add(numberOfMaybe);
        retur.add(numberOfNotAttendig);
        retur.add(numberOfUndecided);
        return retur;
    }

    public static Result updateStatus(String id) {
        Affiliated affiliated = Affiliated.find.byId(Long.parseLong(id));
        affiliated.setStatus(Affiliated.Status.valueOf(new HttpRequestData().get("status")));
        System.out.println(affiliated.getStatus().toString());
        affiliated.update();
        new Notification(affiliated.getEvent().getCreator(), ""+affiliated.getBruker().getUsername()+" in "+ affiliated.getEvent().getTitle()+" changed status to "+ new HttpRequestData().get("status")).save();
        return redirect(routes.Event.renderEvent(String.valueOf(affiliated.getEvent().getEventId())).absoluteURL(request()));
    }

    public static Result remove(String affID) {
        Affiliated.find.ref(Long.parseLong(affID)).delete();
        return getEvents();
    }

    public static List<Room> availableRooms(Calendar eventStarts, Calendar eventEnds) {
        List<models.Event> eventList = models.Event.find.all();
        List<Room> availableRooms = Room.find.all();
        ;
        for (models.Event event : eventList) {
            if (isColliding(eventStarts, eventEnds, event)) {
                availableRooms.remove(event.getRoom());
            }
        }
        return availableRooms;
    }

    public static boolean isColliding(Calendar start, Calendar end, models.Event event) {
        long startNew = start.getTimeInMillis();
        long endNew = end.getTimeInMillis();
        long startEx = event.getEventStarts().getTimeInMillis();
        long endEx = event.getEventEnds().getTimeInMillis();
        if((startNew-startEx)>=0 && (startNew-endEx)<0){
            return true;
        }else if((endNew-startEx)>0 && (endNew-endEx)<0){
            return true;
        }else if((startNew-startEx)<0 &&(endNew-endEx)>0){
            return true;
        }
        return false;
    }

    public static Result editEvent(String eventId) {
        models.Event event = models.Event.find.byId(Long.parseLong(eventId));
        List<Room> rooms = Room.find.all();
        return Bruker.signedIn(ok(layoutHtml.render("Edit Event", views.html.Event.editEvent.render(rooms, event))));
    }

    public static Result editThisEvent(String eventID) {
        Form<models.Event> eventForm = form(models.Event.class).bindFromRequest();
        models.Event eventModel = eventForm.get();
        System.out.println(Bruker.find.byId(session("User")) == models.Event.find.byId(Long.parseLong(eventID)).getCreator());
        if (Bruker.find.byId(session("User")).equals(models.Event.find.byId(Long.parseLong(eventID)).getCreator())) {
            System.out.println(Bruker.find.byId(session("User")));
            eventModel.setEventStarts(new HttpRequestData().get("eStarts"));
            System.out.println(new HttpRequestData().get("eStarts"));
            eventModel.setEventEnds(new HttpRequestData().get("eEnds"));
            try {
                System.out.println("rom er satt til null");
                models.Event event = models.Event.find.byId(Long.parseLong(eventID));
                event.setRoom(null);
                event.save();
            } catch (Exception e) {
                System.out.println("rom er ikke satt til null");
            }

            eventModel.setEventId(Long.parseLong(eventID));
            eventModel.update();
        }
        List<Affiliated> affiliateds = Affiliated.find.where().eq("event", Event.getEvent(eventID)).findList();
        String creator = session("User");
        for(Affiliated aff : affiliateds){
            Bruker bruker = aff.getBruker();
            if(bruker.getUsername()!=session("User")){
                new Notification(bruker, "<a href=\"/event/"+aff.getEvent().getEventId()+"\"> EVENT </a>"+" has changed").save();
            }

        }



        return Bruker.signedIn(redirect(routes.Event.renderEvent(eventID).absoluteURL(request())));
    }

    public static Result deleteEvent(String eventID) {

        models.Event event = models.Event.find.byId(Long.parseLong(eventID));
        if (Bruker.find.byId(session("User")) == event.getCreator()) {
            event.delete();
            System.out.println("WORKS");
        }
        return redirect(routes.Application.index().absoluteURL(request()));
    }

    public static Result setRoom(String eventID) {
        models.Event event = models.Event.find.byId(Long.parseLong(eventID));
        try {
            Room room = Room.find.byId(Long.parseLong(new HttpRequestData().get("roomId")));
            event.setRoom(room);
            event.save();
        } catch (Exception e) {
            event.setRoom(null);
            event.save();
        }
        return redirect(routes.Event.renderEvent(eventID).absoluteURL(request()));

    }

}
