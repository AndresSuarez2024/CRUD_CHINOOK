import java.util.Scanner;

/**
 * Clase principal que gestiona las operaciones CRUD sobre los artistas en la base de datos Chinook. 
 * Utilizando un menú interactivo
 * @author Andrés Suárez
 * @date 07-05-2025
 */

public class Main {
    public static void main(String[] args) {
        ArtistManager manager = new ArtistManager();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nMenú Principal:");
            System.out.println("1. Consultar todos los artistas");
            System.out.println("2. Consultar artistas por su nombre");
            System.out.println("3. Consultar los 5 primeros álbumes por nombre del artista");
            System.out.println("4. Añadir un artista");
            System.out.println("5. Modificar el nombre de un artista");
            System.out.println("6. Borrar un artista");
            System.out.println("7. Salir");
            System.out.print("Selecciona una opción: ");

            String opcion = scanner.nextLine();

            switch (opcion) {
                case "1":
                    manager.getAllArtists();
                    break;
                case "2":
                    manager.getArtistsByName();
                    break;
                case "3":
                    manager.getTopAlbumsByArtist();
                    break;
                case "4":
                    manager.addArtist();
                    break;
                case "5":
                    manager.updateArtist();
                    break;
                case "6":
                    manager.deleteArtist();
                    break;
                case "7":
                    System.out.println("Saliendo..");
                    scanner.close();
                    return;
                default:
                    System.out.println("Opción no válida");
            }
        }
    }
}
