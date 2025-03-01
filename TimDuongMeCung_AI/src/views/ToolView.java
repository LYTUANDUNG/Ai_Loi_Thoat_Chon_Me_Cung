package views;

import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class ToolView extends JPanel {
    private JButton[] buttons;
    private JTextArea textArea; // Add a reference to JTextArea

    public ToolView() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        String[] buttonNames = { "CREATE MAZE", "PSO", "RUN","CHANGE" };
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

    // Add a setter for the textArea
    public void setTextArea(JTextArea textArea) {
        this.textArea = textArea;
    }

    // Method to retrieve the textArea
    public JTextArea getTextArea() {
        return textArea;
    }

    public void addEventFinding(ActionListener event) {
        buttons[buttons.length-2].addActionListener(event);
    }

    public void addEventCreateMatrix(ActionListener event) {
        buttons[0].addActionListener(event);
    }

    public void addEventChangeAL(ActionListener event) {
            buttons[1].addActionListener(event);
    }
    public void addEventChange(ActionListener event) {
            buttons[buttons.length-1].addActionListener(event);
    }
}