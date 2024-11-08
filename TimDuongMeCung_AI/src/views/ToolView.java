package views;

import javax.swing.*;
import java.awt.*;

public class ToolView extends JPanel {
    private JButton[] buttons;

    public ToolView() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        String[] buttonNames = { "CREATE MATRIX", "PSO", "RUN" };
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
}
