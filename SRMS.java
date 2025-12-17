import java.awt.*;
import java.sql.*;
import javax.swing.*;

public class SRMS extends JFrame {
    private static final long serialVersionUID = 1L;

    JTextField id, name, age, dti, javaM, ddca;
    JTextArea output;

    @SuppressWarnings("this-escape")
    public SRMS() {
        setTitle("Student Record Management");
        setSize(600, 600);
        setLayout(new FlowLayout());

        id = new JTextField(10);
        name = new JTextField(10);
        age = new JTextField(10);
        dti = new JTextField(10);
        javaM = new JTextField(10);
        ddca = new JTextField(10);

        add(new JLabel("ID"));
        add(id);
        add(new JLabel("Name"));
        add(name);
        add(new JLabel("Age"));
        add(age);
        add(new JLabel("DTI"));
        add(dti);
        add(new JLabel("Java"));
        add(javaM);
        add(new JLabel("DDCA"));
        add(ddca);

        JButton add = new JButton("Add");

        JButton delete = new JButton("Delete");

        add(add);
        add(delete);

        output = new JTextArea(20, 50);
        add(new JScrollPane(output));

        add.addActionListener(e -> addStudent());

        delete.addActionListener(e -> deleteStudent());

        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    void addStudent() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/studentrecordmanagementsystem",
                    "root",
                    "Sriram@123");

            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO students VALUES (?, ?, ?, ?, ?, ?)");

            ps.setLong(1, Long.parseLong(id.getText()));
            ps.setString(2, name.getText());
            ps.setInt(3, Integer.parseInt(age.getText()));
            ps.setInt(4, Integer.parseInt(dti.getText()));
            ps.setInt(5, Integer.parseInt(javaM.getText()));
            ps.setInt(6, Integer.parseInt(ddca.getText()));

            ps.executeUpdate();
            con.close();

            output.setText("Student Added Successfully\n");

        } catch (Exception ex) {
            output.setText("Error: " + ex.getMessage());
        }
    }

    void deleteStudent() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/studentrecordmanagementsystem",
                    "root",
                    "Sriram@123");

            PreparedStatement ps = con.prepareStatement(
                    "DELETE FROM students WHERE id=?");

            ps.setLong(1, Long.parseLong(id.getText()));
            ps.executeUpdate();

            con.close();
            output.setText("Student Deleted\n");

        } catch (Exception ex) {
            output.setText("Error: " + ex.getMessage());
        }
    }
}
