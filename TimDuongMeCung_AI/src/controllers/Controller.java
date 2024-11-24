package controllers;

import javax.swing.JButton;

import models.ALFactory;
import models.IAlogrithm;
import models.IModel;
import models.Model;
import models.Point;
import views.IView;
import views.View;

public class Controller implements IController {
	IView view;
	IModel model;

	public Controller(IView view, IModel model) {
		this.view = view;
		this.model = model;

	}

	/**
	 * thực hiện tạo mê cung
	 */
	private void createMatrix() {
		view.createMatrix(model.getMatrix());
	}

	/**
	 * kiểm tra điều kiện hiển thị kết quả
	 */
	private void displayResult() {
		Point[] points = model.getPath();
		if (points == null)
			view.displayResult(points, false);
		else
			view.displayResult(points, true);
	}

	@Override
	public void setEvent() {
		eventCreateMatrix();
		eventChangeAL();
		eventFinding();
	}

	private void eventFinding() {
		View v = (View) view;
		v.addEventFinding(e -> {
			displayResult();
		});
	}

	private void eventChangeAL() {
		View v = (View) view;
		v.addEventChangeAL(e -> {
			String s = ((JButton) e.getSource()).getText();
			IAlogrithm al = new ALFactory().createProduct(s);
			((Model) model).setAL(al);
		});
	}

	private void eventCreateMatrix() {
		View v = (View) view;
		v.addEventCreateMatrix(e -> {
			createMatrix();
		});
	}

	@Override
	public void run() {
		view.createGUI();
		setEvent();
	}
}
