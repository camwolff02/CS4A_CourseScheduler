package src.java.utilclasses;

import java.util.HashMap;
import java.util.HashSet;
import java.util.function.BiConsumer;

import src.java.dataclasses.Course;
import src.java.dataclasses.Faculty;
import src.java.dataclasses.Student;

public class CourseScheduler {
    private final HashMap<Integer, Student> students;
    private final HashMap<Integer, Faculty> faculty; 
    private final HashMap<String, Course> courses;

    private String database;

    private int coursesUnscheduled;

    public CourseScheduler(LoadSQL loader) {
        database = loader.getDatabase();
        courses = loader.loadCourses();
        students = loader.loadStudents(courses);
        faculty = loader.loadFaculty(courses); 

        coursesUnscheduled = -1;
    }

    // course scheduling algorithm
    public HashMap<String, Course> scheduleCourses() {
        HashSet<String> scheduledCourses = new HashSet<>();
        
        // number of students in each course
        HashMap<String, Integer> studentsPerCourse = new HashMap<>();  

        System.out.println("Scheduling courses");

        // count how many students want each class
        students.forEach((studentId, student) -> {
            student.forEachCourse(courseId -> {
                courses.get(courseId).addStudent(studentId);
                if (studentsPerCourse.containsKey(courseId))
                    studentsPerCourse.put(courseId, studentsPerCourse.get(courseId) + 1);
                else
                    studentsPerCourse.put(courseId, 1);
            });
        });

        // allocate sessions for students, if there aren't enough students remove course
        studentsPerCourse.forEach((courseId, studentCount) -> {
            Course course = courses.get(courseId);

            if (studentCount >= course.getCourseMinStudents()) {
                int numSessions = studentCount < course.getSessionMaxStudents() ? 1
                            : studentCount / course.getSessionMaxStudents() 
                            + (studentCount % course.getSessionMaxStudents() == 0 ? 0:1);

                // schedule as many courses as we need to, unless we run out of 
                // professors to teach sessions
                while (--numSessions >= 0 && course.addSession(faculty)) {}

                scheduledCourses.add(courseId);
            }
        }); 
        coursesUnscheduled = courses.size() - scheduledCourses.size();
        if (coursesUnscheduled > 0)
            System.out.println(coursesUnscheduled + " course(s) removed, not enough students");

        // fill sessions with students
        System.out.println("Filling sessions...");
        scheduledCourses.forEach(courseId -> {
            Course course = courses.get(courseId);

            course.forEachSession(sPair -> {
                var session = sPair.getValue();

                for (int i = 0; i < course.getSessionMaxStudents() && course.waitlistHasStudents(); ++i) {
                    int studentId = course.removeFromWaitlist();
                    if (studentId != -1) 
                        session.addStudent(studentId);
                }
            });
        });

        // check which students don't have sessions
        students.forEach((studentId, student) -> {
            student.forEachCourse(courseId -> {
                if (scheduledCourses.contains(courseId))
                    student.setHasNoCourses(false);
            });
            if (!student.hasCourses()) Student.incStudentsWithNoClasses();
        }); 

        return courses;
    }

    public void printStats() {
        System.out.println("______________________________________" +
                         "\nTotal Students                 | " + Student.getNumStudents() +
                         "\nTotal Faculty                  | " + Faculty.getNumFaculty() +
                         "\nTotal Courses                  | " + Course.getNumCourses() + 
                         "\nTotal Sessions Scheduled       | " + Course.getNumSessions() +
                         "\nTotal Courses Unscheduled      | " + coursesUnscheduled +
                         "\nTotal Students With No Classes | " + Student.getStudentsWithNoClasses());
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