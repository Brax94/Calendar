package controllers;

import com.avaje.ebean.Ebean;
import models.Bruker;
import models.Dbtest;
import play.*;
import play.data.Form;
import play.mvc.*;

import calendar.EventCalendar;

import views.html.*;

import java.util.ArrayList;
import java.util.List;

import static play.data.Form.form;

public class Application extends Controller {

    public static List<models.Event> eventList = models.Event.find.all();
    public static EventCalendar calendar = new EventCalendar(eventList, Bruker.find.byId(session().get("User")));

    public static Result index() {
        return ok(layoutHtml.render("Kalender", views.html.Frontpage.frontCalendar.render(getCal())));
    }

    final static Form<Dbtest> test = form(Dbtest.class);

    public static Result save(){
       Form<Dbtest> input = test.bindFromRequest();
       Dbtest dbtest = input.get();
        System.out.println(dbtest.getText());
        dbtest.save();
        return index();
    }

    public static Result next() {
        calendar.next();
        return ok(layoutHtml.render("Kalender", views.html.Frontpage.frontCalendar.render(calendar)));
    }

    public static Result prev() {
        calendar.prev();
        return ok(layoutHtml.render("Kalender", views.html.Frontpage.frontCalendar.render(calendar)));
    }

    public static List<String> get(){
        List<String> list = new ArrayList<String>();
        List<Dbtest> dbtest = Ebean.find(Dbtest.class).findList();
        for( Dbtest i : dbtest){
            list.add(i.getText());
        }
        return list;
    }

    public static EventCalendar getCal() {
        return calendar;
    }

}
