package stdy;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIStudy extends JFrame {

    public GUIStudy() {
        // Set window title and default close operation
        super("GUIStudy");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);

        // Main layout for the frame uses BorderLayout
        setLayout(new BorderLayout());

        // 1. JLabel: Displays static text or icons
        JLabel titleLabel = new JLabel("StudentHub Dashboard", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16)); // Customize font
        add(titleLabel, BorderLayout.NORTH); // Add to top of window

        // 2. JPanel: Container for organizing components
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(3, 2, 10, 10)); // 3 rows, 2 columns, with spacing

        // 3. JTextField: Allows user to input text
        JLabel nameLabel = new JLabel("Student Name:");
        JTextField nameField = new JTextField(15); // 15 columns wide
        inputPanel.add(nameLabel);
        inputPanel.add(nameField);

        // 4. JComboBox: Dropdown menu for selecting one option
        JLabel courseLabel = new JLabel("Course:");
        String[] courses = {"Mathematics", "Physics", "Computer Science"};
        JComboBox<String> courseCombo = new JComboBox<>(courses);
        inputPanel.add(courseLabel);
        inputPanel.add(courseCombo);

        // 5. JCheckBox: Toggle option (on/off)
        JLabel notificationsLabel = new JLabel("Notifications:");
        JCheckBox notificationsCheck = new JCheckBox("Enable");
        inputPanel.add(notificationsLabel);
        inputPanel.add(notificationsCheck);

        // Add inputPanel to the center of the frame
        add(inputPanel, BorderLayout.CENTER);

        // 6. JList: List for selecting one or multiple items
        String[] gradeOptions = {"A", "B", "C", "D", "F"};
        JList<String> gradeList = new JList<>(gradeOptions);
        gradeList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION); // Allow multiple selections
        JScrollPane gradeScroll = new JScrollPane(gradeList); // Add scrollbar
        add(gradeScroll, BorderLayout.WEST); // Add to left side

        // 7. JTextArea: Multi-line text area for notes or output
        JTextArea notesArea = new JTextArea(5, 20); // 5 rows, 20 columns
        notesArea.setText("Enter notes here...");
        notesArea.setLineWrap(true); // Enable line wrapping
        notesArea.setWrapStyleWord(true); // Wrap at word boundaries
        JScrollPane notesScroll = new JScrollPane(notesArea); // Add scrollbar
        add(notesScroll, BorderLayout.EAST); // Add to right side

        // 8. JButton: Triggers an action when clicked
        JPanel buttonPanel = new JPanel(); // Another JPanel for buttons
        JButton submitButton = new JButton("Submit");
        JButton clearButton = new JButton("Clear");

        // ActionListener for Submit button: Displays entered data in JTextArea
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String course = (String) courseCombo.getSelectedItem();
                boolean notifications = notificationsCheck.isSelected();
                String grades = gradeList.getSelectedValuesList().toString();
                notesArea.setText("Name: " + name + "\nCourse: " + course + 
                                  "\nNotifications: " + notifications + 
                                  "\nGrades: " + grades);
            }
        });

        // ActionListener for Clear button: Resets fields
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nameField.setText("");
                courseCombo.setSelectedIndex(0);
                notificationsCheck.setSelected(false);
                gradeList.clearSelection();
                notesArea.setText("Enter notes here...");
            }
        });

        buttonPanel.add(submitButton);
        buttonPanel.add(clearButton);
        add(buttonPanel, BorderLayout.SOUTH); // Add buttons to bottom

        // Make the window visible
        setVisible(true);
    }

    public static void main(String[] args) {
        // Run the GUI on the Event Dispatch Thread (EDT) for thread safety
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GUIStudy();
            }
        });
    }
}