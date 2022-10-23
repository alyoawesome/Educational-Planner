package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class CourseTest {
    private Course courseTest;
    private ArrayList<Assignment> assignmentsForCourse;
    private Assignment assignmentTest1;
    private Assignment assignmentTest2;


    @BeforeEach
    public void setUp() {
        assignmentsForCourse = new ArrayList<>();
        courseTest = new Course("Math", assignmentsForCourse);
        assignmentTest1 = new Assignment("ProjectA", 80);
        assignmentTest2 = new Assignment("Midterm", 100);
    }

    @Test
    public void constructorTest() {
        assertEquals("Math", courseTest.getCourseName());
        assertEquals(assignmentsForCourse, courseTest.getAssignments());
        assertEquals(0, courseTest.calculateOverallAverage());
    }

    @Test
    public void addAssignmentTest() {
        courseTest.addAssignment(assignmentTest1);
        assertTrue(courseTest.getAssignments().contains(assignmentTest1));
    }

    @Test
    public void addMultipleAssignmentsTest() {
        courseTest.addAssignment(assignmentTest1);
        assertTrue(courseTest.getAssignments().contains(assignmentTest1));
        courseTest.addAssignment(assignmentTest2);
        assertTrue(courseTest.getAssignments().contains(assignmentTest1));
        assertTrue(courseTest.getAssignments().contains(assignmentTest2));
    }

    @Test
    public void addSameAssignmentTest() {
        courseTest.addAssignment(assignmentTest1);
        assertEquals(1, courseTest.getAssignments().size());
        assertTrue(courseTest.getAssignments().contains(assignmentTest1));
        courseTest.addAssignment(assignmentTest1);
        assertEquals(1, courseTest.getAssignments().size());
        assertTrue(courseTest.getAssignments().contains(assignmentTest1));

    }

    @Test
    public void deleteAssignmentTest() {
        courseTest.addAssignment(assignmentTest1);
        assertEquals(1, courseTest.getAssignments().size());
        assertTrue(courseTest.getAssignments().contains(assignmentTest1));
        courseTest.deleteAssignment(assignmentTest1.getName());
        assertEquals(0, courseTest.getAssignments().size());
        assertFalse(courseTest.getAssignments().contains(assignmentTest1));
    }

    @Test
    public void deleteMultipleAssignmentsTest() {
        courseTest.addAssignment(assignmentTest1);
        assertEquals(1, courseTest.getAssignments().size());
        assertTrue(courseTest.getAssignments().contains(assignmentTest1));
        courseTest.addAssignment(assignmentTest2);
        assertEquals(2, courseTest.getAssignments().size());
        assertTrue(courseTest.getAssignments().contains(assignmentTest2));
        courseTest.deleteAssignment(assignmentTest2.getName());
        assertEquals(1, courseTest.getAssignments().size());
        assertFalse(courseTest.getAssignments().contains(assignmentTest2));
        courseTest.deleteAssignment(assignmentTest1.getName());
        assertEquals(0, courseTest.getAssignments().size());
        assertFalse(courseTest.getAssignments().contains(assignmentTest1));

    }

    @Test
    public void calculateOverallAverageOnceTest() {
        courseTest.addAssignment(assignmentTest1);
        assertEquals(80, courseTest.calculateOverallAverage());

    }

    @Test
    public void calculateOverallAverageMultipleTimesTest() {
        courseTest.addAssignment(assignmentTest1);
        assertEquals(80, courseTest.calculateOverallAverage());
        courseTest.addAssignment(assignmentTest2);
        assertEquals(90, courseTest.calculateOverallAverage());

    }








}