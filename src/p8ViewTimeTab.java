// Last Updated: Reanielle Broas C00296913
// Description: GUI for students and professors to view their timetable
// Permissions: Students, Professors
// Status: WIP

import javax.swing.JFrame;

public class p8ViewTimeTab extends JFrame{
    public p8ViewTimeTab() {
        setTitle("View Timetable");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Closes only this window
        setVisible(true);
    }
}