package src.java;

import java.sql.Date;

import java.util.HashSet;
import java.util.Iterator;
import java.util.function.Consumer;

public class Student extends Person implements Iterable<String> {
    // class data
    private static int numStudents = 0;

    // for user to modify
    private static int maxCourses = 6;
    private Date dob;  // date of birth
    private Date startDate;
    private float gpa;

    private HashSet<String> registeredCourses;

    public Student(String firstName, String middleName, String lastName, String email,
        String telephone, String street, String city, String state, int zip,
        Date dob, Date startDate, float gpa) {
    
        super(firstName, middleName, lastName, email, telephone, street, city, state, zip);
        this.dob = dob;
        this.startDate = startDate;
        this.gpa = gpa;

        ++numStudents;
        registeredCourses = new HashSet<>();
    }

    @Override
    protected void finalize() {
        --numStudents;
    }
    
    public void addCourse(String courseId) {
        registeredCourses.add(courseId);
    }

    public void removeCourse(String courseId) {
        registeredCourses.remove(courseId);
    }

    // iterate over a student's courses
    public void forEachCourse(Consumer<? super String> f) {
        registeredCourses.forEach(f);
    }

    // iterate over a student's courses
    @Override
    public Iterator<String> iterator() {
        return registeredCourses.iterator();
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append(String.format("date of birth: %s, start date: %s, gpa: %.2f, registered courses:%n",
                                 dob, startDate, gpa));

        registeredCourses.forEach(course -> {
            str.append(course + "\t");
        });

        return super.toString() + str + "\n\n";
    }

    /** Getters and Setters */
    public static int getNumStudents() { return numStudents; }

    public static int getMaxCourses() { return maxCourses; }
    public static void setMaxCourses(int maxCourses) { Student.maxCourses = maxCourses; }

    public Date getDob() { return this.dob; }
    public void setDob(Date dob) { this.dob = dob; }

    public Date getStartDate() { return this.startDate; }
    public void setStartDate(Date startDate) { this.startDate = startDate; }

    public float getGpa() { return this.gpa; }
    public void setGpa(float gpa) { this.gpa = gpa; }
}