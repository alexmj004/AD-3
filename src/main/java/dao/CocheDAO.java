package dao;

import database.ConnectionDB;
import database.SchemaDB;
import model.Coche;
import model.Pasajero;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CocheDAO{

    private List<Coche> listadoCoches = new ArrayList<>();

    // Necesitamos tener la conexión para poder trabajar con la bbdd mysql.
    private Connection connection;
    // Necesitamos un objeto de la clase PreparedStatement para poder llevar a cabo las querys.
    private PreparedStatement ps;
    // Necesitamos un objeto de la clase ResultSet para poder trabajar con las querys de selección.
    private ResultSet rs;

    // Añadir coche.
    public void añadirCoche(Coche coche) throws SQLException {
        // Obtenemos la conexión, declarada en la clase ConnectionDB.
        connection = new ConnectionDB().getConnection();
        // Query de insercción.
        String query = "INSERT INTO %s (%s,%s,%s,%s) VALUES (?,?,?,?)";
        // Formateamos.
        ps = connection.prepareStatement(String.format(query,
                SchemaDB.table_coches,
                SchemaDB.coches_matricula,SchemaDB.coches_marca,SchemaDB.coches_modelo,SchemaDB.coches_color
                ));
        // Asignamos valores a los parámetros.
        ps.setString(1,coche.getMatricula());
        ps.setString(2,coche.getMarca());
        ps.setString(3,coche.getModelo());
        ps.setString(4,coche.getColor());
        // Ejecutamos query.
        ps.execute();
        System.out.println("COCHE INSERTADO EN LA BBDD.");


    }

    // Borrar coche por ID.
    public void borrarCoche(int id) throws SQLException {
        connection = new ConnectionDB().getConnection();
        String query = "DELETE FROM %s WHERE %s = ?";
        ps = connection.prepareStatement(String.format(query,
                SchemaDB.table_coches,SchemaDB.coches_id
                ));
        ps.setInt(1,id);
        ps.execute();
        System.out.println("COCHE ELIMINADO CORRECTAMENTE DE LA BBDD.");
    }

    // Consultar coche por ID.
    public void consultarCoche(int id) throws SQLException {
        connection = new ConnectionDB().getConnection();
        String query = "SELECT * FROM %s WHERE %s = ?";
        ps = connection.prepareStatement(String.format(query,
                SchemaDB.table_coches,SchemaDB.coches_id
        ));
        ps.setInt(1,id);
        rs = ps.executeQuery();

        while(rs.next()){
            String matricula=rs.getString(SchemaDB.coches_matricula);
            String marca=rs.getString(SchemaDB.coches_marca);
            String modelo=rs.getString(SchemaDB.coches_modelo);
            String color=rs.getString(SchemaDB.coches_color);

            System.out.println(new Coche(matricula,marca,modelo,color));
        }
    }

    // Modificar coche por ID.
    public void modificarCoche(int id,String color) throws SQLException {
        connection = new ConnectionDB().getConnection();
        String query = "UPDATE %s SET %s = ? WHERE %s = ?";
        ps = connection.prepareStatement(String.format(query,
                SchemaDB.table_coches,
                SchemaDB.coches_color,SchemaDB.coches_id
        ));
        ps.setString(1,color);
        ps.setInt(2,id);

        ps.execute();
        System.out.println("EL COCHE CON ID = "+id+" ACTUALIZADO CORRECTAMENTE.");
    }

    // Listar coches.
    public List<Coche> listarCoches() throws SQLException {
        connection = new ConnectionDB().getConnection();
        String query = "SELECT * FROM %s";
        ps = connection.prepareStatement(String.format(query,
                SchemaDB.table_coches
                ));

        rs = ps.executeQuery();
        while(rs.next()){
            String matricula=rs.getString(SchemaDB.coches_matricula);
            String marca=rs.getString(SchemaDB.coches_marca);
            String modelo=rs.getString(SchemaDB.coches_modelo);
            String color=rs.getString(SchemaDB.coches_color);

            listadoCoches.add(new Coche(matricula,marca,modelo,color));
        }
        for(Coche coche:listadoCoches){
            System.out.println(coche.toString());
        }
        return listadoCoches;
    }

    // Método para asociar un pasajero a un coche.
    public void asociarPasajeroACoche(int cocheId, int pasajeroId) throws SQLException {
        connection = new ConnectionDB().getConnection();
        String query = "UPDATE %s SET %s = ? WHERE %s = ?";
        ps = connection.prepareStatement(String.format(query,
                SchemaDB.table_coches,
                SchemaDB.coches_id_pasajero, SchemaDB.coches_id
        ));

        // Asignar el ID del pasajero y el ID del coche
        ps.setInt(1, pasajeroId);
        ps.setInt(2, cocheId);

        ps.executeUpdate();
        System.out.println("Asignación completada.");
    }

    // Método para eliminar un pasajero de un coche.
    public void eliminarPasajeroDeCoche(int cocheId, int pasajeroId) throws SQLException {
        connection = new ConnectionDB().getConnection();
        String query = "DELETE FROM %s WHERE %s = ? AND %s = ?";
        ps = connection.prepareStatement(String.format(query,
                SchemaDB.table_coches,
                SchemaDB.pasajeros_id,
                SchemaDB.coches_id
        ));

        ps.setInt(1, pasajeroId);
        ps.setInt(2, cocheId);
        ps.execute();

        System.out.println("Pasajero eliminado correctamente del coche.");
    }

    // Método para obtener los pasajeros asociados a un coche.
    public List<Integer> listarIdPasajeros(int idCoche) throws SQLException {
    List<Integer> idPasajeros = new ArrayList<>();
    // Seleccionamos todos los idPasajeros de la tabla coche.
    String query = "SELECT %s FROM %s WHERE %s = ?";
    ps = connection.prepareStatement(String.format(query,
            SchemaDB.coches_id_pasajero,
            SchemaDB.table_coches, SchemaDB.coches_id
            ));
    ps.setInt(1,idCoche);
    rs = ps.executeQuery();
    while(rs.next()){
        // Añadimos cada idPasajero a la lista de ids.
        int idPasajero = rs.getInt( SchemaDB.coches_id_pasajero);
        idPasajeros.add(idPasajero);
    }

    // Obtenemos la lista de id de los pasajeros asociados a un coche.
    return idPasajeros;
}


}
