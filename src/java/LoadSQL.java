package src.java;

import java.sql.Connection;     // connects you to the database
import java.sql.DriverManager;  // converts the database
import java.sql.ResultSet;      // resulf off a query
import java.sql.SQLException;   // for error throws
import java.sql.Statement;      // actual statement to send the sql server

import java.util.HashMap;
import java.util.Stack;

import com.mysql.cj.xdevapi.SqlUpdateResult;

public class LoadSQL  {
    private Connection con;
    private String database;
    private String url;
    private String username;
    private String password;
    
    public LoadSQL() throws SQLException {
        this("University");
    }

    public LoadSQL(String database) throws SQLException {
        this.database = database;
        url = "jdbc:mysql://localhost:3306/" + this.database;
        username = "root";
        password = "@Oreo9195";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            System.out.println("Error: SQL libraries not imported correctly");
            e.printStackTrace();
        } catch (SQLException e) {
            throw new SQLException();
        }
    }

    // close all databases on object destruction if databases are open
    protected void finalize() {
        try { con.close(); } catch (Exception e) { /* Ignored */ }
    }

    /** loading data for different classes */
    public HashMap<Integer, Student> loadStudents(HashMap<String, Course> courses) {
        HashMap<Integer, Student> students = new HashMap<>();
        String name = "ERROR";
        Statement statement = null;
        ResultSet result = null;
        
        try {
            statement = con.createStatement();
            result = statement.executeQuery("select * from Students");

            while (result.next()) {  // while there are still results coming in                
                Student s = new Student(
                    result.getString(1),   // firstName
                    result.getString(2),   // middleName
                    result.getString(3),   // lastName
                    result.getString(4),   // email
                    result.getString(5),   // telephone
                    result.getString(6),   // street
                    result.getString(7),   // city
                    result.getString(8),   // state
                    result.getInt(9),      // zip
                    result.getDate(10),    // dob
                    result.getDate(11),    // startDate
                    result.getFloat(12)    // gpa
                );

                name = s.getName();  // for error message
                var courseStr = result.getString(13);
                for (var str : courseStr.substring(1, courseStr.lastIndexOf('}') + 1).split("\"}")) {
                    s.addCourse(
                        str.replace("{\"department\":\"","")
                           .replace("\",\"code\":\"","")
                           .replace("\"},", "")
                           .replace(",","")
                    );
                }

                students.put(s.getId(), s);
            }
            
        }
        catch (SQLException e) {
            System.out.println("FATAL ERROR: student table incorrectly formatted");
            e.printStackTrace();
        }
        catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Warning: Student "+name+" has not enrolled in any classes" );
        } finally {
            
            try { result.close(); } catch (Exception e) { /* Ignored */ }
            try { statement.close(); } catch (Exception e) { /* Ignored */ }
        }

        return students;
    }
    
    public HashMap<Integer, Faculty> loadFaculty(HashMap<String, Course> courses) {
        HashMap<Integer, Faculty> faculty = new HashMap<>();
        String name = "ERROR";
        int coursesRemoved = 0;
        Statement statement = null;
        ResultSet result = null;

        try {
            statement = con.createStatement();
            result = statement.executeQuery("select * from Faculty");
            
            while (result.next()) {  // while there are still results coming in                
                Faculty f = new Faculty(
                    result.getString(1),   // firstName
                    result.getString(2),   // middleName
                    result.getString(3),   // lastName
                    result.getString(4),   // email
                    result.getString(5),   // telephone
                    result.getString(6),   // street
                    result.getString(7),   // city
                    result.getString(8),   // state
                    result.getInt(9),      // zip
                    result.getDate(10),    // hireDate
                    result.getBoolean(11)  // isTenured
                );

                name = f.getName();  // for error message
                var courseStr = result.getString(12);
                for (var str : courseStr.substring(1, courseStr.lastIndexOf('}') + 1).split("\"}")) {
                    f.addCourse(
                        str.replace("{\"department\":\"","")
                           .replace("\",\"code\":\"","")
                           .replace("\"},", "")
                           .replace(",","")
                    );
                }

                Stack<String> noSuchCourse = new Stack<>();
                f.forEachCourse(course -> {  // track which courses professor teaches
                    if (courses.containsKey(course)) 
                        courses.get(course).addFaculty(f.getId());
                    else 
                        noSuchCourse.push(course);
                });

                // remove courses that do not exist
                coursesRemoved += noSuchCourse.size();
                while (!noSuchCourse.empty()) 
                    f.removeCourse(noSuchCourse.pop());

                faculty.put(f.getId(), f);  // add professor to list of faculty
            }
        }
        catch (SQLException e) {
            System.out.println("FATAL ERROR: student table incorrectly formatted");
            e.printStackTrace();
        }
        catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Warning: Faculty "+name+" does not teach any classes" );
        } finally {
            try { statement.close(); } catch (Exception e) { /* Ignored */ }
            try { result.close(); } catch (Exception e) { /* Ignored */ }
        }

        if (coursesRemoved > 0)
            System.out.println(coursesRemoved+" nonexistent course(s) removed from faculty");

        return faculty;
    }

    public HashMap<String, Course> loadCourses() {
        HashMap<String, Course> courses = new HashMap<>();
        Statement statement = null;
        ResultSet result = null;
        
        try {
            statement = con.createStatement();
            result = statement.executeQuery("select * from Courses");

            while (result.next()) {  // while there are still results coming in                
                Course c = new Course(
                    result.getString(1),   // department
                    result.getString(2),   // code
                    result.getString(3),   // description
                    result.getInt(4),      // minStudents
                    result.getInt(5)       // maxStudents
                );

                courses.put(c.getId(), c);
            }
        }
        catch (SQLException e) {
            System.out.println("FATAL ERROR: student table incorrectly formatted");
            e.printStackTrace();
        } finally {
            try { statement.close(); } catch (Exception e) { /* Ignored */ }
            try { result.close(); } catch (Exception e) { /* Ignored */ }
        }

        return courses;
    }


    public Connection getCon() { return this.con; }
    public void setCon(Connection con) { this.con = con; }

    public String getDatabase() { return this.database; }
    public void setDatabase(String database) { this.database = database; }

    public String getUrl() { return this.url; }
    public void setUrl(String url) { this.url = url; }

    public String getUsername() { return this.username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return this.password; }
    public void setPassword(String password) { this.password = password; }
}