// Last Updated: Reanielle Broas C00296913
// Description: GUI for uploading notes for students to view
// Permissions: EXEC Professor
// Status: WIP
// Reference: https://youtu.be/YZ_tQFTMYoQ?feature=shared

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class p5UploadNotes extends JFrame {
    public p5UploadNotes(String professorID) {
        // window setup
        super("Upload Notes");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800,500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // header panel
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

            // title 
            JLabel titleLabel = new JLabel("Upload Notes", JLabel.CENTER);
            titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
            titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            headerPanel.add(titleLabel);

            // instructions
            JLabel instructionLabel = new JLabel("Select a file to upload (Supported formats: PDF, DOCX, TXT)", JLabel.CENTER);
            instructionLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            instructionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            headerPanel.add(instructionLabel);

        add(headerPanel, BorderLayout.NORTH);

        // main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

            // file chooser
            JTextField filePathField = new JTextField(30);
            filePathField.setEditable(false);
            filePathField.setMaximumSize(new Dimension(400, 30));
            JButton browseButton = new JButton("Browse");

            // file chooser functionality
            browseButton.addActionListener(e -> {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    filePathField.setText(selectedFile.getAbsolutePath());
                }
            });

            JPanel fileChooserPanel = new JPanel();
            fileChooserPanel.add(filePathField);
            fileChooserPanel.add(browseButton);
            mainPanel.add(fileChooserPanel);

            // upload button
            JButton uploadButton = new JButton("Upload");
            uploadButton.setBackground(new Color(34, 139, 34));
            uploadButton.setForeground(Color.WHITE);

            // upload button functionality
            uploadButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String filePath = filePathField.getText();
                    if (filePath.isEmpty()) {
                        JOptionPane.showMessageDialog(p5UploadNotes.this, "Please select a file to upload.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // validate file format
                    if (!isSupportedFormat(filePath)) {
                        JOptionPane.showMessageDialog(p5UploadNotes.this, "Unsupported file format. Please upload a PDF, DOCX, or TXT file.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // upload file to database
                    if (uploadNotesToDatabase(professorID, filePath)) {
                        JOptionPane.showMessageDialog(p5UploadNotes.this, "Notes uploaded successfully!");
                        filePathField.setText(""); // clear the file path field
                    } else {
                        JOptionPane.showMessageDialog(p5UploadNotes.this, "Failed to upload notes. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });

            mainPanel.add(Box.createRigidArea(new Dimension(0, 20))); // vertical spacing
            mainPanel.add(uploadButton);

        add(mainPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    // method to validate if the file is in a valid file format
    private boolean isSupportedFormat(String filePath) {
        String[] supportedFormats = {".pdf", ".docx", ".txt"}; // array of supported formats
        for (String format : supportedFormats) {
            if (filePath.toLowerCase().endsWith(format)) { // if filename.pdf/txt/docx
                return true;
            }
        }
        return false; // not supported
    }

    // method to upload notes to the database (for viewing)
    private boolean uploadNotesToDatabase(String professorID, String filePath) {
        String query = "INSERT INTO Notes (professorID, filePath, uploadDate) VALUES (?, ?, datetime('now'))";
        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, professorID);
                stmt.setString(2, filePath);
                stmt.executeUpdate();
                return true;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new p5UploadNotes("P001").setVisible(true)); // example professorID
    }
}

