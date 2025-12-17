import javax.swing.*;
import java.awt.*;

public class SRMSDashboard extends JFrame {

    public SRMSDashboard(String userId) {

        setTitle("SRMS Dashboard");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // Header
        JLabel lblWelcome = new JLabel("Welcome " + userId);
        lblWelcome.setFont(new Font("Arial", Font.BOLD, 18));
        lblWelcome.setBounds(350, 20, 300, 30);
        add(lblWelcome);

        // Sidebar
        JPanel sidebar = new JPanel();
        sidebar.setLayout(null);
        sidebar.setBackground(new Color(30, 136, 229));
        sidebar.setBounds(0, 0, 200, 600);
        add(sidebar);

        JButton btnMarks = new JButton("Marks Module");
        btnMarks.setBounds(20, 100, 160, 40);
        sidebar.add(btnMarks);

        // ✅ WORKING ACTION LISTENER
        btnMarks.addActionListener(e -> {
            new MarksModule().setVisible(true);
        });
    }
}
