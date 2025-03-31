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

         // header panel
         JPanel headerPanel = new JPanel();
         headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
         headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
 
         JLabel welcomeLabel = new JLabel("Welcome to Student Hub!", JLabel.CENTER);
         welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));
         headerPanel.add(welcomeLabel);
 
         JLabel userLabel = new JLabel("User Type: " + userType, JLabel.CENTER);
         userLabel.setFont(new Font("Arial", Font.PLAIN, 16));
         headerPanel.add(userLabel);
 
         add(headerPanel, BorderLayout.NORTH);
 
         // button panel
         JPanel buttonPanel = new JPanel();
         buttonPanel.setLayout(new GridLayout(2, 2, 20, 20));
         buttonPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
 
         JButton manageUserButton = createNavButton("Manage Users (Admin Only)", "p3ManageUserSys");
         JButton viewNotesButton = createNavButton("View Notes", "p4ViewNotes");
         JButton addNotesButton = createNavButton("Upload Notes (Professor Only)", "p5UploadNotes");
         JButton viewGradesButton = createNavButton("View Grades", "p6ViewGrades");
 
         // Disable buttons based on userType
         if (userType.equalsIgnoreCase("Student")) {
             manageUserButton.setEnabled(false);
             addNotesButton.setEnabled(false);
         } else if (userType.equalsIgnoreCase("Professor")) {
             manageUserButton.setEnabled(false);
         }
 
         buttonPanel.add(manageUserButton);
         buttonPanel.add(viewNotesButton);
         buttonPanel.add(addNotesButton);
         buttonPanel.add(viewGradesButton);
 
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
                JOptionPane.showMessageDialog(this, "Navigating to: Upload Notes");
                break;
            case "p6ViewGrades":
                new p6ViewGrades();
                break;
            default:
                JOptionPane.showMessageDialog(this, "Page not found!");
                setVisible(true); // Reopen if an invalid page was clicked
        }
    }
 
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new p2Main("PROFESSOR")); // Example role
    }
 }
 