package ui;

import model.Assignment;
import model.Course;

import java.util.ArrayList;
import java.util.Scanner;

//CITATION: Learned to use Scanner classes and manipulate user input from the TellerApp
//Educational Planner application
public class EducationalPlannerApp {
    private Scanner input;
    private ArrayList<Course> courses = new ArrayList<>();

    // EFFECTS: runs the Educational Planner application
    public EducationalPlannerApp() {
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
        System.out.println("\tavg -> calculate average");
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

    // REQUIRES: The number of course selected must be >=0 and <= courses.size()
    // EFFECTS: shows the user the overall average for a course of their choosing
    private void calculateOverallAverage() {
        System.out.println("What number course do you want to know your average?");
        int courseNum = Integer.parseInt(input.next());
        double overallAverage = courses.get(courseNum).calculateOverallAverage();
        System.out.println(courses.get(courseNum).getCourseName() + ":" + overallAverage + "%");

    }


}
