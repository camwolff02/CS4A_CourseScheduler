package src.java;

import java.util.ArrayList;
import java.sql.Date;

/* TODO:
- import data to classes
- create a flexible way to change id algorithms for both people and sessions
*/

public class Driver {
    public static void main(String[] args) {
        LoadSQL loader = new LoadSQL("College");  // load data
        CourseScheduler scheduler = new CourseScheduler(loader);  // pass data to scheduler
        //System.out.println(scheduler);
        scheduler.scheduleCourses();
    }
}

// if there are no sessions for a course, that class is cancelled
// if this course does not meet the minimum number of students, cancelled