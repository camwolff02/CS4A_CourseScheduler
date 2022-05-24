package src.java;

import java.util.ArrayList;
import java.util.HashMap;

import java.sql.Connection;     // connects you to the database
import java.sql.DriverManager;  // converts the database
import java.sql.ResultSet;      // resulf off a query
import java.sql.SQLException;   // for error throws
import java.sql.Statement;      // actual statement to send the sql server

public class LoadSQL  {
    private Connection con;
    private String database;
    private String url;
    private String username;
    private String password;
    
    public LoadSQL() {
        this("University");
    }

    public LoadSQL(String database) {
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
            System.out.println("Error: SQL connection to database unsuccessful");
            e.printStackTrace();
        }
    }

    /** loading data for different classes */
    public ArrayList<Student> loadStudents() {
        ArrayList<Student> students = new ArrayList<>();
        
        try {
            Statement statement = con.createStatement();
            ResultSet result = statement.executeQuery("select * from Students");

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
                
                var courseStr = result.getString(13);
                for (var str : courseStr.substring(1, courseStr.lastIndexOf('}') + 1).split("\"}")) {
                    s.addCourse(
                        str.replace("{\"department\":\"","")
                           .replace("\",\"code\":\"","")
                           .replace("\"},", "")
                           .replace(",","")
                    );
                }

                students.add(s);
            }
            
        }
        catch (SQLException e) {
            System.out.println("FATAL ERROR: student table incorrectly formatted");
            e.printStackTrace();
        }
        catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Warning: Student " + students.get(students.size() - 1).getName() +
                " has not enrolled in any classes" );
        }

        return students;
    }
    
    public ArrayList<Faculty> loadFaculty() {
        ArrayList<Faculty> faculty = new ArrayList<>();
        
        try {
            Statement statement = con.createStatement();
            ResultSet result = statement.executeQuery("select * from Faculty");

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

                var courseStr = result.getString(12);
                for (var str : courseStr.substring(1, courseStr.lastIndexOf('}') + 1).split("\"}")) {
                    f.addCourse(
                        str.replace("{\"department\":\"","")
                           .replace("\",\"code\":\"","")
                           .replace("\"},", "")
                           .replace(",","")
                    );
                }

                faculty.add(f);
            }
        }
        catch (SQLException e) {
            System.out.println("FATAL ERROR: student table incorrectly formatted");
            e.printStackTrace();
        }
        catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Warning: Faculty " + faculty.get(faculty.size() - 1).getName() +
                " does not teach any classes" );
        }

        return faculty;
    }

    public HashMap<String, Course> loadCourses() {
        HashMap<String, Course> courses = new HashMap<>();
        
        try {
            Statement statement = con.createStatement();
            ResultSet result = statement.executeQuery("select * from Courses");

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