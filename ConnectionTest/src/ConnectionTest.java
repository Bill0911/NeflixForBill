import java.sql.*;


public class ConnectionTest {
    public static void main(String[] args) {
        // jdbc:mariadb://<host>:<port>/<database>
        String url = "jdbc:mysql://localhost:3306/db_demo";
        String username = "root";
        String password = "";

        try {
            // Load and register the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connection established successfully!");

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from book");

            while (resultSet.next()) {
                System.out.println(resultSet.getInt(1) + " "+resultSet.getString(2)+" "+resultSet.getFloat(4));
            }

            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
