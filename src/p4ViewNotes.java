// Last Updated: Reanielle Broas C00296913
// Description: GUI for viewing notes uploaded by professor
// Permissions: Student, Professor
// Status: COMPLETE

/*
>retrieve notes from database
>display notes to table (prob on like a list/array or somethin)
>open file
 */

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class p4ViewNotes extends JFrame {
    private DefaultListModel<String> listModel;
    private JList<String> notesList;

    public p4ViewNotes() {
        // window setup
        super("View Notes");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // header panel
        JLabel titleLabel = new JLabel("Available Notes", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(titleLabel, BorderLayout.NORTH);

        // list to display notes (their path directories)
        listModel = new DefaultListModel<>();
        notesList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(notesList);
        add(scrollPane, BorderLayout.CENTER);

        fetchNotes();

        // open file button
        JButton openButton = new JButton("Open Note");
        openButton.addActionListener(e -> openSelectedFile());

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(openButton);
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    // fetch notes from the database
    private void fetchNotes() {
        String query = "SELECT filePath FROM Notes";
        listModel.clear();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            boolean hasNotes = false;
            while (rs.next()) {
                listModel.addElement(rs.getString("filePath"));
                hasNotes = true;
            }

            if (!hasNotes) {
                listModel.addElement("No notes available.");
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error fetching notes from the database.", "Database Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    // method to open the selected file
    private void openSelectedFile() {
        String selectedNote = notesList.getSelectedValue();
        
        // if no notes available/invalid
        if (selectedNote == null || selectedNote.equals("No notes available.")) {
            JOptionPane.showMessageDialog(this, "Please select a valid note to open.");
            return;
        }

        // ensures selected note exists b4 attempting to open
        File file = new File(selectedNote); // creates new file object that represents notes path
        if (!file.exists()) { //file does not exist
            JOptionPane.showMessageDialog(this, "The selected file does not exist.", "File Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // if file cant be opened
        try {
            Desktop.getDesktop().open(file); // attempt to open file
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error opening file: " + file.getName(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new p4ViewNotes().setVisible(true));
    }
}
