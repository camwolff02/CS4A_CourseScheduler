package src.java;

// if there are not enough students to fill a session, that session is cancelled
public class Session {
    // class data
    private static int currId = 1000000;
    private static int numSessions = 0;

    // for user to access
    private int id;  // unique session id

    // for user to modify
    private int numStudents;  // number of students in this session
    private String profFirstName;
    private String profLastName;

    public Session(String profFirstName, String profLastName) {
        this.profFirstName = profFirstName;
        this.profLastName = profLastName;
        numStudents = 0;
        ++numSessions;

        id = generateNextId(id);
    }

    private static int generateNextId(int id) {
        return currId++;
    }

    /** Getters and Setters */
    public static int getNumSessions() { return numSessions; }

    public int getId() { return this.id; }

    public int getNumStudents() { return this.numStudents; }

    public String getProfFirstName() { return this.profFirstName; }
    public void setProfFirstName(String profFirstName) { this.profFirstName = profFirstName; }

    public String getProfLastName() { return this.profLastName; }
    public void setProfLastName(String profLastName) { this.profLastName = profLastName; }

}