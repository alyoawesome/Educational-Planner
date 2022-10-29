package persistence;

import model.Assignment;
import model.Course;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

//CITATION: Reused code from JsonSerializationDemo's JsonReader Class for the methods in this class

// Represents a reader that reads courses from JSON data stored in file
public class JsonReader {
    private String source;


    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads courses from file and returns it;
    // throws IOException if an error occurs reading data from file
    public ArrayList<Course> read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseCourses(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses list of courses from JSON object and returns the list of courses
    private ArrayList<Course> parseCourses(JSONObject jsonObject) {
        JSONArray courses = jsonObject.getJSONArray("courses");
        ArrayList<Course> courseList = new ArrayList<>();
        for (Object json : courses) {
            JSONObject nextThingy = (JSONObject) json;
            addCourse(nextThingy,courseList);
        }
        return courseList;
    }

    // MODIFIES: courses
    // EFFECTS: parses course from JSON object and adds them to list of courses
    private void addCourse(JSONObject jsonObject, ArrayList<Course> courses) {
        String name = jsonObject.getString("name");
        JSONArray assignments = jsonObject.getJSONArray("assignments");
        Course c = new Course(name,addAssignments(assignments));
        courses.add(c);
    }

    // MODIFIES: assignmentList
    // EFFECTS: parses assignments from JSON object, adds them to list of assignments, and returns
    //          the list of assignments
    private ArrayList<Assignment> addAssignments(JSONArray assignments) {
        ArrayList<Assignment> assignmentList = new ArrayList<>();
        for (Object json : assignments) {
            JSONObject nextAssignment = (JSONObject) json;
            assignmentList.add(addAssignment(nextAssignment));
        }
        return assignmentList;
    }

    // EFFECTS: parses assignment from JSON object and returns a new assignment
    private Assignment addAssignment(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        Double grade = jsonObject.getDouble("grade");
        return new Assignment(name, grade);
    }

}
