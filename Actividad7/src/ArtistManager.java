import java.sql.*;
import java.util.Scanner;

/**
 * Clase que gestiona las operaciones CRUD sobre los artistas en la base de datos Chinook.
 * @author Andrés Suárez
 * @date 07-05-2025
 */

public class ArtistManager {
    private Scanner scanner = new Scanner(System.in);

    /**
     * Consulta y muestra todos los artistas almacenados en la base de datos.
     */

    public void getAllArtists() {
        String query = "SELECT * FROM artist";

        try (Connection conn = DbConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            boolean hayResultados = false;
            while (rs.next()) {
                int id = rs.getInt("artist_id");
                String nombre = rs.getString("name");
                System.out.println("ID: " + id + ", NOMBRE: " + nombre);
                hayResultados = true;
            }

            if (!hayResultados) {
                System.out.println("No hay resultados para esta consulta.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

     /**
     * Busca y muestra los artistas cuyo nombre coincida con el nombre proporcionado por el usuario.
     * 
     * @param nombre Nombre del artista a buscar. Si el nombre tiene menos de 2 caracteres,
     *               muestra un mensaje indicando que debe introducirse al menos 2 caracteres.
     */

    public void getArtistsByName() {
        System.out.print("Introduce el nombre del artista: ");
        String nombre = scanner.nextLine();

        if (nombre.length() < 2) {
            System.out.println("Debes introducir al menos 2 caracteres.");
            return;
        }

        String query = "SELECT * FROM artist WHERE name ILIKE ?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, "%" + nombre + "%");
            ResultSet rs = stmt.executeQuery();

            boolean hayResultados = false;
            while (rs.next()) {
                int id = rs.getInt("artist_id");
                String nombreArtista = rs.getString("name");
                System.out.println("ID: " + id + ", NOMBRE: " + nombreArtista);
                hayResultados = true;
            }

            if (!hayResultados) {
                System.out.println("No hay resultados para esta consulta.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Consulta los primeros 5 álbumes de un artista cuyo nombre coincida con el nombre proporcionado
     * por el usuario.
     * 
     * @param nombre Nombre del artista para consultar los álbumes. Si el nombre tiene menos de 2 caracteres,
     *               muestra un mensaje indicando que debe introducirse al menos 2 caracteres.
     */

    public void getTopAlbumsByArtist() {
        System.out.print("Introduce el nombre del artista: ");
        String nombre = scanner.nextLine();

        if (nombre.length() < 2) {
            System.out.println("Debes introducir al menos 2 caracteres.");
            return;
        }

        String query = """
            SELECT album.album_id, album.title, artist.name 
            FROM album 
            JOIN artist ON album.artist_id = artist.artist_id 
            WHERE artist.name ILIKE ? 
            ORDER BY album.album_id 
            LIMIT 5
            """;

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, nombre + "%");
            ResultSet rs = stmt.executeQuery();

            boolean hayResultados = false;
            while (rs.next()) {
                int idAlbum = rs.getInt("album_id");
                String nombreAlbum = rs.getString("title");
                String nombreArtista = rs.getString("name");
                System.out.println("ID_ALBUM: " + idAlbum + ", NOMBRE_ALBUM: " + nombreAlbum + ", NOMBRE_ARTISTA: " + nombreArtista);
                hayResultados = true;
            }

            if (!hayResultados) {
                System.out.println("No hay resultados para esta consulta.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Agrega un nuevo artista a la base de datos.
     * 
     * @param nombre Nombre del nuevo artista a agregar. Si el nombre tiene menos de 2 caracteres,
     *               muestra un mensaje indicando que debe introducirse al menos 2 caracteres.
     */

    public void addArtist() {
        System.out.print("Introduce el nombre del nuevo artista: ");
        String nombre = scanner.nextLine();

        if (nombre.length() < 2) {
            System.out.println("Debes introducir al menos 2 caracteres.");
            return;
        }

        String query = "INSERT INTO artist (name) VALUES (?)";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, nombre);
            int filas = stmt.executeUpdate();

            if (filas > 0) {
                System.out.println("El artista se ha añadido correctamente.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Modifica el nombre de un artista existente en la base de datos.
     * 
     * @param id ID del artista a modificar.
     * @param nuevoNombre Nuevo nombre del artista.
     *                   Si el nombre tiene menos de 2 caracteres, muestra un mensaje indicando
     *                   que debe introducirse al menos 2 caracteres.
     */

    public void updateArtist() {
        System.out.print("Introduce la ID del artista a modificar: ");
        String idTexto = scanner.nextLine();
        int id;

        try {
            id = Integer.parseInt(idTexto);
        } catch (NumberFormatException e) {
            System.out.println("La ID debe ser un número.");
            return;
        }

        System.out.print("Introduce el nuevo nombre del artista: ");
        String nuevoNombre = scanner.nextLine();

        if (nuevoNombre.length() < 2) {
            System.out.println("Debes introducir al menos 2 caracteres.");
            return;
        }

        String query = "UPDATE artist SET name = ? WHERE artist_id = ?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, nuevoNombre);
            stmt.setInt(2, id);
            int filas = stmt.executeUpdate();

            if (filas > 0) {
                System.out.println("El artista se ha modificado correctamente.");
            } else {
                System.out.println("No se encontró ningún artista con esa ID.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Elimina un artista de la base de datos utilizando su ID.
     * 
     * @param id ID del artista a eliminar. Si la ID no es un número, muestra un mensaje indicando
     *           que la ID debe ser un número.
     */

    public void deleteArtist() {
        System.out.print("Introduce la ID del artista a eliminar: ");
        String idTexto = scanner.nextLine();
        int id;

        try {
            id = Integer.parseInt(idTexto);
        } catch (NumberFormatException e) {
            System.out.println("La ID debe ser un número.");
            return;
        }

        String query = "DELETE FROM artist WHERE artist_id = ?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            int filas = stmt.executeUpdate();

            if (filas > 0) {
                System.out.println("El artista se ha borrado correctamente.");
            } else {
                System.out.println("No se encontró ningún artista con esa ID.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
