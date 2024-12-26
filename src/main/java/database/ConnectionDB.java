package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB {
    private Connection connection;

    // CREAMOS CONEXIÓN.
    public void createNewConnection(){
        String url = "jdbc:mysql://localhost:3306/"+SchemaDB.DB;
        try {
            connection = DriverManager.getConnection(url,"root","");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // DAMOS LA CONEXIÓN.
    public Connection getConnection(){
        // Sí no existe la conexión la creamos.
        if(connection == null){
            createNewConnection();
        }

        return connection;
    }

    // CERRAMOS LA CONEXIÓN.
    public void closeConnection(){
        try {
            connection.close();
            connection=null;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
