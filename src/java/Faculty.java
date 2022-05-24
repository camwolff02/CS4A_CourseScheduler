package src.java;

import java.util.ArrayList;
import java.sql.Date;

public class Faculty extends Person {
    // class data
    private static int numFaculty = 0;

    // for user to access
    private static int maxCourses = 6;
    private Date hireDate;
    private boolean isTenured;

    private ArrayList<String> taughtCourses;

    public Faculty(String firstName, String middleName, String lastName, String email,
        String telephone, String street, String city, String state, int zip,
        Date hireDate, boolean isTenured) {
    
        super(firstName, middleName, lastName, email, telephone, street, city, state, zip);
        this.hireDate = hireDate;
        this.isTenured = isTenured;

        ++numFaculty;
        taughtCourses = new ArrayList<>();
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append(String.format("hire date: %s, is tenured? %b, taught courses:%n",
                    hireDate, isTenured));
        
        taughtCourses.forEach(course -> {
            str.append(course + "\t");
        });

        return super.toString() + str + "\n\n";
    }

    public void addCourse(String courseId) {
        taughtCourses.add(courseId);
    }

    /** Getters and Setters */
    public static int getNumFaculty() { return numFaculty; }

    public Date getHireDate() { return this.hireDate; }
    public void setHireDate(Date hireDate) { this.hireDate = hireDate; }
   
    public boolean isTenured() { return this.isTenured; }
    public boolean getIsTenured() { return this.isTenured; }
    public void setIsTenured(boolean isTenured) { this.isTenured = isTenured; }
}