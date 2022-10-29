package persistence;

import org.json.JSONObject;


//CITATION: Reused code from JsonSerializationDemo's writable interface for the code in this class

public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}