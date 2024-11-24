package views;

import javax.swing.*;

import models.IAlogrithm;

import java.awt.*;
import java.awt.event.ActionListener;

public class ToolView extends JPanel {
    private JButton[] buttons;

    public ToolView() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        String[] buttonNames = { "CREATE MAZE", "PSO", "RUN" };
        buttons = new JButton[buttonNames.length];

        // Dùng Dimension để set form cho các button
        Dimension buttonSize = new Dimension(150, 30); 

        for (int i = 0; i < buttonNames.length; i++) {
            buttons[i] = new JButton(buttonNames[i]);
            buttons[i].setPreferredSize(buttonSize);
            buttons[i].setMaximumSize(buttonSize); 
            add(buttons[i]);
        }
    }

	public void addEventFinding(ActionListener event) {
		buttons[buttons.length-1].addActionListener(event);
	}

	public void addEventCreateMatrix(ActionListener event) {
		buttons[0].addActionListener(event);
	}

	public void addEventChangeAL(ActionListener event) {
		for (int i = 1; i < buttons.length-1; i++) {
			buttons[i].addActionListener(event);
		}
	}
}
