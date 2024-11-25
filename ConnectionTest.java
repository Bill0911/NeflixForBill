import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionTest {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/testdbpc";
        // Leave username and password empty
        String username = "root";
        String password = "";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            System.out.println("Connection successful!");
        } catch (Exception e) {
            System.err.println("Connection failed: " + e.getMessage());
        }
    }
}