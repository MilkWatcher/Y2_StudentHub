// Last Updated: Reanielle Broas C00296913
// Description: GUI for viewing notes uploaded by professor
// Permissions: Student, Professor
// Status: WIP
import javax.swing.*;
import java.awt.*;

public class p4ViewNotes extends JFrame {
    public p4ViewNotes() {
        // Window setup
        super("View Notes");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 500);

        // Main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Padding

        mainPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Vertical padding

        // Title
        JLabel welcomeLabel = new JLabel("View Notes Page", JLabel.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(welcomeLabel);

        // Sample notes display (placeholder)
        JTextArea notesArea = new JTextArea(10, 50);
        notesArea.setEditable(false);
        notesArea.setText("Your saved notes will appear here...");

        // Scroll pane for notes
        JScrollPane scrollPane = new JScrollPane(notesArea);
        mainPanel.add(scrollPane);

        // Add panel to frame
        add(mainPanel);

        // Make the frame visible
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new p4ViewNotes());
    }
}


