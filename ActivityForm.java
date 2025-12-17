import javax.swing.*;

public class ActivityForm extends JFrame {

    JTextField txtTitle, txtDate;
    JTextArea txtDesc;

    public ActivityForm(String activityName) {

        setTitle(activityName + " Details");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setLayout(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JLabel lblHeading = new JLabel(activityName + " Entry Form");
        lblHeading.setBounds(150, 20, 300, 25);
        add(lblHeading);

        JLabel lblTitle = new JLabel("Title:");
        lblTitle.setBounds(40, 70, 100, 25);
        add(lblTitle);

        txtTitle = new JTextField();
        txtTitle.setBounds(140, 70, 250, 25);
        add(txtTitle);

        JLabel lblDate = new JLabel("Date:");
        lblDate.setBounds(40, 110, 100, 25);
        add(lblDate);

        txtDate = new JTextField();
        txtDate.setBounds(140, 110, 250, 25);
        add(txtDate);

        JLabel lblDesc = new JLabel("Description:");
        lblDesc.setBounds(40, 150, 100, 25);
        add(lblDesc);

        txtDesc = new JTextArea();
        JScrollPane sp = new JScrollPane(txtDesc);
        sp.setBounds(140, 150, 250, 100);
        add(sp);

        JButton btnSave = new JButton("Save");
        btnSave.setBounds(180, 280, 100, 30);
        add(btnSave);

        btnSave.addActionListener(e -> saveData());
    }

    private void saveData() {
        if (txtTitle.getText().isEmpty() ||
                txtDate.getText().isEmpty() ||
                txtDesc.getText().isEmpty()) {

            JOptionPane.showMessageDialog(this,
                    "Please fill all fields");
            return;
        }

        JOptionPane.showMessageDialog(this,
                "Details Saved Successfully");
        dispose();
    }
}
