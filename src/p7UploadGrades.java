// Last Updated: Reanielle Broas C00296913
// Description: GUI for a professor to upload their grades
// Permissions: EXEC Professor
// Status: COMPLETE

/*
>Professor inputs:
    - student id
    - course id
    - grade (0-100)
>validate data
    >student id and course id exist
    >grade is between 0 and 100
>upload grade to database for view grades
 */

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.time.LocalDate;

public class p7UploadGrades extends JFrame {
    private JTextField studentIDField, courseIDField, gradeField;

    public p7UploadGrades() {
        // window setup
        setTitle("Upload Grades");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 2, 10, 10));
        
        // input fields
            // input student id
            add(new JLabel("Student ID:"));
            studentIDField = new JTextField();
            add(studentIDField);

            // input course id
            add(new JLabel("Course ID:"));
            courseIDField = new JTextField();
            add(courseIDField);

            // input grade
            add(new JLabel("Grade (0-100):"));
            gradeField = new JTextField();
            add(gradeField);

        // upload button
        JButton uploadButton = new JButton("Upload Grade");
        uploadButton.addActionListener(e -> uploadGrade());
        add(uploadButton);

        setVisible(true);
    }

    private void uploadGrade() {
        String studentID = studentIDField.getText().trim();
        String courseID = courseIDField.getText().trim();
        String gradeText = gradeField.getText().trim();
        if (studentID.isEmpty() || courseID.isEmpty() || gradeText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields must be filled out.");
            return;
        }
        try {
            int course = Integer.parseInt(courseID);
            float grade = Float.parseFloat(gradeText);
            if (grade < 0 || grade > 100) {
                JOptionPane.showMessageDialog(this, "Grade must be between 0 and 100.");
                return;
            }
            String uploadDate = LocalDate.now().toString();
            DatabaseConnection.addGrade(studentID, course, grade, uploadDate);
            JOptionPane.showMessageDialog(this, "Grade uploaded successfully.");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid input for Course ID or Grade.");
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error uploading grade.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new p7UploadGrades().setVisible(true));
    }
}