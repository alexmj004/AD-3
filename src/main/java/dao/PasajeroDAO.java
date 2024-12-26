package dao;

import database.ConnectionDB;
import database.SchemaDB;
import model.Pasajero;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PasajeroDAO {
    private Connection connection;
    private PreparedStatement ps;
    private ResultSet rs;

    public void añadirPasajero(Pasajero pasajero) throws SQLException {
        connection = new ConnectionDB().getConnection();
        String query="INSERT INTO %s (%s,%s%s) VALUES(?,?,?)";
        ps = connection.prepareStatement(String.format(query,
                SchemaDB.table_pasajeros,
                SchemaDB.pasajeros_nombre,SchemaDB.pasajeros_edad,SchemaDB.pasajeros_peso
                ));

        ps.setString(1,pasajero.getNombre());
        ps.setInt(2,pasajero.getEdad());
        ps.setDouble(3,pasajero.getPeso());

        ps.execute();
        System.out.println("PASAJERO AÑADIDO CORRECTAMENTE.");
    }

    public void borrarPasajero(int id) throws SQLException {
        connection = new ConnectionDB().getConnection();
        String query="DELETE FROM %s WHERE %s = ?";
        ps = connection.prepareStatement(String.format(query,
                SchemaDB.table_pasajeros,
                SchemaDB.pasajeros_id
        ));

        ps.setInt(1,id);
        ps.execute();
        System.out.println("PASAJERO ELIMINADO DE LA BBDD");
    }

    public void consultarPasajero(int id) throws SQLException {
        connection = new ConnectionDB().getConnection();
        String query="SELECT * FROM %s WHERE %s = ?";
        ps = connection.prepareStatement(String.format(query,
                SchemaDB.table_pasajeros,
                SchemaDB.pasajeros_id
        ));

        ps.setInt(1,id);

        rs = ps.executeQuery();
        while(rs.next()){
            String nombre = rs.getString(SchemaDB.pasajeros_nombre);
            int edad = rs.getInt(SchemaDB.pasajeros_edad);
            double peso = rs.getDouble(SchemaDB.pasajeros_peso);

            System.out.println(new Pasajero(nombre,edad,peso));
        }
    }

    public List<Pasajero> listarPasajeros() throws SQLException {
        connection = new ConnectionDB().getConnection();
        String query="SELECT * FROM %s";
        ps = connection.prepareStatement(String.format(query,
                SchemaDB.table_pasajeros
        ));

        rs = ps.executeQuery();
        List<Pasajero> pasajeros = new ArrayList<>();
        while (rs.next()){
            String nombre = rs.getString(SchemaDB.pasajeros_nombre);
            int edad = rs.getInt(SchemaDB.pasajeros_edad);
            double peso = rs.getDouble(SchemaDB.pasajeros_peso);

            pasajeros.add(new Pasajero(nombre,edad,peso));
        }
        return pasajeros;

    }



}
