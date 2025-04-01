// Last Updated: Reanielle Broas C00296913
// Description: GUI for uploading timetable
// Permissions: EXEC Professor
// Status: WIP

/*
>professor inputs details (course id, times, day, location)
>inputs are validated
>details are put into the database for view timetable

notes:
course id will correlate to a student and managed in manage user sys (by admin)

 */

import javax.swing.JFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class p9UploadTimeTab extends JFrame {
    private JTextField courseIDField, dayField, startTimeField, endTimeField, locationField;
    private JButton uploadButton;

    public p9UploadTimeTab() {
        setTitle("Upload Timetable");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(6, 2));

        // Input fields
        add(new JLabel("Course ID:"));
        courseIDField = new JTextField();
        add(courseIDField);

        add(new JLabel("Day:"));
        dayField = new JTextField();
        add(dayField);

        add(new JLabel("Start Time (HH:MM):"));
        startTimeField = new JTextField();
        add(startTimeField);

        add(new JLabel("End Time (HH:MM):"));
        endTimeField = new JTextField();
        add(endTimeField);

        add(new JLabel("Location:"));
        locationField = new JTextField();
        add(locationField);

        // Upload button
        uploadButton = new JButton("Upload Timetable");
        uploadButton.addActionListener(this::uploadTimetable);
        add(uploadButton);
    }

    private void uploadTimetable(ActionEvent e) {
        try {
            int courseID = Integer.parseInt(courseIDField.getText());
            String day = dayField.getText();
            String startTime = startTimeField.getText();
            String endTime = endTimeField.getText();
            String location = locationField.getText();

            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement("INSERT INTO Timetable (courseID, day, startTime, endTime, location) VALUES (?, ?, ?, ?, ?)")
            ) {
                stmt.setInt(1, courseID);
                stmt.setString(2, day);
                stmt.setString(3, startTime);
                stmt.setString(4, endTime);
                stmt.setString(5, location);
                stmt.executeUpdate();
                JOptionPane.showMessageDialog(this, "Timetable uploaded successfully.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid Course ID.", "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new p9UploadTimeTab().setVisible(true));
    }
}


