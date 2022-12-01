package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

//CITATION: Reused code from AlarmSystem and JsonSerializationDemo's WorkRoom Class for the methods in this class

//Represents a course having a name, an overall average (in percent), and a list of assignments
public class Course implements Writable {
    private String name;
    private double overallAverage;
    private ArrayList<Assignment> assignments;

    // REQUIRES: the course's name needs to be a String (no numbers) and has length > 0;
    //           list of assignments must be empty when course is first made
    // EFFECTS:  creates a Course with a name, a list of assignments, and an overall average of 0
    public Course(String name, ArrayList<Assignment> assignments) {
        this.name = name;
        this.assignments = assignments;
        this.overallAverage = 0;

    }



    // MODIFIES: this
    // EFFECTS: adds assignment to the course's list of assignments if not already in the list, else provides an error
    //          saying that the assignments has already been added
    public void addAssignment(Assignment assignment) {
        String name = assignment.getName();
        if (!this.assignments.contains(assignment)) {
            this.assignments.add(assignment);
            EventLog.getInstance().logEvent(new Event("Added Assignment:"
                    + " " + name + " " + "to" + " " + this.name));
            EventLog.getInstance().logEvent(new Event("Added Assignment:"
                    + " " + name + " " + "to all assignments panel"));
        } else {
            System.out.println("ERROR, assignment already in course!");
        }
    }

    // REQUIRES: The assignment the user wants to delete must already be in the course's list of assignments
    // MODIFIES: this
    // EFFECTS: deletes an assignment from the course's list of assignments
    public void deleteAssignment(String assignment) {
        for (Assignment a : this.assignments) {
            String name = a.getName();
            if (name.equals(assignment)) {
                this.assignments.remove(this.assignments.indexOf(a));
                EventLog.getInstance().logEvent(new Event("Deleted Assignment:"
                        + " " + name + " " + "from" + " " + this.name));
                EventLog.getInstance().logEvent(new Event("Deleted Assignment:"
                        + " " + name + " " + "from all assignments panel"));
                break;
            }
        }

    }

    // MODIFIES: this
    // EFFECTS: calculates overall course average from the assignments' grades
    public double calculateOverallAverage() {
        int numAssignments = 0;
        this.overallAverage = 0;
        for (Assignment assignment : this.assignments) {
            this.overallAverage += assignment.getGrade();
            numAssignments = numAssignments + 1;
        }
        if (numAssignments == 0) {
            this.overallAverage = this.overallAverage / 1;
        } else {
            this.overallAverage = this.overallAverage / numAssignments;
        }
        return this.overallAverage;
    }


    public String getCourseName() {
        return this.name;
    }


    public ArrayList<Assignment> getAssignments() {
        return this.assignments;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("assignments", assignmentsToJson());
        return json;
    }

    // EFFECTS: returns assignments in this course as a JSON array
    private JSONArray assignmentsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Assignment a : assignments) {
            jsonArray.put(a.toJson());
        }

        return jsonArray;
    }


}
