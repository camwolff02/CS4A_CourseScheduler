package src.java.dataclasses;

import java.util.Stack;

// if there are not enough students to fill a session, that session is cancelled
public class Session {
    // class data
    private static int currId = 1000000;
    private static int numSessions = 0;

    // for user to access
    private int id;  // unique session id
    private Stack<Integer> students;  // set of all students in this course

    // for user to modify
    private int numStudents;  // number of students in this session
    private int profId;  // ID of professor teaching session


    public Session(int profId) {
        this.profId = profId;
        numStudents = 0;
        ++numSessions;

        id = generateNextId(id);
        students = new Stack<>();
    }

    @Override
    protected void finalize() {
        --numSessions;
    }

    public void addStudent(int studentId) {
        students.push(studentId);
    }

    public Integer removeStudent() {
        return students.pop();
    }

    private static int generateNextId(int id) {
        return currId++;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();

        str.append("SESSION | Professor: "+ profId +", Session id: "+id+", Students: ");
        students.forEach(studentId -> {
            str.append(studentId + "\t");
        });

        return str.toString();
    }

    /** Getters and Setters */
    public static int getNumSessions() { return numSessions; }

    public int getId() { return this.id; }

    public int getNumStudents() { return this.numStudents; }

    public int getProfId() { return this.profId; }
    public void setProfId(int profId) { this.profId = profId; }


}