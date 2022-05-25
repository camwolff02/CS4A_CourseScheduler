package src.java;

import java.sql.SQLException;
/* TODO:
- create a flexible way to change id algorithms for both people and sessions
- create a flexible way to change course scheduling algorithms
*/
import java.util.Scanner;

import src.java.utilclasses.CourseScheduler;
import src.java.utilclasses.FileGenerator;
import src.java.utilclasses.LoadSQL;

public class Driver {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);        
        LoadSQL loader = null;

        // load database
        do {    
            try {
                System.out.print("Enter Database name: ");
                loader = new LoadSQL(in.nextLine());
                System.out.println("Database loaded successfully");
            } catch (SQLException e) {
                System.out.println("Error: SQL connection to database unsuccessful");
            }
        } while (loader == null);
        System.out.println();

        // load data from database into scheduler
        CourseScheduler scheduler = new CourseScheduler(loader);  // pass data to scheduler
        System.out.println("Data loaded successfully\n");
        
        // process data in scheduler
        scheduler.scheduleCourses();
        scheduler.printStats();

        // generate output files from processing
        FileGenerator fileGenerator = new FileGenerator(scheduler);
        fileGenerator.generateScheduler();

        in.close();
    }
}

// if there are no sessions for a course, that class is cancelled
// if this course does not meet the minimum number of students, cancelled