import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {

    public static Connection getConnection() throws Exception {
        String url = "jdbc:mysql://localhost:3306/studentrecordmanagementsystem";
        String user = "root"; // change if needed
        String password = "Sriram@123"; // change if needed

        return DriverManager.getConnection(url, user, password);
    }
}
