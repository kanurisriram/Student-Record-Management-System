import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class SRMSLogin extends JFrame {

    JTextField txtUser;
    JPasswordField txtPass;
    JButton btnLogin;

    public SRMSLogin() {
        setTitle("SRMS Login");
        setSize(420, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        getContentPane().setBackground(new Color(245, 247, 250));

        JPanel card = new JPanel();
        card.setPreferredSize(new Dimension(320, 380));
        card.setBackground(Color.WHITE);
        card.setLayout(new GridBagLayout());
        card.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 10, 10, 10);
        c.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("Student Record System", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setForeground(new Color(33, 37, 41));
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        card.add(title, c);

        JLabel sub = new JLabel("Sign in to continue", JLabel.CENTER);
        sub.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        sub.setForeground(Color.GRAY);
        c.gridy = 1;
        card.add(sub, c);

        c.gridwidth = 2;
        c.gridy = 2;
        card.add(new JLabel("Username"), c);

        txtUser = new JTextField();
        txtUser.setPreferredSize(new Dimension(250, 35));
        c.gridy = 3;
        card.add(txtUser, c);

        c.gridy = 4;
        card.add(new JLabel("Password"), c);

        txtPass = new JPasswordField();
        txtPass.setPreferredSize(new Dimension(250, 35));
        c.gridy = 5;
        card.add(txtPass, c);

        btnLogin = new JButton("Login");
        btnLogin.setBackground(new Color(13, 110, 253));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFocusPainted(false);
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnLogin.setPreferredSize(new Dimension(250, 40));
        c.gridy = 6;
        card.add(btnLogin, c);

        JLabel footer = new JLabel("© SRMS 2025", JLabel.CENTER);
        footer.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        footer.setForeground(Color.GRAY);
        c.gridy = 7;
        card.add(footer, c);

        add(card);

        btnLogin.addActionListener(e -> login());

        setVisible(true);
    }

    private void login() {
        String user = txtUser.getText();
        String pass = new String(txtPass.getPassword());

        if (user.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter username and password");
            return;
        }

        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement pst = con.prepareStatement(
                    "SELECT * FROM users WHERE username=? AND password=?");
            pst.setString(1, user);
            pst.setString(2, pass);

            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                new SRMSDashboard(user).setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials");
            }
            con.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage());
            // Fallback for testing
            if (user.equals("admin") && pass.equals("admin")) {
                new SRMSDashboard(user).setVisible(true);
                dispose();
            }
        }
    }

    public static void main(String[] args) {
        new SRMSLogin();
    }
}
