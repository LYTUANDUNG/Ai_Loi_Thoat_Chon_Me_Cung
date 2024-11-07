package controllers;

import models.IModel;
import models.Point;
import views.IView;

public class Controller implements IController {
	IView view;
	IModel model;

	public Controller(IView view, IModel model) {
		this.view = view;
		this.model = model;

	}
	/**
	 * event thực hiện các button 
	 * 
	 */
	
	/**
	 * thực hiện tạo mê cung
	 */
	private void createMatrix() {
		Point[] points = model.getMatrix();
		view.createMatrix(points);
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
}
