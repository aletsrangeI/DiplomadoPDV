package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
/**
 *
 * @author Brandon Emmanuel Reséndiz Granados, Ruben Lora Cruz, Alejandro Avila Rangel
 * 
 */
public class AccessHandler {

    private static final String DB_URL = "jdbc:ucanaccess://C:\\AccessBD\\puntodeventatest.accdb";

    private static Connection connection = null;

    public boolean conectar() {
        boolean connected = false;
        try {
            connection = DriverManager.getConnection(DB_URL);
            connected = true;
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error al conectar a la base de datos: " + e.getMessage());
        }
        return connected;
    }

    public boolean desconectar() {
        boolean disconnected = false;
        if (connection != null) {
            try {
                connection.close();
                disconnected = true;
            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println("Error al desconectar de la base de datos: " + e.getMessage());
            }
        }
        return disconnected;
    }

    public boolean ejecutarComando(String sql) {
        boolean executed = false;
        if (connection != null) {
            try {
                Statement statement = connection.createStatement();
                statement.executeUpdate(sql);
                statement.close();
                executed = true;
            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println("Error al ejecutar el comando: " + e.getMessage());
            }
        } else {
            System.err.println("No se ha establecido una conexión a la base de datos.");
        }
        return executed;
    }

    public ResultSet leerDatos(String sql) {
        ResultSet resultSet = null;
        if (connection != null) {
            try {
                Statement statement = connection.createStatement();
                resultSet = statement.executeQuery(sql);
                System.out.println("Lectura de datos exitosa.");
            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println("Error al leer datos: " + e.getMessage());
            }
        } else {
            System.err.println("No se ha establecido una conexión a la base de datos.");
        }
        return resultSet;
    }
}
