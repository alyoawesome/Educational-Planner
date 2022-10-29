package ui;

import java.io.FileNotFoundException;

//CITATION: Reused code from JsonSerializationDemo's Main class for the code in this class
public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        try {
            new EducationalPlannerApp();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run application: file not found");
        }
    }
}
