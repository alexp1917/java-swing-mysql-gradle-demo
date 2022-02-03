package org.example.swing_demo;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Vector;

public class App {
    JList<Object> jList;
    JPanel jPanel;
    JFrame frame;
    JLabel label;

    public App() {
        frame = new JFrame("Hello Swing with MySQL");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jPanel = new JPanel(new FlowLayout());
        label = new JLabel("Today, the table reads:");
        jList = new JList<>(new Vector<>(Arrays.asList("abc", "def")));

        frame.setLocation(new Point(200, 200));
        frame.setSize(new Dimension(640, 480));

        jPanel.add(Box.createVerticalStrut(50));
        jPanel.add(Box.createHorizontalStrut(50));
        jPanel.add(label);
        jPanel.add(Box.createHorizontalStrut(50));
        jPanel.add(jList);
        jPanel.add(Box.createVerticalStrut(50));
        jPanel.add(Box.createHorizontalStrut(50));

        frame.getContentPane().add(jPanel);

        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(App::new);
    }
}
