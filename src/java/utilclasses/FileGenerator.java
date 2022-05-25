package src.java.utilclasses;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.util.HashMap;

import src.java.dataclasses.Course;
import src.java.dataclasses.Faculty;
import src.java.dataclasses.Student;

public class FileGenerator {
    private String path;

    // constant references to database
    private final CourseScheduler scheduler;
    private final HashMap<Integer, Student> students;
    private final HashMap<Integer, Faculty> faculty;
    private final HashMap<String, Course> courses;

    public FileGenerator(CourseScheduler scheduler) { 
        path = "out" + File.separator + scheduler.getDatabase() + "-" ;
        this.scheduler = scheduler;
        this.students = scheduler.getStudents();
        this.faculty = scheduler.getFaculty(); 
        this.courses = scheduler.getCourses();
    }   

    public void generateScheduledCourseSessions() {
        File file = new File(path + "ScheduledCourseSessions.txt");
        FileWriter fout = null;

        try {
            fout = new FileWriter(file);
            StringBuilder str = new StringBuilder();

            /** WRITE TO STR HERE */ 

            /** END OF WRITE */ 
            
            fout.append(str.toString());

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try { fout.close(); } catch (Exception e) { };
        }
    }

    public void generateUnscheduledCourseSessions() {
        File file = new File(path + "UnscheduledCourseSessions.txt");
        FileWriter fout = null;

        try {
            fout = new FileWriter(file);
            StringBuilder str = new StringBuilder();

            /** WRITE TO STR HERE */ 

            /** END OF WRITE */ 
            
            fout.append(str.toString());

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try { fout.close(); } catch (Exception e) { };
        }
    }

    public void generateFaculty() {
        File file = new File(path + "ScheduledCourseSessions.txt");
        FileWriter fout = null;

        try {
            fout = new FileWriter(file);
            StringBuilder str = new StringBuilder();
            
            /** WRITE TO STR HERE */ 
            faculty.forEach((facultyId, facultyMember) -> {
                str.append(facultyMember.toString());
                // facultyMember.forEachCourse(courseId -> {
                //     courses.get(courseId).forEachSession(sPair -> {
                //         int fId = sPair.getKey();
                //         Session s = sPair.getValue();

                //         if (facultyId == fId) 
                //             str.append(s.toString() + "\n");
                //     });
                // });
            });
            /** END OF WRITE */ 
            
            fout.append(str.toString());

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try { fout.close(); } catch (Exception e) { };
        }
    }

    public void generateScheduledStudents() {
        File file = new File(path + "ScheduledStudents.txt");
        FileWriter fout = null;
        
        try {
            fout = new FileWriter(file);
            StringBuilder str = new StringBuilder();

            /** WRITE TO STR HERE */ 

            /** END OF WRITE */ 

            fout.append(str.toString());

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try { fout.close(); } catch (Exception e) { };
        }
    }

    public void generateUnscheduledStudents() {
        File file = new File(path + "UnscheduledStudents.txt");
        FileWriter fout = null;

        try {
            fout = new FileWriter(file);
            StringBuilder str = new StringBuilder();

            /** WRITE TO STR HERE */ 

            /** END OF WRITE */ 
            
            fout.append(str.toString());

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try { fout.close(); } catch (Exception e) { };
        }
    }

    public void generateScheduler() {
        File file = new File(path + "SchedulerData.txt");
        FileWriter fout = null;

        try {
            fout = new FileWriter(file);

            /** WRITE TO STR HERE */ 
            fout.append(scheduler.toString());

            /** END OF WRITE */ 
            

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try { fout.close(); } catch (Exception e) { };
        }
    }

}
