package src.java;

import java.util.Stack;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.AbstractMap.SimpleEntry;
import java.util.function.BiConsumer;

public class CourseScheduler {
    private String database;
    private HashMap<Integer, Student> students;
    private HashMap<Integer, Faculty> faculty; 
    private HashMap<String, Course> courses;

    public CourseScheduler(LoadSQL loader) {
        database = loader.getDatabase();
        courses = loader.loadCourses();
        students = loader.loadStudents(courses);
        faculty = loader.loadFaculty(courses); 
    }

    // course scheduling algorithm
    public HashMap<String, Course> scheduleCourses() {
        var scheduledCourses = (HashMap<String, Course>)courses.clone();
        
        // number of students in each course
        HashMap<String, Integer> numStudents = new HashMap<>();  
        Stack<Entry<String, Student>> noSuchCourse = new Stack<>();

        // count how many students want each class
        students.forEach((studentId, student) -> {
            student.forEachCourse(id -> {
                // if the class does not exist, remove it
                if (!courses.containsKey(id)) 
                    noSuchCourse.push(new SimpleEntry<>(id, student));
                else {  // if the class exists, track student count
                    if (numStudents.containsKey(id))
                        numStudents.put(id, numStudents.get(id) + 1);
                    else
                        numStudents.put(id, 1);
                }
            });
        });

        // prune courses that do not exist
        if (!noSuchCourse.empty())
            System.out.println(noSuchCourse.size()+" nonexistent course(s) removed from students");
        while (!noSuchCourse.empty()) {
            var entry = noSuchCourse.pop(); 
            entry.getValue().removeCourse(entry.getKey());
        } 
            

        // allocate sessions for students, if there aren't enough students remove course
        numStudents.forEach((courseId, studentCount) -> {
            Course course = courses.get(courseId);

            if (studentCount >= course.getCourseMinStudents()) {
                int numSessions = studentCount < course.getSessionMaxStudents() ? 1
                            : studentCount / course.getSessionMaxStudents() 
                            + (studentCount % course.getSessionMaxStudents() == 0 ? 0:1);
            
                // schedule as many courses as we need to, unless we run out of 
                // professors to teach sessions
                while (--numSessions >= 0 && course.addSession(faculty)) {}
            }
            else courses.remove(courseId);
        }); 

        // fill sessions with students
        courses.forEach((courseId, course) -> {
            int studentsPerSession = 0;
            while (studentsPerSession < course.getSessionMaxStudents() && course.waitlistNotEmpty()) {
                course.forEachSession(sessionPair -> {
                    int studentId = course.getFromWaitlist();
                    if (studentId != -1) 
                        sessionPair.getValue().addStudent(studentId);
                });
            }
        });

        // prune sessions that are too small
        // courses.forEach((courseId, course) -> {
        //     if (course.getLastSession() != null && course.getLastSession().getNumStudents() < course.getSessionMinStudents()) {
        //         Session deletedSession = course.popLastSession();
        //         course.forEachSession(sessionPair -> {
        //             int studentId = deletedSession.removeStudent();
        //             if (studentId != -1)
        //                 sessionPair.getValue().addStudent(deletedSession.removeStudent());
        //         });
        //     }
        // });

        return courses;
    }

    public void printStats() {
        System.out.println("______________________________________" +
                         "\nTotal Students                 | " + Student.getNumStudents() +
                         "\nTotal Faculty                  | " + Faculty.getNumFaculty() +
                         "\nTotal Courses                  | " + Course.getNumCourses() + 
                         "\nTotal Sessions Scheduled       | " +
                         "\nTotal Courses Unscheduled      | " +
                         "\nTotal Students With No Classes |");
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        int headers = 25;

        for (int i = 0; i < headers; ++i) str.append("***STUDENTS");
        str.append("\n\n\n");
        students.forEach((id, x) -> { str.append(x); }); 

        str.append("\n\n\n");
        for (int i = 0; i < headers; ++i) str.append("***FACULTY");
        str.append("\n\n\n\n\n\n");
        faculty.forEach((id, x) -> { str.append(x); }); 

        str.append("\n\n\n");
        for (int i = 0; i < headers; ++i) str.append("***COURSES");
        str.append("\n\n\n\n\n\n");
        courses.forEach((id, course) -> {
            str.append(course);
        });

        return str.toString();
    }   

    /** Iterator Methods */
    // iterate over the scheduler's students
    public void forEachStudent(BiConsumer<? super Integer,? super Student> f) {
        students.forEach(f);
    }

    // iterate over the scheduler's faculty
    public void forEachFaculty(BiConsumer<? super Integer,? super Faculty> f) {
        faculty.forEach(f);
    }

    // iterate over the scheduler's courses
    public void forEachCourse(BiConsumer<? super String, ? super Course> f) {
        courses.forEach(f);
    }

    /** Getters */
    public final HashMap<Integer, Student> getStudents() { return students; }
    public final HashMap<Integer, Faculty> getFaculty() { return faculty; }
    public final HashMap<String, Course> getCourses() { return courses; }

    public String getDatabase() { return database; }
}