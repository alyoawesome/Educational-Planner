package ui;

import model.Assignment;
import model.Course;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

//CITATIONS: Learned to use Scanner classes and manipulate user input from the TellerApp.
//          Borrowed very similar code from TellerApp for the methods runEducationalPlanner,
//          displayMenu, and processCommand.
//          Learned to `manipulate and implement save and load functionality from the JsonSerializationDemo and
//          reused code from JsonSerializationDemo's WorkRoomApp Class for the methods in this class


//Educational Planner application
public class EducationalPlannerApp {
    private static final String JSON_STORE = "./data/courses.json";
    private Scanner input;
    private ArrayList<Course> courses = new ArrayList<>();
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: constructs course and runs the Educational Planner application
    public EducationalPlannerApp() throws FileNotFoundException {
        input = new Scanner(System.in);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runEducationalPlanner();
    }


    // MODIFIES: this
    // EFFECTS: processes user input
    private void runEducationalPlanner() {
        boolean keepGoing = true;
        String command = null;

        input = new Scanner(System.in);

        while (keepGoing) {
            displayMenu();
            command = input.next();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

    }


    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\tc -> add course");
        System.out.println("\ta -> add assignment");
        System.out.println("\tdel -> delete assignment");
        System.out.println("\tavg -> calculate average");
        System.out.println("\ts -> Save all data");
        System.out.println("\tl -> Load all data");
        System.out.println("\tq -> quit");
    }


    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("c")) {
            addCourse();
        } else if (command.equals("a")) {
            addAssignment();
        } else if (command.equals("avg")) {
            calculateOverallAverage();
        } else if (command.equals("del")) {
            deleteAssignment();
        } else if (command.equals("s")) {
            saveCourse();
        } else if (command.equals("l")) {
            loadCourse();
        } else {
            System.out.println("Incorrect keyword, try again!");
        }
    }

    // REQUIRES: the course's name needs to be a String (no numbers) and has length > 0;
    // MODIFIES: this
    // EFFECTS: makes a new course
    private Course makeCourse(String name) {
        ArrayList<Assignment> assignments = new ArrayList<>();
        Course course = new Course(name, assignments);
        return course;
    }


    // MODIFIES: this
    // EFFECTS: makes a course with the inputted name
    private void addCourse() {
        System.out.println("Enter the name of this course");
        String name = input.next();
        courses.add(makeCourse(name));
    }

    // MODIFIES: this
    // EFFECTS: adds an assignment to a course of user's choosing
    private void addAssignment() {
        System.out.println("What number course is this assignment in?");
        int courseNum = Integer.parseInt(input.next());
        System.out.println("Enter the name of this assignment");
        String name = input.next();
        System.out.println("Enter grade of this assignment");
        double grade = Double.parseDouble(input.next());
        Assignment assignment = new Assignment(name, grade);
        courses.get(courseNum).addAssignment(assignment);
    }

    // MODIFIES: this
    // EFFECTS: deletes an assignment in a course of user's choosing
    private void deleteAssignment() {
        System.out.println("What number course is this assignment in?");
        int courseNum = Integer.parseInt(input.next());
        Course selectedCourse = courses.get(courseNum);
        System.out.println("Enter the name of the assignment you want to delete");
        String name = input.next();
        selectedCourse.deleteAssignment(name);
    }


    // REQUIRES: The number of course selected must be >=0 and <= courses.size()
    // EFFECTS: shows the user the overall average for a course of their choosing
    private void calculateOverallAverage() {
        System.out.println("What number course do you want to know your average?");
        int courseNum = Integer.parseInt(input.next());
        Course selectedCourse = courses.get(courseNum);
        double overallAverage = selectedCourse.calculateOverallAverage();
        System.out.println(selectedCourse.getCourseName() + ":" + " " + overallAverage + "%");
        System.out.println("Assignments in this course:");
        for (Assignment assignment : selectedCourse.getAssignments()) {
            System.out.println(assignment.getName() + ":" + " " + assignment.getGrade() + "%");
        }

    }

    // EFFECTS: saves the Courses to file
    private void saveCourse() {
        try {
            jsonWriter.open();
            jsonWriter.write(courses);
            jsonWriter.close();
            System.out.println("Saved to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads course from file
    private void loadCourse() {
        try {
            this.courses = jsonReader.read();
            System.out.println("Loaded from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }



}
