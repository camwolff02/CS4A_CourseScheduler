package src.java;

/* TODO:
- create a flexible way to change id algorithms for both people and sessions
*/

public class Driver {
    public static void main(String[] args) {
        // new SchedulerGUI();
        LoadSQL loader = new LoadSQL("College");  // load data
        CourseScheduler scheduler = new CourseScheduler(loader);  // pass data to scheduler
        scheduler.scheduleCourses();
        System.out.println(scheduler);
    }
}

// if there are no sessions for a course, that class is cancelled
// if this course does not meet the minimum number of students, cancelled