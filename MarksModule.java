import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.io.*;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

public class MarksModule extends JFrame {

    JTextField txtId, txtName, txtAge, txtDti, txtJava, txtDdca;
    JTextField txtProjectName, txtProjectMarks, txtSeminarMarks;
    JTextField txtSearch;

    JTable table;
    DefaultTableModel model;

    JButton btnAdd, btnUpdate, btnDelete, btnSearch, btnExcel, btnPDF;

    public MarksModule() {

        setTitle("Marks Module");
        setSize(1050, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);

        // ---------- INPUT FIELDS ----------
        addLabel("ID", 20, 20);
        txtId = addField(120, 20);
        addLabel("Name", 20, 60);
        txtName = addField(120, 60);
        addLabel("Age", 20, 100);
        txtAge = addField(120, 100);
        addLabel("DTI", 20, 140);
        txtDti = addField(120, 140);
        addLabel("Java", 20, 180);
        txtJava = addField(120, 180);
        addLabel("DDCA", 20, 220);
        txtDdca = addField(120, 220);

        addLabel("Project", 350, 20);
        txtProjectName = addField(480, 20);
        addLabel("Proj Marks", 350, 60);
        txtProjectMarks = addField(480, 60);
        addLabel("Seminar", 350, 100);
        txtSeminarMarks = addField(480, 100);

        // ---------- BUTTONS ----------
        btnAdd = new JButton("Add");
        btnAdd.setBounds(350, 150, 100, 30);
        add(btnAdd);

        btnUpdate = new JButton("Update");
        btnUpdate.setBounds(460, 150, 100, 30);
        add(btnUpdate);

        btnDelete = new JButton("Delete");
        btnDelete.setBounds(570, 150, 100, 30);
        add(btnDelete);

        btnExcel = new JButton("Export Excel");
        btnExcel.setBounds(680, 150, 130, 30);
        add(btnExcel);

        btnPDF = new JButton("Export PDF");
        btnPDF.setBounds(820, 150, 130, 30);
        add(btnPDF);

        addLabel("Search ID", 350, 200);
        txtSearch = new JTextField();
        txtSearch.setBounds(480, 200, 120, 25);
        add(txtSearch);

        btnSearch = new JButton("Search");
        btnSearch.setBounds(610, 200, 100, 25);
        add(btnSearch);

        // ---------- TABLE ----------
        model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[] {
                "ID", "Name", "Age", "DTI", "Java", "DDCA",
                "Project", "Project Marks", "Seminar Marks"
        });

        table = new JTable(model);
        JScrollPane sp = new JScrollPane(table);
        sp.setBounds(20, 260, 1000, 280);
        add(sp);

        // 🔥 CLICK ROW → LOAD DATA INTO FIELDS
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {

                int row = table.getSelectedRow();
                if (row == -1)
                    return;

                txtId.setText(model.getValueAt(row, 0).toString());
                txtName.setText(model.getValueAt(row, 1).toString());
                txtAge.setText(model.getValueAt(row, 2).toString());
                txtDti.setText(model.getValueAt(row, 3).toString());
                txtJava.setText(model.getValueAt(row, 4).toString());
                txtDdca.setText(model.getValueAt(row, 5).toString());
                txtProjectName.setText(model.getValueAt(row, 6).toString());
                txtProjectMarks.setText(model.getValueAt(row, 7).toString());
                txtSeminarMarks.setText(model.getValueAt(row, 8).toString());
            }
        });

        // ---------- ACTIONS ----------
        btnAdd.addActionListener(e -> addRecord());
        btnUpdate.addActionListener(e -> updateRecord());
        btnDelete.addActionListener(e -> deleteRecord());
        btnSearch.addActionListener(e -> searchRecord());
        btnExcel.addActionListener(e -> exportExcel());
        btnPDF.addActionListener(e -> exportPDF());

        loadDataFromDB();
    }

    // ================= CRUD METHODS =================

    void addRecord() {
        if (!validateNumericFields())
            return;

        try {
            Connection con = DatabaseConnection.getConnection();
            String sql = "INSERT INTO marks VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, Long.parseLong(txtId.getText().trim()));
            ps.setString(2, txtName.getText());
            ps.setInt(3, Integer.parseInt(txtAge.getText()));
            ps.setInt(4, Integer.parseInt(txtDti.getText()));
            ps.setInt(5, Integer.parseInt(txtJava.getText()));
            ps.setInt(6, Integer.parseInt(txtDdca.getText()));
            ps.setString(7, txtProjectName.getText());
            ps.setInt(8, Integer.parseInt(txtProjectMarks.getText()));
            ps.setInt(9, Integer.parseInt(txtSeminarMarks.getText()));

            ps.executeUpdate();
            con.close();

            JOptionPane.showMessageDialog(this, "Record added");
            clearFields();
            loadDataFromDB();

        } catch (SQLIntegrityConstraintViolationException ex) {
            JOptionPane.showMessageDialog(this, "ID already exists");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    void updateRecord() {
        if (txtId.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Select a record first");
            return;
        }
        if (!validateNumericFields())
            return;

        try {
            Connection con = DatabaseConnection.getConnection();
            String sql = "UPDATE marks SET name=?, age=?, dti=?, java=?, ddca=?, " +
                    "project_name=?, project_marks=?, seminar_marks=? WHERE id=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, txtName.getText());
            ps.setInt(2, Integer.parseInt(txtAge.getText()));
            ps.setInt(3, Integer.parseInt(txtDti.getText()));
            ps.setInt(4, Integer.parseInt(txtJava.getText()));
            ps.setInt(5, Integer.parseInt(txtDdca.getText()));
            ps.setString(6, txtProjectName.getText());
            ps.setInt(7, Integer.parseInt(txtProjectMarks.getText()));
            ps.setInt(8, Integer.parseInt(txtSeminarMarks.getText()));
            ps.setLong(9, Long.parseLong(txtId.getText()));

            ps.executeUpdate();
            con.close();

            JOptionPane.showMessageDialog(this, "Record updated");
            loadDataFromDB();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    void deleteRecord() {
        if (txtId.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Select a record first");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                this, "Delete this record?", "Confirm",
                JOptionPane.YES_NO_OPTION);

        if (confirm != JOptionPane.YES_OPTION)
            return;

        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("DELETE FROM marks WHERE id=?");

            ps.setLong(1, Long.parseLong(txtId.getText()));
            ps.executeUpdate();
            con.close();

            JOptionPane.showMessageDialog(this, "Record deleted");
            clearFields();
            loadDataFromDB();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    void searchRecord() {
        if (txtSearch.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter ID to search");
            return;
        }

        model.setRowCount(0);

        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM marks WHERE id=?");

            ps.setLong(1, Long.parseLong(txtSearch.getText()));
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                model.addRow(new Object[] {
                        rs.getLong("id"), rs.getString("name"), rs.getInt("age"),
                        rs.getInt("dti"), rs.getInt("java"), rs.getInt("ddca"),
                        rs.getString("project_name"),
                        rs.getInt("project_marks"),
                        rs.getInt("seminar_marks")
                });
            } else {
                JOptionPane.showMessageDialog(this, "Record not found");
                loadDataFromDB();
            }
            con.close();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    void loadDataFromDB() {
        model.setRowCount(0);
        try {
            Connection con = DatabaseConnection.getConnection();
            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM marks");

            while (rs.next()) {
                model.addRow(new Object[] {
                        rs.getLong("id"), rs.getString("name"), rs.getInt("age"),
                        rs.getInt("dti"), rs.getInt("java"), rs.getInt("ddca"),
                        rs.getString("project_name"),
                        rs.getInt("project_marks"),
                        rs.getInt("seminar_marks")
                });
            }
            con.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // ================= EXPORT =================

    void exportExcel() {
        try {
            Workbook wb = new XSSFWorkbook();
            Sheet sheet = wb.createSheet("Marks");

            Row header = sheet.createRow(0);
            for (int i = 0; i < model.getColumnCount(); i++)
                header.createCell(i).setCellValue(model.getColumnName(i));

            for (int i = 0; i < model.getRowCount(); i++) {
                Row row = sheet.createRow(i + 1);
                for (int j = 0; j < model.getColumnCount(); j++)
                    row.createCell(j).setCellValue(model.getValueAt(i, j).toString());
            }

            JFileChooser fc = new JFileChooser();
            fc.setSelectedFile(new File("Marks.xlsx"));
            if (fc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                wb.write(new FileOutputStream(fc.getSelectedFile()));
                wb.close();
                JOptionPane.showMessageDialog(this, "Excel exported");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    void exportPDF() {
        try {
            Document doc = new Document();
            JFileChooser fc = new JFileChooser();
            fc.setSelectedFile(new File("Marks.pdf"));

            if (fc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                PdfWriter.getInstance(doc,
                        new FileOutputStream(fc.getSelectedFile()));
                doc.open();

                PdfPTable pdfTable = new PdfPTable(model.getColumnCount());
                for (int i = 0; i < model.getColumnCount(); i++)
                    pdfTable.addCell(model.getColumnName(i));

                for (int i = 0; i < model.getRowCount(); i++)
                    for (int j = 0; j < model.getColumnCount(); j++)
                        pdfTable.addCell(model.getValueAt(i, j).toString());

                doc.add(pdfTable);
                doc.close();
                JOptionPane.showMessageDialog(this, "PDF exported");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    // ================= HELPERS =================

    boolean validateNumericFields() {
        try {
            Long.parseLong(txtId.getText().trim());
            Integer.parseInt(txtAge.getText().trim());
            Integer.parseInt(txtDti.getText().trim());
            Integer.parseInt(txtJava.getText().trim());
            Integer.parseInt(txtDdca.getText().trim());
            Integer.parseInt(txtProjectMarks.getText().trim());
            Integer.parseInt(txtSeminarMarks.getText().trim());
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Numeric fields must contain numbers");
            return false;
        }
    }

    void clearFields() {
        txtId.setText("");
        txtName.setText("");
        txtAge.setText("");
        txtDti.setText("");
        txtJava.setText("");
        txtDdca.setText("");
        txtProjectName.setText("");
        txtProjectMarks.setText("");
        txtSeminarMarks.setText("");
    }

    void addLabel(String t, int x, int y) {
        JLabel l = new JLabel(t);
        l.setBounds(x, y, 120, 25);
        add(l);
    }

    JTextField addField(int x, int y) {
        JTextField tf = new JTextField();
        tf.setBounds(x, y, 180, 25);
        add(tf);
        return tf;
    }
}
