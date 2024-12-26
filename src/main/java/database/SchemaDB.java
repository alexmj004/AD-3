package database;

public interface SchemaDB {
    String DB = "concesionario";

    String table_coches= "coches";
    String table_pasajeros = "pasajeros";

    String coches_id="id";
    String coches_matricula = "matricula";
    String coches_marca = "marca";
    String coches_modelo = "modelo";
    String coches_color = "color";
    String coches_id_pasajero="id_pasajero";

    String pasajeros_id = "id";
    String pasajeros_nombre = "nombre";
    String pasajeros_edad = "edad";
    String pasajeros_peso = "peso";
}
