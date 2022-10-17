package model;

// Represents an assignment having a name and grade (in percent)
public class Assignment {
    private String name;
    private double grade;

    // REQUIRES: the assignment's name needs to be a String (no numbers) and has length > 0;
    //           grade must be > 0
    // EFFECTS:  creates an assignment with a name and grade
    public Assignment(String name, double grade) {
        this.name = name;
        this.grade = grade;
    }


    public String getName() {
        return this.name;
    }


    public double getGrade() {
        return this.grade;
    }
        
}
