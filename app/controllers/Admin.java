package controllers;

import models.Room;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

import views.html.*;

import java.util.List;

import static play.data.Form.form;

/**
 * Created by valdemarrolfsen on 05.03.15.
 */
public class Admin extends Controller {

    public static Result index() {
        List<Room> rooms = Room.find.all();
        return ok(layoutHtml.render("Admin", views.html.Admin.admin.render(rooms)));
    }

    public static Result add() {
        Form<Room> roomForm = form(Room.class).bindFromRequest();
        if(roomForm.hasErrors()){
            System.out.println("has error");
        }
        Room room = new Room();
        room.create(roomForm.data());
        room.save();
        return index();
    }
}
