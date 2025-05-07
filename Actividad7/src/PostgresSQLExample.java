import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

public class PostgresSQLExample {
    public static void main(String[] args) {
        String urls="jdbc:postgresql://localhost:5432/chinook_v2";
        String usuari="postgres";
        String password="1234";

        try(Connection connection = DriverManager.getConnection(urls, usuari, password)){
            Statement statement=connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM artist");

            while (resultSet.next()) {
                int id=resultSet.getInt("artist_id");
                String name = resultSet.getString("name");
                System.out.println("ID: " + id + ", Nom: " + name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}