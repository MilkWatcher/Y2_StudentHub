// Last Updated: Reanielle Broas C00296913
// Description: Login GUI for Student Hub
// Status: COMPLETE
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class p1Login extends JFrame {
    public p1Login() {
        // window title + default close operation
        super("Login page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 500);

        // main panel w/boxlayout
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // padding

        mainPanel.add(Box.createRigidArea(new Dimension(0, 200))); // vertical spacing

        // title
        JLabel titleLabel = new JLabel("Login to Student Hub", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // center
        mainPanel.add(titleLabel);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 20))); // vertical spacing

        // email field
        // email label
        JLabel emailLabel = new JLabel("Enter your Email: ");
        emailLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(emailLabel);

        // email input
        JTextField emailInput = new JTextField(20);
        emailInput.setMaximumSize(new Dimension(250, 30));
        emailInput.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(emailInput);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 10))); // vertical spacing

        // password field
        // password label
        JLabel passwordLabel = new JLabel("Enter Password:");
        passwordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(passwordLabel);

        // password input
        JPasswordField passwordInput = new JPasswordField(20); // masks user input
        passwordInput.setMaximumSize(new Dimension(250, 30));
        passwordInput.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(passwordInput);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 20))); // vertical spacing

        // login field
        // login button gui
        JButton loginButton = new JButton("LOGIN");
        loginButton.setBackground(new Color(34, 139, 34));
        loginButton.setForeground(Color.WHITE);
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.setMaximumSize(new Dimension(100, 30)); // button size

        // login functionality
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = emailInput.getText().trim();
                String password = new String(passwordInput.getPassword()).trim();

                if (email.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(p1Login.this, "Email and password cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (authenticateUser(email, password)) {
                    String userType = DatabaseConnection.getUserType(email);
                    if (userType != null) {
                        JOptionPane.showMessageDialog(p1Login.this, "Login successful!");
                        new p2Main(userType); // Pass userType to the dashboard
                        dispose(); // Close the login window
                    } else {
                        JOptionPane.showMessageDialog(p1Login.this, "Unable to retrieve user type!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(p1Login.this, "Invalid email or password!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        mainPanel.add(loginButton);

        add(mainPanel, BorderLayout.CENTER);

        // make the window visible
        setVisible(true);
    }

    // method to validate user credentials from the database
    private boolean authenticateUser(String email, String password) {
        String query = "SELECT * FROM Users WHERE email = ? AND password = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            stmt.setString(2, password);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next(); // returns true if a user is found
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } catch (NullPointerException ex) {
            JOptionPane.showMessageDialog(this, "Database connection failed!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public static void main(String[] args) {
        // Run the GUI on the Event Dispatch Thread (EDT) for thread safety
        SwingUtilities.invokeLater(() -> new p1Login());
    }
}