package persistence;


import model.Course;
import model.Assignment;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;


import static org.junit.jupiter.api.Assertions.*;

//CITATION: Reused code from JsonSerializationDemo's JsonReaderTest Class for the methods in this class

class JsonReaderTest extends JsonTest {
    private ArrayList<Course> courses;
    private ArrayList<Assignment> assignmentsList1;
    private ArrayList<Assignment> assignmentsList2;


    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            courses = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmpty() {
        JsonReader reader = new JsonReader("./data/testReaderEmpty.json");
        try {
            courses = reader.read();
            assertEquals(0, courses.size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneral() {
        JsonReader reader = new JsonReader("./data/testReaderGeneral.json");
        try {
            courses = reader.read();
            assertEquals(2, courses.size());
            checkThingy("science", courses.get(0));
            checkThingy("math", courses.get(1));
            assignmentsList1 = courses.get(0).getAssignments();
            assertEquals(2, assignmentsList1.size());
            checkThingy2("test", 99.0, assignmentsList1.get(0));
            checkThingy2("project", 98.0, assignmentsList1.get(1));
            assignmentsList2 = courses.get(1).getAssignments();
            assertEquals(2, assignmentsList2.size());
            checkThingy2("midterm", 97.0, assignmentsList2.get(0));
            checkThingy2("assignment", 96.0, assignmentsList2.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}