package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AssignmentTest {
    private Assignment assignmentTest;

    @BeforeEach
    public void setUp() {
        assignmentTest = new Assignment("ProjectA", 80);
    }

    @Test
    public void constructorTest() {
        assertEquals("ProjectA", assignmentTest.getName());
        assertEquals(80, assignmentTest.getGrade());

    }
}
