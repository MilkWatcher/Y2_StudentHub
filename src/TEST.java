import org.junit.*; // JUnit 4 annotations
import java.sql.*;
import static org.junit.Assert.*; // JUnit 4 assertions

public class DatabaseConnectionTest {

    private static Connection connection;

    @BeforeClass
    public static void setupDatabase() throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite::memory:");
        try (Statement stmt = connection.createStatement()) {
            // Create tables for testing
            stmt.execute("CREATE TABLE Users (userID TEXT PRIMARY KEY, email TEXT, password TEXT, role TEXT)");
            stmt.execute("CREATE TABLE Courses (courseID INTEGER PRIMARY KEY AUTOINCREMENT, courseName TEXT, professorID TEXT)");
            stmt.execute("CREATE TABLE Grades (gradeID INTEGER PRIMARY KEY AUTOINCREMENT, studentID TEXT, courseID INTEGER, grade REAL, uploadDate TEXT)");
            stmt.execute("CREATE TABLE Notes (noteID INTEGER PRIMARY KEY AUTOINCREMENT, courseID INTEGER, professorID TEXT, filePath TEXT, content TEXT, uploadDate TEXT)");
            stmt.execute("CREATE TABLE Timetable (timetableID INTEGER PRIMARY KEY AUTOINCREMENT, courseID INTEGER, day TEXT, startTime TEXT, endTime TEXT, location TEXT)");
        }
    }

    @AfterClass
    public static void tearDownDatabase() throws SQLException {
        connection.close();
    }

    @Test
    public void testAddUser() throws SQLException {
        DatabaseConnection.addUser("U001", "testuser@example.com", "password123", "student");
        try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Users WHERE userID = ?")) {
            stmt.setString(1, "U001");
            ResultSet rs = stmt.executeQuery();
            assertTrue(rs.next());
            assertEquals("testuser@example.com", rs.getString("email"));
        }
    }

    @Test
    public void testUpdateUser() throws SQLException {
        DatabaseConnection.addUser("U002", "updateuser@example.com", "password123", "student");
        DatabaseConnection.updateUser("U002", "updateduser@example.com", "newpassword123", "admin");
        try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Users WHERE userID = ?")) {
            stmt.setString(1, "U002");
            ResultSet rs = stmt.executeQuery();
            assertTrue(rs.next());
            assertEquals("updateduser@example.com", rs.getString("email"));
            assertEquals("admin", rs.getString("role"));
        }
    }

    @Test
    public void testDeleteUser() throws SQLException {
        DatabaseConnection.addUser("U003", "deleteuser@example.com", "password123", "student");
        DatabaseConnection.deleteUser("U003");
        try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Users WHERE userID = ?")) {
            stmt.setString(1, "U003");
            ResultSet rs = stmt.executeQuery();
            assertFalse(rs.next());
        }
    }

    @Test
    public void testAddCourse() throws SQLException {
        DatabaseConnection.addCourse("Math 101", "P001");
        try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Courses WHERE courseName = ?")) {
            stmt.setString(1, "Math 101");
            ResultSet rs = stmt.executeQuery();
            assertTrue(rs.next());
            assertEquals("P001", rs.getString("professorID"));
        }
    }

    @Test
    public void testUpdateCourse() throws SQLException {
        DatabaseConnection.addCourse("Science 101", "P002");
        try (PreparedStatement stmt = connection.prepareStatement("SELECT courseID FROM Courses WHERE courseName = ?")) {
            stmt.setString(1, "Science 101");
            ResultSet rs = stmt.executeQuery();
            rs.next();
            int courseID = rs.getInt("courseID");

            DatabaseConnection.updateCourse(courseID, "Advanced Science 101", "P003");
            try (PreparedStatement stmt2 = connection.prepareStatement("SELECT * FROM Courses WHERE courseID = ?")) {
                stmt2.setInt(1, courseID);
                ResultSet rs2 = stmt2.executeQuery();
                assertTrue(rs2.next());
                assertEquals("Advanced Science 101", rs2.getString("courseName"));
                assertEquals("P003", rs2.getString("professorID"));
            }
        }
    }

    @Test
    public void testDeleteCourse() throws SQLException {
        DatabaseConnection.addCourse("History 101", "P004");
        try (PreparedStatement stmt = connection.prepareStatement("SELECT courseID FROM Courses WHERE courseName = ?")) {
            stmt.setString(1, "History 101");
            ResultSet rs = stmt.executeQuery();
            rs.next();
            int courseID = rs.getInt("courseID");

            DatabaseConnection.deleteCourse(courseID);
            try (PreparedStatement stmt2 = connection.prepareStatement("SELECT * FROM Courses WHERE courseID = ?")) {
                stmt2.setInt(1, courseID);
                ResultSet rs2 = stmt2.executeQuery();
                assertFalse(rs2.next());
            }
        }
    }

    @Test
    public void testAddGrade() throws SQLException {
        DatabaseConnection.addGrade("S001", 1, 95.5f, "2025-04-02");
        try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Grades WHERE studentID = ?")) {
            stmt.setString(1, "S001");
            ResultSet rs = stmt.executeQuery();
            assertTrue(rs.next());
            assertEquals(95.5f, rs.getFloat("grade"));
        }
    }

    @Test
    public void testUpdateGrade() throws SQLException {
        DatabaseConnection.addGrade("S002", 2, 85.0f, "2025-04-02");
        try (PreparedStatement stmt = connection.prepareStatement("SELECT gradeID FROM Grades WHERE studentID = ?")) {
            stmt.setString(1, "S002");
            ResultSet rs = stmt.executeQuery();
            rs.next();
            int gradeID = rs.getInt("gradeID");

            DatabaseConnection.updateGrade(gradeID, 90.0f);
            try (PreparedStatement stmt2 = connection.prepareStatement("SELECT * FROM Grades WHERE gradeID = ?")) {
                stmt2.setInt(1, gradeID);
                ResultSet rs2 = stmt2.executeQuery();
                assertTrue(rs2.next());
                assertEquals(90.0f, rs2.getFloat("grade"));
            }
        }
    }

    @Test
    public void testDeleteGrade() throws SQLException {
        DatabaseConnection.addGrade("S003", 3, 75.0f, "2025-04-02");
        try (PreparedStatement stmt = connection.prepareStatement("SELECT gradeID FROM Grades WHERE studentID = ?")) {
            stmt.setString(1, "S003");
            ResultSet rs = stmt.executeQuery();
            rs.next();
            int gradeID = rs.getInt("gradeID");

            DatabaseConnection.deleteGrade(gradeID);
            try (PreparedStatement stmt2 = connection.prepareStatement("SELECT * FROM Grades WHERE gradeID = ?")) {
                stmt2.setInt(1, gradeID);
                ResultSet rs2 = stmt2.executeQuery();
                assertFalse(rs2.next());
            }
        }
    }

    @Test
    public void testLoginUser() throws SQLException {
        DatabaseConnection.addUser("U004", "loginuser@example.com", "securepassword", "admin");
        String userType = DatabaseConnection.loginUser("loginuser@example.com", "securepassword");
        assertEquals("admin", userType);

        String invalidLogin = DatabaseConnection.loginUser("loginuser@example.com", "wrongpassword");
        assertNull(invalidLogin);
    }
}