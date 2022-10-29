package persistence;

import model.Assignment;
import model.Course;

import static org.junit.jupiter.api.Assertions.assertEquals;

//CITATION: Reused code from JsonSerializationDemo's JsonTest Class for the methods in this class

public class JsonTest {
    protected void checkThingy(String name, Course course) {
        assertEquals(name, course.getCourseName());
    }

    protected void checkThingy2(String name, Double grade, Assignment assignment) {
        assertEquals(name, assignment.getName());
        assertEquals(grade, assignment.getGrade());
    }
}