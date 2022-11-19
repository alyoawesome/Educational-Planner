package ui;

import javax.swing.*;
import java.io.FileNotFoundException;

//CITATION: Reused code from JsonSerializationDemo's Main class for the code in this class
//          Also used stackoverflow to help me understand how to show an image with delay,
////          url: https://stackoverflow.com/questions/20098124/displaying-an-image-in-a-jframe

public class Main {
    public static void main(String[] args) throws FileNotFoundException {

        new LoadScreen();

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new GUI();
            }
        });
    }
}


