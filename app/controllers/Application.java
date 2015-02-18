package controllers;

import com.avaje.ebean.Ebean;
import models.Dbtest;
import play.*;
import play.data.Form;
import play.mvc.*;

import views.html.*;

import java.util.ArrayList;
import java.util.List;

import static play.data.Form.form;

public class Application extends Controller {

    public static Result index() {
        return ok(layoutHtml.render("Kalender", layout.render(get())));
    }

    final static Form<Dbtest> test = form(Dbtest.class);

    public static Result save(){
       Form<Dbtest> input = test.bindFromRequest();
       Dbtest dbtest = input.get();
        System.out.println(dbtest.getText());
        dbtest.save();
        return index();
    }

    public static List<String> get(){
        List<String> list = new ArrayList<String>();
        List<Dbtest> dbtest = Ebean.find(Dbtest.class).findList();
        for( Dbtest i : dbtest){
            list.add(i.getText());
        }
        return list;
    }

}
