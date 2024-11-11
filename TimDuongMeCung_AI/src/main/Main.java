package main;

import controllers.Controller;
import controllers.IController;
import views.IView;
import views.View;
import models.IModel;
import models.Model;
public class Main {
	public static void main(String[] args) {
		IView view = new View();
		IModel model = new Model();
		IController c = new Controller(view, model);
		c.run();
	}
}
