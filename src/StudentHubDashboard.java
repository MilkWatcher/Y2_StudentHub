import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StudentHubDashboard extends JFrame {
    public StudentHubDashboard() {
        // Window setup
        setTitle("Student Hub Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 400);
        setLocationRelativeTo(null); // Center window
        setLayout(new BorderLayout());

        // Panel for title and user role
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(50, 0, 50, 0)); // Padding

        JLabel welcomeLabel = new JLabel("Welcome to Student Hub");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel userLabel = new JLabel("User: PROFESSOR");
        userLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        userLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        headerPanel.add(welcomeLabel);
        headerPanel.add(userLabel);
        
        // Panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 4, 20, 0)); // 4 buttons in a row with spacing

        // Buttons
        JButton addNotesButton = createNavButton("Add Notes", "p4AddNotes");
        JButton viewNotesButton = createNavButton("View Notes", "p4ViewNotes");
        JButton uploadGradesButton = createNavButton("Upload Grades", "p7UploadGrades");
        JButton viewTimetableButton = createNavButton("View Timetable", "p8ViewTimeTab");

        // Add buttons to panel
        buttonPanel.add(addNotesButton);
        buttonPanel.add(viewNotesButton);
        buttonPanel.add(uploadGradesButton);
        buttonPanel.add(viewTimetableButton);

        // Adding components to frame
        add(headerPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);

        // Make the window visible
        setVisible(true);
    }

    // Helper method to create buttons with action listeners
    private JButton createNavButton(String title, String className) {
        JButton button = new JButton(title);
        button.setBackground(Color.WHITE);
        button.setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));
        button.setFocusPainted(false);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openPage(className);
            }
        });
        return button;
    }

    // Open different pages based on the class name
    private void openPage(String className) {
        switch (className) {
            case "p4ViewNotes":
                new p4ViewNotes(); // Open View Notes GUI
                break;
            case "p4AddNotes":
                JOptionPane.showMessageDialog(this, "Navigating to: Add Notes");
                break;
            case "p7UploadGrades":
                JOptionPane.showMessageDialog(this, "Navigating to: Upload Grades");
                break;
            case "p8ViewTimeTab":
                JOptionPane.showMessageDialog(this, "Navigating to: View Timetable");
                break;
            default:
                JOptionPane.showMessageDialog(this, "Page not found!");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StudentHubDashboard());
    }
}
