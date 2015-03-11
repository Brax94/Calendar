package controllers;

import controllers.*;
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
        return Bruker.signedIn(ok(views.html.layoutHtml.render("Event", views.html.Event.event.render(getEvent(eventID), getAffiliation(eventID), getAffiliated(eventID), getStatusNumbers(eventID), getGroups()))));
    }

    public static List<Gruppe> getGroups() {
        return models.Gruppe.find.all();
    }

    public static Result newEvent() {
        List<Room> rooms = Room.find.all();
        return Bruker.signedIn(ok(views.html.layoutHtml.render("New Event", views.html.Event.newEvent.render(rooms))));
    }

    public static Result saveNewEvent() {
        Form<models.Event> eventForm = form(models.Event.class).bindFromRequest();
        models.Event eventModel = eventForm.get();
        eventModel.setEventStarts(new HttpRequestData().get("eStarts"));
        System.out.println(new HttpRequestData().get("eStarts"));
        eventModel.setEventEnds(new HttpRequestData().get("eEnds"));
        eventModel.setCreator(Bruker.find.byId(session().get("User")));
        try {
            Room room = Room.find.byId(Long.parseLong(new HttpRequestData().get("roomId")));
            eventModel.setRoom(room);
        } catch (Exception e) {

        }
        eventModel.save();
        Affiliated affiliated = new Affiliated(Bruker.find.byId(session("User")), eventModel);
        affiliated.setStatus(Affiliated.Status.ATTENDING);
        affiliated.save();
        return Bruker.signedIn(redirect(routes.Event.renderEvent("" + eventModel.getEventId()).absoluteURL(request())));
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
        if ((start.getTimeInMillis() - event.getEventStarts().getTimeInMillis()) < 0 && (end.getTimeInMillis() - event.getEventEnds().getTimeInMillis()) > 0) {
            return true;
        } else if ((start.getTimeInMillis() - event.getEventStarts().getTimeInMillis()) < 0 && (end.getTimeInMillis() - event.getEventStarts().getTimeInMillis() > 0)) {
            return true;
        } else if ((start.getTimeInMillis() - event.getEventStarts().getTimeInMillis()) >= 0 && (end.getTimeInMillis() - event.getEventStarts().getTimeInMillis() <= 0)) {
            return true;
        } else if ((start.getTimeInMillis() - event.getEventStarts().getTimeInMillis()) >= 0 && (end.getTimeInMillis() - event.getEventStarts().getTimeInMillis() >= 0)) {
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
        if (Bruker.find.byId(session("User")) == eventModel.getCreator()) {
            eventModel.setEventStarts(new HttpRequestData().get("eStarts"));
            System.out.println(new HttpRequestData().get("eStarts"));
            eventModel.setEventEnds(new HttpRequestData().get("eEnds"));
            try {
                Room room = Room.find.byId(Long.parseLong(new HttpRequestData().get("roomId")));
                eventModel.setRoom(room);
            } catch (Exception e) {

            }

            eventModel.setEventId(Long.parseLong(eventID));
            eventModel.update();
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

}
