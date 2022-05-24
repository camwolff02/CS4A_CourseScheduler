package src.java;

import java.sql.SQLException;
/* TODO:
- create a flexible way to change id algorithms for both people and sessions
*/
import java.util.Scanner;

public class Driver {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        
        // new SchedulerGUI();
        LoadSQL loader = null;
        do {    
            try {
                System.out.print("Enter Database name: ");
                loader = new LoadSQL(in.nextLine());
            } catch (SQLException e) {
                System.out.println("Error: SQL connection to database unsuccessful");
            }
        } while (loader == null);
        System.out.println();

        CourseScheduler scheduler = new CourseScheduler(loader);  // pass data to scheduler
        scheduler.scheduleCourses();
        scheduler.printStats();
        // FileManager fileManager = new FileManager(scheduler.getStudents(), 
        //                                           scheduler.getFaculty(), 
        //                                           scheduler.getCourses());
        FileGenerator fileGenerator = new FileGenerator(scheduler);
        fileGenerator.generateScheduler();

        in.close();
    }
}

// if there are no sessions for a course, that class is cancelled
// if this course does not meet the minimum number of students, cancelled