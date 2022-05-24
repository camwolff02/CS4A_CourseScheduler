package src.java;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Stack;
import java.util.function.Consumer;



public class Course {
    // class data
    private static int numCourses = 0;
    private int currentFaculty;  // current professor's class we are adding to


    // for user to access
    private int totalStudents;  // number of students in this course
    private String id;  // department + code
    private int courseMinStudents;  // minimum students for course to be scheduled
    private int courseMaxStudents;  // maximum students allowed in the course

    // for user to modify
    private String department;  // ex. CS, BIO
    private String code;  // ex. 1A, 4A, 4B
    private String description;  
    private int sessionMinStudents;  // maximum students for one session
    private int sessionMaxStudents;  // minimum students for one session
    private Stack<Entry<Integer, Session>> sessions;  // all sessions of this course
    private ArrayList<Entry<Integer, Integer>> faculty;  // professors mapped to the number of courses they teach
    private Queue<Integer> waitlist;  // queue of students not scheduled for this course
    

    public Course(String department, String code, String description, int sessionMinStudents, int sessionMaxStudents) {
        this.department = department;
        this.code = code;
        this.description = description;
        this.id = department + code;
        this.sessionMinStudents = sessionMinStudents;
        this.sessionMaxStudents = sessionMaxStudents;

        courseMinStudents = sessionMinStudents;
        courseMaxStudents = sessionMaxStudents;

        sessions = new Stack<>();
        faculty = new ArrayList<>();
        waitlist = new LinkedList<>();
        totalStudents = 0;
        currentFaculty = -1;
        ++numCourses;
    }

    public Course(String department, String code, String description) {
        this(department, code, description, 12, 40);
    }

    @Override
    protected void finalize() {
        --numCourses;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append(String.format("Course: %s, Description: %s, minimum students: %d, maximum students: %d%n",
                        id, description, sessionMinStudents, sessionMaxStudents));
        
        if (sessions.empty()) str.append("NO SESSIONS SCHEDULED");
        sessions.forEach(sPair -> {
            str.append(sPair.getValue().toString() + "\n");
        });
        return str.toString() + "\n\n";
    }

    public boolean addSession(HashMap<Integer, Faculty> ftable) {
        int facultyId, numClasses, facultyChecked=0;
        do {
            currentFaculty = (currentFaculty+1 < sessionMaxStudents ? currentFaculty+1 : 0);
            facultyId = faculty.get(currentFaculty).getKey();
            numClasses = faculty.get(currentFaculty).getValue();
            ++facultyChecked;
        } while (numClasses == 0 && facultyChecked < faculty.size());

        if (facultyChecked == faculty.size()) return false;

        faculty.set(currentFaculty, new SimpleEntry<>(facultyId, numClasses-1));
        sessions.push(new SimpleEntry<>(facultyId, new Session(facultyId)));

        return false;
    }

    // add another instructor that teaches this course
    public void addFaculty(int facultyId) {
        faculty.add(new SimpleEntry<>(facultyId, Faculty.getMaxCourses()));
        courseMaxStudents = faculty.size() * sessionMaxStudents;
    }

    // add another student who wants this course
    public void addStudent(int studentId) {
        waitlist.add(studentId);
    }

    public boolean waitlistIsEmpty() { return waitlist.isEmpty(); }
    public boolean waitlistNotEmpty() { return !waitlist.isEmpty(); }

    public int getFromWaitlist() {
        if (waitlist.isEmpty()) return -1;
        else return waitlist.remove();
    }

    public void forEachSession(Consumer<? super Entry<Integer, Session>> f) {
        sessions.forEach(f);
    }

    /** Getters and Setters */
    public Session getLastSession() { 
        if (!sessions.isEmpty())
            return sessions.peek().getValue(); 
        else
            return null;
    }
    public Session popLastSession() { 
        if (!sessions.isEmpty())
            return sessions.pop().getValue(); 
        else
            return null;
    }

    public static int getNumCourses() { return numCourses; }

    public int getTotalStudents() { return this.totalStudents; }

    public String getId() { return this.id; }

    public String getDepartment() { return this.department; }
    public void setDepartment(String department) { this.department = department; }

    public String getCode() { return this.code; }
    public void setCode(String code) { this.code = code; }

    public String getDescription() { return this.description; }
    public void setDescription(String description) { this.description = description; }

    public int getSessionMinStudents() { return this.sessionMinStudents; }
    public void setSessionMinStudents(int sessionMinStudents) { this.sessionMinStudents = sessionMinStudents; }

    public int getSessionMaxStudents() { return this.sessionMaxStudents; }
    public void setSessionMaxStudents(int sessionMaxStudents) { this.sessionMaxStudents = sessionMaxStudents; }

    public int getCourseMinStudents() { return this.courseMinStudents; }
    public void setCourseMinStudents(int courseMinStudents) { this.courseMinStudents = courseMinStudents; }

    public int courseMaxStudents() { return this.courseMaxStudents; }  
    public void courseMaxStudents(int courseMaxStudents) { this.courseMaxStudents = courseMaxStudents; }
}