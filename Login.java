import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame {
    public Login() {
        // Window title and default close operation
        super("Login Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300); // Reasonable size for the login window

        // Use a single JPanel with BoxLayout to stack components vertically and center them
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS)); // Stack components vertically
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add padding around the panel

        // 1. JLabel for title at the top
        JLabel titleLabel = new JLabel("Login Page", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center horizontally
        mainPanel.add(titleLabel);

        // Add vertical spacing after the title
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // 2. Email field (changed from Username to match mockup)
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the label
        mainPanel.add(emailLabel);

        JTextField emailInput = new JTextField(20); // Wider field to match mockup
        emailInput.setMaximumSize(new Dimension(250, 30)); // Limit width and set height
        emailInput.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the text field
        mainPanel.add(emailInput);

        // Add vertical spacing between fields
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // 3. Password field
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the label
        mainPanel.add(passwordLabel);

        JPasswordField passwordInput = new JPasswordField(20); // Masks input for security
        passwordInput.setMaximumSize(new Dimension(250, 30)); // Match email field size
        passwordInput.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the password field
        mainPanel.add(passwordInput);

        // Add vertical spacing before the button
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // 4. Login button (green to match mockup)
        JButton loginButton = new JButton("LOGIN");
        loginButton.setBackground(new Color(34, 139, 34)); // Green color (RGB: 34, 139, 34)
        loginButton.setForeground(Color.WHITE); // White text for contrast
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the button
        loginButton.setMaximumSize(new Dimension(100, 30)); // Set button size

        // ActionListener for the button (placeholder for functionality)
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = emailInput.getText();
                String password = new String(passwordInput.getPassword());
                JOptionPane.showMessageDialog(Login.this, 
                    "Login attempt with Email: " + email + "\nPassword: " + password);
                // Add database validation here later (e.g., with DatabaseConnection)
            }
        });

        mainPanel.add(loginButton);

        // Add the main panel to the center of the frame
        add(mainPanel, BorderLayout.CENTER);

        // Make the window visible
        setVisible(true);
    }

    public static void main(String[] args) {
        // Run the GUI on the Event Dispatch Thread (EDT) for thread safety
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Login();
            }
        });
    }
}