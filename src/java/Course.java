package src.java;

import java.util.ArrayList;

public class Course {
    // class data
    private static int numCourses = 0;

    // for user to access
    protected int totalStudents;  // number of students in this course
    protected String id;  // department + code

    // for user to modify
    protected String department;  // ex. CS, BIO
    protected String code;  // ex. 1A, 4A, 4B
    protected String description;  
    protected int minStudents;  
    protected int maxStudents;
    protected ArrayList<Session> sessions; 

    public Course(String department, String code, String description, int minStudents, int maxStudents) {
        this.department = department;
        this.code = code;
        this.description = description;
        id = department + code;
        this.minStudents = minStudents;
        this.maxStudents = maxStudents;

        sessions = new ArrayList<>();
        totalStudents = 0;
        ++numCourses;
    }

    public Course(String department, String code, String description) {
        this(department, code, description, 12, 40);
    }

    @Override
    public String toString() {
        return String.format("Class: %s, Description: %s, minimum students: %d, maximum students: %d%n",
            id, description, minStudents, maxStudents);
    }

    public void addSession(String profFirstName, String profLastName) {
        sessions.add(new Session(profFirstName, profLastName));
    }

    /** Getters and Setters */
    public int getTotalStudents() { return this.totalStudents; }

    public String getId() { return this.id; }

    public String getDepartment() { return this.department; }
    public void setDepartment(String department) { this.department = department; }

    public String getCode() { return this.code; }
    public void setCode(String code) { this.code = code; }

    public String getDescription() { return this.description; }
    public void setDescription(String description) { this.description = description; }

    public int getMinStudents() { return this.minStudents; }
    public void setMinStudents(int minStudents) { this.minStudents = minStudents; }

    public int getMaxStudents() { return this.maxStudents; }
    public void setMaxStudents(int maxStudents) { this.maxStudents = maxStudents; }
}