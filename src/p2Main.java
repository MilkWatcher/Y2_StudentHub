// Last Updated: Reanielle Broas C00296913
// Description: Main Dashboard for Student Hub
// Status: COMPLETE

/*
 SETUP:
 [main
    [header]
    [button]
            main]
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class p2Main extends JFrame {
    public p2Main(String userType) {
        super("Student Hub Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        headerPanel.setBackground(new Color(50, 50, 50));

        JLabel welcomeLabel = new JLabel("Welcome to Student Hub!", JLabel.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Larger, bolder font
        welcomeLabel.setForeground(Color.WHITE); // White text for contrast
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center-align horizontally
        headerPanel.add(welcomeLabel);

        JLabel userLabel = new JLabel("User Type: " + userType, JLabel.CENTER);
        userLabel.setFont(new Font("Arial", Font.ITALIC, 16)); // Italic for style
        userLabel.setForeground(new Color(230, 230, 230)); // Light gray text
        userLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center-align horizontally
        headerPanel.add(userLabel);

        add(headerPanel, BorderLayout.NORTH);

        // Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 3, 20, 20)); // Updated to fit 3 rows & 3 columns
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        // Buttons
        JButton manageUserButton = createNavButton("Manage Users (Admin Only)", "p3ManageUserSys");
        JButton viewNotesButton = createNavButton("View Notes", "p4ViewNotes");
        JButton addNotesButton = createNavButton("Upload Notes (Professor Only)", "p5UploadNotes");
        JButton viewGradesButton = createNavButton("View Grades", "p6ViewGrades");
        JButton uploadGradesButton = createNavButton("Upload Grades (Professor Only)", "p7UploadGrades");
        JButton viewTimeTabButton = createNavButton("View Timetable", "p8ViewTimeTab");
        JButton uploadTimeTabButton = createNavButton("Upload Timetable (Professor Only)", "p9UploadTimeTab");

        // Disable buttons based on userType
        if (userType.equalsIgnoreCase("Student")) {
            manageUserButton.setEnabled(false);
            addNotesButton.setEnabled(false);
            uploadGradesButton.setEnabled(false);
            uploadTimeTabButton.setEnabled(false);
        } else if (userType.equalsIgnoreCase("Professor")) {
            manageUserButton.setEnabled(false);
        }

        // Adding buttons to panel
        buttonPanel.add(manageUserButton);
        buttonPanel.add(viewNotesButton);
        buttonPanel.add(addNotesButton);
        buttonPanel.add(viewGradesButton);
        buttonPanel.add(uploadGradesButton);
        buttonPanel.add(viewTimeTabButton);
        buttonPanel.add(uploadTimeTabButton);

        add(buttonPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    private JButton createNavButton(String title, String className) {
        JButton button = new JButton(title);
        button.setFocusPainted(false);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openPage(className);
            }
        });
        return button;
    }

    private void openPage(String className) {
        switch (className) {
            case "p3ManageUserSys":
                new p3ManageUserSys();
                break;
            case "p4ViewNotes":
                new p4ViewNotes();
                break;
            case "p5UploadNotes":
                new p5UploadNotes(className);
                break;
            case "p6ViewGrades":
                new p6ViewGrades();
                break;
            case "p7UploadGrades":
                new p7UploadGrades();
                break;
            case "p8ViewTimeTab":
                new p8ViewTimeTab();
                break;
            case "p9UploadTimeTab":
                new p9UploadTimeTab();
                break;
            default:
                JOptionPane.showMessageDialog(this, "Page not found!");
                setVisible(true); // reopen if an invalid page was clicked
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new p2Main("PROFESSOR")); // example role
    }
}
