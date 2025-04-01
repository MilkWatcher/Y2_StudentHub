// Last Updated: Reanielle Broas C00296913 @ 04-02-2025 1:45AM
// Description: Java for connecting database to SQL along with all the CRUD code for each page
// Status: COMPLETE

import java.sql.*;

public class DatabaseConnection {
    private static final String URL = "jdbc:sqlite:studenthub.db"; // Relative path to database

    // Method to establish a connection to the database
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    // CRUD for users
        // add user
        public static void addUser(String userID, String email, String password, String role) throws SQLException {
            String query = "INSERT INTO Users (userID, email, password, role) VALUES (?, ?, ?, ?)";
            try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, userID);
                stmt.setString(2, email);
                stmt.setString(3, password);
                stmt.setString(4, role);
                stmt.executeUpdate();
            }
        }

        // update user
        public static void updateUser(String userID, String email, String password, String role) throws SQLException {
            String query = "UPDATE Users SET email = ?, password = ?, role = ? WHERE userID = ?";
            try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, email);
                stmt.setString(2, password);
                stmt.setString(3, role);
                stmt.setString(4, userID);
                stmt.executeUpdate();
            }
        }
    
        // delete user
        public static void deleteUser(String userID) throws SQLException {
            String query = "DELETE FROM Users WHERE userID = ?";
            try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, userID);
                stmt.executeUpdate();
            }
        }


    // CRUD for course
        // add course
        public static void addCourse(String courseName, String professorID) throws SQLException {
            String query = "INSERT INTO Courses (courseName, professorID) VALUES (?, ?)";
            try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, courseName);
                stmt.setString(2, professorID);
                stmt.executeUpdate();
            }
        }

        // view course
        public static ResultSet getCourses() throws SQLException {
            String query = "SELECT * FROM Courses";
            Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            return stmt.executeQuery();
        }
        
        // update course
        public static void updateCourse(int courseID, String courseName, String professorID) throws SQLException {
            String query = "UPDATE Courses SET courseName = ?, professorID = ? WHERE courseID = ?";
            try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, courseName);
                stmt.setString(2, professorID);
                stmt.setInt(3, courseID);
                stmt.executeUpdate();
            }
        }
        


    // CRUD for grades
        // add grade
        public static void addGrade(String studentID, int courseID, float grade, String uploadDate) throws SQLException {
            String query = "INSERT INTO Grades (studentID, courseID, grade, uploadDate) VALUES (?, ?, ?, ?)";
            try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, studentID);
                stmt.setInt(2, courseID);
                stmt.setFloat(3, grade);
                stmt.setString(4, uploadDate);
                stmt.executeUpdate();
            }
        }

        // view grade
        public static void viewGrades(String studentID) throws SQLException {
            String query = "SELECT * FROM Grades WHERE studentID = ?";
            try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, studentID);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    System.out.println("Course ID: " + rs.getInt("courseID") + ", Grade: " + rs.getFloat("grade") + ", Date: " + rs.getString("uploadDate"));
                }
            }
        }

    // CRUD for notes into a course
    public static void addNote(int courseID, String professorID, String title, String content, String uploadDate) throws SQLException {
        String query = "INSERT INTO Notes (courseID, professorID, title, content, uploadDate) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, courseID);
            stmt.setString(2, professorID);
            stmt.setString(3, title);
            stmt.setString(4, content);
            stmt.setString(5, uploadDate);
            stmt.executeUpdate();
        }
    }

    // CRUD for timetable
        // add timetable
        public static void addTimetable(int courseID, String day, String startTime, String endTime, String location) throws SQLException {
            String query = "INSERT INTO Timetable (courseID, day, startTime, endTime, location) VALUES (?, ?, ?, ?, ?)";
            try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, courseID);
                stmt.setString(2, day);
                stmt.setString(3, startTime);
                stmt.setString(4, endTime);
                stmt.setString(5, location);
                stmt.executeUpdate();
            }
        }

        // view timetable
        public static ResultSet viewTimetable(int courseID) throws SQLException {
            String query = "SELECT * FROM Timetable WHERE courseID = ?";
            Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, courseID);
            return stmt.executeQuery();
        }
        

    // retrieve the usertype to be sent into dashboard
    public static String getUserType(String username) {
        String userType = null;
        String query = "SELECT role FROM Users WHERE email = ?";

        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                userType = resultSet.getString("role");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userType;
    }

   //verify login credentials and return user role
    public static String loginUser(String email, String password) {
        String userType = null;
        String query = "SELECT role FROM Users WHERE email = ? AND password = ?";

        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                userType = resultSet.getString("role");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userType;
    }

    // login functionality
    public static void main(String[] args) {
        String email = "professor1@example.com";
        String password = "prof456";

        String userType = loginUser(email, password);
        if (userType != null) {
            System.out.println("Login successful! User type: " + userType);
            new p2Main(userType);
        } else {
            System.out.println("Invalid login credentials!");
        }
    }
}

/*
    emails:                         passwords:
    student@email.com       ~~      pass123
    professor@email.com     ~~      prof456
    admin@email.com         ~~      admin789
*/