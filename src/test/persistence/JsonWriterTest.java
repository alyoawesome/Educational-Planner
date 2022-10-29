package persistence;


import model.Assignment;
import model.Course;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;


import static org.junit.jupiter.api.Assertions.*;

//CITATION: Reused code from JsonSerializationDemo's JsonWriterTest Class for the methods in this class
class JsonWriterTest extends JsonTest {


    @Test
    void testWriterInvalidFile() {
        try {
            ArrayList<Course> courses = new ArrayList<Course>();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmpty() {
        try {
            ArrayList<Course> courses = new ArrayList<Course>();
            JsonWriter writer = new JsonWriter("./data/testWriterEmpty.json");
            writer.open();
            writer.write(courses);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmpty.json");
            courses = reader.read();
            assertEquals(0, courses.size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneral() {
        try {
            ArrayList<Course> courses = new ArrayList<Course>();
            ArrayList<Assignment> assignmentsList1 =  new ArrayList<>();
            Assignment assignment1 = new Assignment("test", 99);
            Assignment assignment2 = new Assignment("project", 98);
            assignmentsList1.add(assignment1);
            assignmentsList1.add(assignment2);
            courses.add(new Course("science", assignmentsList1));

            ArrayList<Assignment> assignmentsList2 =  new ArrayList<>();
            Assignment assignment3 = new Assignment("midterm", 97);
            Assignment assignment4 = new Assignment("assignment", 96);
            assignmentsList2.add(assignment3);
            assignmentsList2.add(assignment4);
            courses.add(new Course("math", assignmentsList2));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneral.json");
            writer.open();
            writer.write(courses);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneral.json");
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
            fail("Exception should not have been thrown");
        }
    }
}