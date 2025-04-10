// Last Updated: Reanielle Broas C00296913
// Description: GUI for uploading timetable
// Permissions: EXEC Professor
// Status: COMPLETE

/*
>professor inputs details (course id, times, day, location)
>inputs are validated
>details are put into the database for view timetable

notes:
course id will correlate to a student and managed in manage user sys (by admin)

 */

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
        // window setup
        setTitle("Upload Timetable");
        setSize(500, 400); // Increased window size for better spacing
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window
        setLayout(new BorderLayout(10, 10)); // Add spacing between components

        // Main panel
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add padding around the panel
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Add spacing between components
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Dimension inputBoxSize = new Dimension(250, 30); // Set preferred size for input boxes

        // Input fields
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(new JLabel("Course ID:"), gbc);
        gbc.gridx = 1;
        courseIDField = new JTextField();
        courseIDField.setPreferredSize(inputBoxSize);
        mainPanel.add(courseIDField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        mainPanel.add(new JLabel("Day:"), gbc);
        gbc.gridx = 1;
        dayField = new JTextField();
        dayField.setPreferredSize(inputBoxSize);
        mainPanel.add(dayField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        mainPanel.add(new JLabel("Start Time (HH:MM):"), gbc);
        gbc.gridx = 1;
        startTimeField = new JTextField();
        startTimeField.setPreferredSize(inputBoxSize);
        mainPanel.add(startTimeField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        mainPanel.add(new JLabel("End Time (HH:MM):"), gbc);
        gbc.gridx = 1;
        endTimeField = new JTextField();
        endTimeField.setPreferredSize(inputBoxSize);
        mainPanel.add(endTimeField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        mainPanel.add(new JLabel("Location:"), gbc);
        gbc.gridx = 1;
        locationField = new JTextField();
        locationField.setPreferredSize(inputBoxSize);
        mainPanel.add(locationField, gbc);

        // Add main panel to the frame
        add(mainPanel, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        uploadButton = new JButton("Upload Timetable");
        uploadButton.addActionListener(this::uploadTimetable);
        buttonPanel.add(uploadButton);
        uploadButton.setBackground(new Color(34, 139, 34));
        uploadButton.setForeground(Color.WHITE);
        uploadButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        uploadButton.setMaximumSize(new Dimension(100, 30)); // button size

        // Add button panel to the frame
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    // functionality for uploading timetable
    private void uploadTimetable(ActionEvent e) {
        try {
            int courseID = Integer.parseInt(courseIDField.getText());
            String day = dayField.getText().trim();
            String startTime = startTimeField.getText().trim();
            String endTime = endTimeField.getText().trim();
            String location = locationField.getText().trim();

            // Validate day
            String[] validDays = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
            boolean isValidDay = false;
            for (String validDay : validDays) {
                if (validDay.equalsIgnoreCase(day)) {
                    isValidDay = true;
                    break;
                }
            }
            if (!isValidDay) {
                JOptionPane.showMessageDialog(this, "Invalid day. Please enter a valid day (e.g., Monday, Tuesday).", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validate time format
            if (!isValidTime(startTime)) {
                JOptionPane.showMessageDialog(this, "Invalid start time. Please enter a valid time in HH:MM format (e.g., 09:30).", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!isValidTime(endTime)) {
                JOptionPane.showMessageDialog(this, "Invalid end time. Please enter a valid time in HH:MM format (e.g., 17:45).", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Insert into database
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement("INSERT INTO Timetable (courseID, day, startTime, endTime, location) VALUES (?, ?, ?, ?, ?)")) {
                stmt.setInt(1, courseID);
                stmt.setString(2, day);
                stmt.setString(3, startTime);
                stmt.setString(4, endTime);
                stmt.setString(5, location);
                stmt.executeUpdate();
                JOptionPane.showMessageDialog(this, "Timetable uploaded successfully.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid Course ID. Please enter a numeric value.", "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Helper method to validate time in HH:MM format
    private boolean isValidTime(String time) {
        if (time.matches("\\d{2}:\\d{2}")) {
            String[] parts = time.split(":");
            int hours = Integer.parseInt(parts[0]);
            int minutes = Integer.parseInt(parts[1]);
            return hours >= 0 && hours <= 23 && minutes >= 0 && minutes <= 59;
        }
        return false;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new p9UploadTimeTab().setVisible(true));
    }
}


