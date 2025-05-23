// Last Updated: Reanielle Broas C00296913
// Description: GUI for managing user permissions (student, professor, admin)
// Status: COMPLETE
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class p3ManageUserSys extends JFrame {
    private JTable usersTable;
    private DefaultTableModel tableModel;
    private JTextField userIDField, emailField, roleField;
    private JPasswordField passwordField;
    private JButton addButton, updateButton, deleteButton, refreshButton;

    public p3ManageUserSys() {
        setTitle("Manage Users");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10)); // Added spacing between components

        // table setup
        tableModel = new DefaultTableModel(new String[]{"User ID", "Email", "Role"}, 0);
        usersTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(usersTable);
        tableScrollPane.setBorder(BorderFactory.createTitledBorder("User List")); // Added a titled border
        add(tableScrollPane, BorderLayout.CENTER);

        // input panel
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder("User Details")); // Added a titled border
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Reduced spacing between components
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // input user id field
        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(new JLabel("User ID:"), gbc);
        gbc.gridx = 1;
        userIDField = new JTextField(20);
        inputPanel.add(userIDField, gbc);

        // input email field
        gbc.gridx = 0;
        gbc.gridy++;
        inputPanel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        emailField = new JTextField(20);
        inputPanel.add(emailField, gbc);

        // input password field
        gbc.gridx = 0;
        gbc.gridy++;
        inputPanel.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        passwordField = new JPasswordField(20);
        inputPanel.add(passwordField, gbc);

        // input role (usertype) field
        gbc.gridx = 0;
        gbc.gridy++;
        inputPanel.add(new JLabel("Role:"), gbc);
        gbc.gridx = 1;
        roleField = new JTextField(20);
        inputPanel.add(roleField, gbc);

        add(inputPanel, BorderLayout.NORTH);

        // button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        addButton = new JButton("Add User");
        updateButton = new JButton("Update User");
        deleteButton = new JButton("Delete User");
        refreshButton = new JButton("Refresh");

        // buttons
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // event listeners
        addButton.addActionListener(e -> addUser());
        updateButton.addActionListener(e -> updateUser());
        deleteButton.addActionListener(e -> deleteUser());
        refreshButton.addActionListener(e -> loadUsers());

        loadUsers();
        setVisible(true);
    }

    // functionality for viewing users
    private void loadUsers() {
        tableModel.setRowCount(0);
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT userID, email, role FROM Users");
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                tableModel.addRow(new Object[]{rs.getString("userID"), rs.getString("email"), rs.getString("role")});
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error loading users: " + ex.getMessage());
        }
    }

    // functionality for adding users
    private void addUser() {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO Users (userID, email, password, role) VALUES (?, ?, ?, ?)")) {

            stmt.setString(1, userIDField.getText());
            stmt.setString(2, emailField.getText());
            stmt.setString(3, new String(passwordField.getPassword()));
            stmt.setString(4, roleField.getText());
            stmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "User added successfully!");
            loadUsers();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error adding user: " + ex.getMessage());
        }
    }

    // functionality for editing users
    private void updateUser() {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("UPDATE Users SET email = ?, password = ?, role = ? WHERE userID = ?")) {

            stmt.setString(1, emailField.getText());
            stmt.setString(2, new String(passwordField.getPassword()));
            stmt.setString(3, roleField.getText());
            stmt.setString(4, userIDField.getText());
            stmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "User updated successfully!");
            loadUsers();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error updating user: " + ex.getMessage());
        }
    }

    // functionality for deleting users
    private void deleteUser() {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM Users WHERE userID = ?")) {

            stmt.setString(1, userIDField.getText());
            stmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "User deleted successfully!");
            loadUsers();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error deleting user: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new p3ManageUserSys().setVisible(true));
    }
}

