// Last Updated: Reanielle Broas C00296913
// Description: GUI for students to view their grades from a certain course
// Permissions: Student, Professor
// Status: WIP

/*
>student/professor inputs student ID
>system fetches the grade correlated to the student id
>grades are displayed

issues:
    student can access another students grades if they have their student id, no privacy pretty much
    however this is the best way to execute view grades so that professors can view grades of a certain student
 
notes:
    fix sql/databaseconnection.java so that studentid must exist
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class p6ViewGrades extends JFrame {
    private JTextField studentIDField;
    private JTextArea gradesArea;

    public p6ViewGrades() {
        // window setup
        setTitle("View Grades");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // input your student ID to see what grades correlate to you
        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Student ID:"));
        studentIDField = new JTextField(10);
        inputPanel.add(studentIDField);

        // view grades button
        JButton viewButton = new JButton("View Grades");
        inputPanel.add(viewButton);
        add(inputPanel, BorderLayout.NORTH);

        // grades panel
        gradesArea = new JTextArea();
        gradesArea.setEditable(false);
        add(new JScrollPane(gradesArea), BorderLayout.CENTER);

        viewButton.addActionListener(e -> fetchGrades());
        setVisible(true);
    }

    // viewing grades functionality (based on student id input)
    //  downsides to this:  student can access another students grades if they have their student id, no privacy pretty much
    //                      however this is the best way to execute view grades so that professors can view grades of a certain student
    private void fetchGrades() {
        String studentID = studentIDField.getText().trim();
        // ask student for id
        if (studentID.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter Student ID.");
            return;
        }
        try {
            String query = "SELECT * FROM Grades WHERE studentID = ?";
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, studentID);
                ResultSet rs = stmt.executeQuery();
                StringBuilder gradesText = new StringBuilder();
                while (rs.next()) {
                    gradesText.append("Course ID: ").append(rs.getInt("courseID"))
                              .append(", Grade: ").append(rs.getFloat("grade"))
                              .append(", Date: ").append(rs.getString("uploadDate"))
                              .append("\n");
                }
                gradesArea.setText(gradesText.length() > 0 ? gradesText.toString() : "No grades found."); // student has no grade
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching grades.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(p6ViewGrades::new);
    }
}

