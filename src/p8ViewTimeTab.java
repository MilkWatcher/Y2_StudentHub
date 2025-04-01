// Last Updated: Reanielle Broas C00296913
// Description: GUI for students and professors to view their timetable
// Permissions: Students, Professors
// Status: WIP

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class p8ViewTimeTab extends JFrame {
    private JTextField courseIDField;
    private JButton viewButton;
    private JTable timetableTable;
    private DefaultTableModel tableModel;

    public p8ViewTimeTab() {
        // window setup
        setTitle("View Timetable");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // input panel for entering course id
        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Course ID:"));
        courseIDField = new JTextField(10);
        inputPanel.add(courseIDField);

        // view time table button
        viewButton = new JButton("View Timetable");
        inputPanel.add(viewButton);
        add(inputPanel, BorderLayout.NORTH);

        // table for showing the timetable
        tableModel = new DefaultTableModel(new String[]{"Day", "Start Time", "End Time", "Location"}, 0);
        /*
        DAY     Start Time  End Time    Location
        MONDAY  12:00       13:00       Room101
         */
        timetableTable = new JTable(tableModel);
        add(new JScrollPane(timetableTable), BorderLayout.CENTER);
        viewButton.addActionListener(e -> loadTimetable());

    }

    private void loadTimetable() {
        tableModel.setRowCount(0); // clear previous data

        // handling invalid input
        int courseID;
        try {
            courseID = Integer.parseInt(courseIDField.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid Course ID (numeric value).", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // get database connection (must close after use)
        try (Connection conn = DatabaseConnection.getConnection(); 
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Timetable WHERE courseID = ?")) {

            stmt.setInt(1, courseID);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    tableModel.addRow(new Object[]{
                        rs.getString("day"),
                        rs.getString("startTime"),
                        rs.getString("endTime"),
                        rs.getString("location")
                    });
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error retrieving timetable: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    
        public static void main(String[] args) {
            SwingUtilities.invokeLater(() -> new p8ViewTimeTab().setVisible(true));
        }
    }