import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL =
            "jdbc:mysql://localhost:3306/TrafficManagementDB";

    private static final String USER = "root";

    // Enter your MySQL password here
    private static final String PASSWORD = "ans@801806";

    public static Connection getConnection() throws SQLException {

        return DriverManager.getConnection(
                URL,
                USER,
                PASSWORD
        );
    }

    public static void main(String[] args) {

        try (Connection con = getConnection()) {

            System.out.println("MySQL Connected Successfully!");

        } catch (SQLException e) {

            System.out.println("MySQL Connection Failed!");

            e.printStackTrace();
        }
    }
}
