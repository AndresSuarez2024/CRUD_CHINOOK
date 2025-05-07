import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase que conecta a la base de datos PostgreSQL Chinook.
 * 
 * @author Andrés Suárez
 * @date 07-05-2025
 */

public class DbConnection {
    private static final String URL = "jdbc:postgresql://localhost:5432/chinook_v2";
    private static final String USER = "postgres";
    private static final String PASSWORD = "1234";

    /**
     * Establece y devuelve una conexión a la base de datos PostgreSQL.
     */
    
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}