// Last Updated: Reanielle Broas C00296913 @ 04-02-2025 1:45AM
// Description: Java for connecting database to sql
// Status: WIP

// src/DatabaseConnection.java



import java.sql.*;

public class DatabaseConnection {
    private static final String URL = "jdbc:sqlite:studenthub.db"; // Relative path to database

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    // method 4 adding user
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

    // method 4 adding a course
    public static void addCourse(String courseName, String professorID) throws SQLException {
        String query = "INSERT INTO Courses (courseName, professorID) VALUES (?, ?)";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, courseName);
            stmt.setString(2, professorID);
            stmt.executeUpdate();
        }
    }

    // method 4 adding a students grade in a course
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

    // method 4 adding notes to a course
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

    // method 4 adding a timetable
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

    public static void main(String[] args) {
        try {
            // this is sample/test data
            addUser("S001", "student1@example.com", "pass123", "Student");
            addUser("P001", "professor1@example.com", "prof456", "Professor");
            addCourse("Mathematics", "P001");
            addGrade("S001", 1, 88.5f, "2025-03-11");
            addNote(1, "P001", "Math Lecture 1", "Calculus Basics", "2025-03-11");
            addTimetable(1, "Monday", "09:00", "11:00", "Room 101");

            System.out.println("Sample data added successfully!!!");
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}