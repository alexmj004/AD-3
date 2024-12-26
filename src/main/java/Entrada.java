import dao.CocheDAO;
import dao.PasajeroDAO;
import database.ConnectionDB;
import model.Coche;
import model.Pasajero;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Entrada {
    static Scanner scan = new Scanner(System.in);
    private static CocheDAO cocheDAO = new CocheDAO();
    private static PasajeroDAO pasajeroDAO = new PasajeroDAO();

    public static void main(String[] args) {
        int opcionMenu;

        do{
            System.out.println("""
                1. Añadir nuevo coche.
                2. Borrar coche por ID.
                3. Consulta coche por ID.
                4. Modificar coche por ID.
                5. Listado de coches.
                6. Gestionar pasajeros.
                7. Terminar el programa.
                
                Elige una de las opciones.
                
                """);
            opcionMenu = scan.nextInt();

            switch (opcionMenu) {
                case 1:
                    insertarCoche();
                    break;
                case 2:
                    borrarCoche();
                    break;
                case 3:
                    consultarCoche();
                    break;
                case 4:
                    modificarCoche();
                    break;
                case 5:
                    listarCoches();
                    break;
                case 6:
                    int opcionSubMenu;
                    System.out.println("""
                            1. Añadir nuevo pasajero.
                            2. Borrar pasajero por ID.
                            3. Consulta pasajero por ID.
                            4. Listar todos los pasajeros por ID.
                            5. Añadir pasajero a coche.
                            6. Eliminar pasajero de un coche.
                            7. Listar todos los pasajeros de un coche.
                            
                            Elige una de las opciones.
                            
                            """);
                    opcionSubMenu = scan.nextInt();
                    switch (opcionSubMenu) {
                        case 1: insertarPasajero();
                            break;
                        case 2: borrarPasajero();
                            break;
                        case 3: consultarPasajero();
                            break;
                        case 4:listaPasajeros();
                            break;
                        case 5: añadirPasajeroACoche();
                            break;
                        case 6: eliminarPasajeroDeCoche();
                            break;
                        case 7: listarPasajerosDeCoche();
                            break;
                        default:
                            System.out.println("OPCIÓN NO VÁLIDA EN EL SUBMENÚ.");

                    }
                    break;
                default:
                    System.out.println("OPCIÓN NO VÁLIDA EN EL MENÚ.");
            }
        }while(opcionMenu != 7);

        // Cerramos conexión con la bbdd.
        new ConnectionDB().closeConnection();
    }


    // Métodos menú.
    public static void insertarCoche(){
        System.out.println("MATRICULA:");
        String matricula = scan.next();
        System.out.println("MARCA:");
        String marca = scan.next();
        System.out.println("MODELO:");
        String modelo = scan.next();
        System.out.println("COLOR:");
        String color = scan.next();

        try {
            cocheDAO.añadirCoche(new Coche(matricula,marca,modelo,color));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public static void borrarCoche(){

        try {
            System.out.println("ID DEL COCHE A BORRAR:");
            scan.nextLine();
            int id = scan.nextInt();
            cocheDAO.borrarCoche(id);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public static void consultarCoche(){
        System.out.println("ID DEL COCHE A CONSULTAR:");
        int id = scan.nextInt();
        try {
            cocheDAO.consultarCoche(id);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public static void modificarCoche(){
        System.out.println("ID COCHE ACTUALIZAR:");
        int id = scan.nextInt();
        scan.nextLine();
        System.out.println("NUEVO COLOR COCHE:");
        String color = scan.next();
        try {
            cocheDAO.modificarCoche(id,color);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public static void listarCoches(){
        try {
            cocheDAO.listarCoches();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Método submenú.
    public static void insertarPasajero(){
        System.out.println("NOMBRE PASAJERO:");
        String nombre=scan.next();
        System.out.println("EDAD PASAJERO:");
        int edad=scan.nextInt();
        System.out.println("PESO PASAJERO:");
        double peso=scan.nextDouble();

        try {
            pasajeroDAO.añadirPasajero(new Pasajero(nombre,edad,peso));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public static void borrarPasajero(){
        System.out.println("ID COCHE A BORRAR:");
        int id = scan.nextInt();
        try {
            pasajeroDAO.borrarPasajero(id);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public static void consultarPasajero(){
        System.out.println("ID COCHE A CONSULTAR:");
        int id = scan.nextInt();
        try {
            pasajeroDAO.consultarPasajero(id);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public static void listaPasajeros(){
        try {
            for(Pasajero pas : pasajeroDAO.listarPasajeros()){
                System.out.println(pas.toString());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public static void añadirPasajeroACoche() {
        try {
            // Listar todos los coches disponibles
            System.out.println("Coches disponibles:");
            for (Coche coche : cocheDAO.listarCoches()) {
                System.out.println("ID: " + coche.getId() + " - " + coche.getMatricula() + " " + coche.getMarca() + " " + coche.getModelo());
            }

            // Solicitar el ID del coche y el pasajero
            System.out.println("Introduce el ID del coche al que quieres añadir un pasajero:");
            int cocheId = scan.nextInt();

            System.out.println("Introduce el ID del pasajero que deseas añadir al coche:");
            int pasajeroId = scan.nextInt();

            // Llamar al método para asociar el pasajero al coche
            cocheDAO.asociarPasajeroACoche(cocheId, pasajeroId);
            System.out.println("Pasajero añadido al coche con éxito.");

        } catch (SQLException e) {
            System.out.println("Error al añadir pasajero al coche: " + e.getMessage());
        }
    }
    public static void eliminarPasajeroDeCoche() {
        try {
            // Listar todos los coches con los pasajeros asociados
            System.out.println("Coches y pasajeros asociados:");
            for (Coche coche : cocheDAO.listarCoches()) {
                System.out.println("ID Coche: " + coche.getId() + " - " + coche.getMatricula() + " " + coche.getMarca() + " " + coche.getModelo());

                // Mostrar los pasajeros si están asociados al coche
                if (!coche.getPasajeros().isEmpty()) {
                    for (Pasajero pasajero : coche.getPasajeros()) {
                        System.out.println("  Pasajero: " + pasajero.getNombre() + " (ID: " + pasajero.getId() + ")");
                    }
                } else {
                    System.out.println("  No hay pasajeros asociados.");
                }
            }

            // Solicitar el ID del coche y el pasajero a eliminar
            System.out.println("Introduce el ID del coche del que quieres eliminar un pasajero:");
            int cocheId = scan.nextInt();

            System.out.println("Introduce el ID del pasajero que deseas eliminar del coche:");
            int pasajeroId = scan.nextInt();

            // Llamar al método para eliminar al pasajero del coche
            cocheDAO.eliminarPasajeroDeCoche(cocheId, pasajeroId);
            System.out.println("Pasajero eliminado del coche con éxito.");

        } catch (SQLException e) {
            System.out.println("Error al eliminar pasajero del coche: " + e.getMessage());
        }
    }
    public static void listarPasajerosDeCoche(){
        System.out.println("ID COCHE:");
        int id = scan.nextInt();

        try {
            // Para cada idPasajero de la lista, consultamos la información del pasajero.
            for(int idPasajero: cocheDAO.listarIdPasajeros(id)){
                pasajeroDAO.consultarPasajero(idPasajero);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }



}
