package src.java;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.*;

public class CourseScheduler {
    ArrayList<Student> students;
    ArrayList<Faculty> faculty; 
    HashMap<String, Course> courses;

    public CourseScheduler(LoadSQL loader) {
        students = loader.loadStudents();
        faculty = loader.loadFaculty(); 
        courses = loader.loadCourses();
    }

    public void scheduleCourses() {
        // number of students in each course
        HashMap<String, Integer> numStudents = new HashMap<>();  

        // count how many students want each class
        students.forEach(student -> {
            student.forEachCourse(id -> {
                // if the class does not exist, remove it
                

                // if the class exists, track student count
                if (numStudents.containsKey(id))
                    numStudents.put(id, numStudents.get(id) + 1);
                else
                    numStudents.put(id, 1);
            });
        });

        // allocate sessions for students
        numStudents.forEach((courseId, studentCount) -> {
            try {
                Course course = courses.get(courseId);
    
                int numSessions = studentCount < course.getMaxStudents() ? 1
                                : studentCount / course.getMaxStudents() 
                                + studentCount % course.getMaxStudents();
                
                System.out.printf("Course: %s Students: %d Max: %d Sessions: %d%n",
                    courseId,studentCount,course.getMaxStudents(), numSessions);
            }
            catch (NullPointerException e) {
                System.out.println("Error: course " + courseId + " does not exist. Removing requested course.");
            }
        }); 
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        
        students.forEach(x -> { str.append(x); }); 
        str.append("***********************************************************"
            + "****************************************************************");

        faculty.forEach(x -> { str.append(x); }); 
        str.append("***********************************************************"
            + "****************************************************************");

        courses.forEach((id, course) -> {
            str.append(course);
        });

        return str.toString();
    }   
    
    /** Iterator Methods */
    // iterate over the scheduler's students
    public void forEachStudent(Consumer<? super Student> f) {
        students.forEach(f);
    }

    // iterate over the scheduler's faculty
    public void forEachFaculty(Consumer<? super Faculty> f) {
        faculty.forEach(f);
    }

    // iterate over the scheduler's courses
    public void forEachCourse(BiConsumer<? super String, ? super Course> f) {
        courses.forEach(f);
    }
    
}