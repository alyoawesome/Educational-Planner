package ui;

import javax.swing.*;

//CITATION: Used stackoverflow to help me understand how to show an image with delay,
//          url: https://stackoverflow.com/questions/20098124/displaying-an-image-in-a-jframe

//Represents the load screen that shows an image before going to the GUI for the Educational Planner Application
public class LoadScreen extends JFrame {

    private JFrame frame = new JFrame();
    private ImageIcon icon = new ImageIcon("./data/tobs.jpg");
    private JLabel label = new JLabel(icon);

    //MODIFIES: this
    //EFFECTS: displays the loading screen for the Educational Planner for 1 second and then goes to the
    // GUI for the Educational Planner Application
    public LoadScreen() {
        frame.add(label);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        frame.dispose();
        frame.repaint();
    }
}
