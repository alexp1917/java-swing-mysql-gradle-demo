package org.example.swing_demo;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Vector;

public class App {
    JList<Object> jList;
    JPanel jPanel;
    JFrame frame;
    JLabel label;
    DataLayer dataLayer;

    public App() {
        frame = new JFrame("Hello Swing with MySQL");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jPanel = new JPanel(new FlowLayout());
        label = new JLabel("Today, the table reads:");
        dataLayer = new DataLayer(false);
        try {
            jList = new JList<>(new Vector<>(dataLayer.selectAllDataValues()));
        } catch (SQLException e) {
            jList = new JList<>(new Vector<>(Arrays.asList("couldn't connect", e.getErrorCode(), e.getMessage())));
        }

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
