import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionTest {
    public static void main(String[] args) {
        // jdbc:mariadb://<host>:<port>/<database>
        String url = "jdbc:mariadb://localhost:3306/testdbpc";
        String username = "root";
        String password = "";      

        try {
            // Load and register the JDBC driver
            Class.forName("org.mariadb.jdbc.Driver");

            Connection connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connection established successfully!");


            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
