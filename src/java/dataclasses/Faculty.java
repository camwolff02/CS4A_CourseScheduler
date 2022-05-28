package src.java.dataclasses;

import java.sql.Date;

import java.util.HashSet;
import java.util.function.Consumer;

public class Faculty extends Person {
    // class data
    private static int numFaculty = 0;

    // for user to access
    private static int maxCourses = 6;
    private Date hireDate;
    private boolean isTenured;

    private HashSet<String> taughtCourses;

    public Faculty(String firstName, String middleName, String lastName, String email,
        String telephone, String street, String city, String state, int zip,
        Date hireDate, boolean isTenured) {
    
        super(firstName, middleName, lastName, email, telephone, street, city, state, zip);
        this.hireDate = hireDate;
        this.isTenured = isTenured;

        ++numFaculty;
        taughtCourses = new HashSet<>();
    }

    @Override
    protected void finalize() {
        --numFaculty;
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

    public void removeCourse(String courseId) {
        taughtCourses.remove(courseId);
    }

    // iterate over a student's courses
    public void forEachCourse(Consumer<? super String> f) {
        taughtCourses.forEach(f);
    }

    /** Getters and Setters */
    public static int getNumFaculty() { return numFaculty; }

    public static int getMaxCourses() { return maxCourses; }
    public static void setMaxCourses(int maxCourses) { Faculty.maxCourses = maxCourses; }

    public Date getHireDate() { return this.hireDate; }
    public void setHireDate(Date hireDate) { this.hireDate = hireDate; }
   
    public boolean isTenured() { return this.isTenured; }
    public boolean getIsTenured() { return this.isTenured; }
    public void setIsTenured(boolean isTenured) { this.isTenured = isTenured; }
}