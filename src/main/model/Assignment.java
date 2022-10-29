package model;

import org.json.JSONObject;
import persistence.Writable;

//CITATION: Reused code from JsonSerializationDemo's Thingy Class for the methods in this class



// Represents an assignment having a name and grade (in percent)
public class Assignment implements Writable {
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

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("grade", grade);
        return json;
    }
        
}
