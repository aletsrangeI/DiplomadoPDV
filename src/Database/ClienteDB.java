package Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Clases.Cliente;

public class ClienteDB {

    public List<Cliente> readAll() {
        List<Cliente> clientes = new ArrayList<>();
        MysqlHandler mysqlHandler = MysqlHandler.getInstance();
        mysqlHandler.openConnection();
        ResultSet resultSet = mysqlHandler.readData("SELECT * FROM cliente");
        try {
            while (resultSet.next()) {
                int id = resultSet.getInt("ID_Cliente");
                String nombres = resultSet.getString("Nombre");
                String apellidos = resultSet.getString("Apellido");
                String correo = resultSet.getString("Correo");
                Date fechaNacimiento = resultSet.getDate("FechaNacimiento");
                Cliente cliente = new Cliente(id, nombres, apellidos, correo, fechaNacimiento);
                clientes.add(cliente);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        mysqlHandler.closeConnection();
        return clientes;
    }

    public boolean create(Cliente cliente) {
        MysqlHandler mysqlHandler = MysqlHandler.getInstance();
        mysqlHandler.openConnection();
        String query = "INSERT INTO cliente (Nombre, Apellido, Correo, FechaNacimiento) VALUES ('" + cliente.getNombre()
                + "', '" + cliente.getApellido() + "', '" + cliente.getCorreo() + "', '" + cliente.getFechaNacimiento()
                + "')";
        boolean success = mysqlHandler.insertData(query);
        mysqlHandler.closeConnection();
        return success;
    }

    public boolean update(Cliente cliente) {
        MysqlHandler mysqlHandler = MysqlHandler.getInstance();
        mysqlHandler.openConnection();
        String query = "UPDATE cliente SET Nombre = '" + cliente.getNombre() + "', Apellido = '" + cliente.getApellido()
                + "', Correo = '" + cliente.getCorreo() + "', FechaNacimiento = '" + cliente.getFechaNacimiento()
                + "' WHERE ID_Cliente = " + cliente.getId_Cliente();
        boolean success = mysqlHandler.updateData(query);
        mysqlHandler.closeConnection();
        return success;
    }

    public boolean delete(int id) {
        MysqlHandler mysqlHandler = MysqlHandler.getInstance();
        mysqlHandler.openConnection();
        String query = "DELETE FROM cliente WHERE Id_Cliente = " + id;
        boolean success = mysqlHandler.deleteData(query);
        mysqlHandler.closeConnection();
        return success;
    }

    public Cliente getClienteById(int id) {
        Cliente cliente = null;
        MysqlHandler mysqlHandler = MysqlHandler.getInstance();
        mysqlHandler.openConnection();
        ResultSet resultSet = mysqlHandler.readData("SELECT * FROM cliente WHERE Id_Cliente = " + id);
        try {
            while (resultSet.next()) {
                String nombres = resultSet.getString("Nombre");
                String apellidos = resultSet.getString("Apellido");
                String correo = resultSet.getString("Correo");
                Date fechaNacimiento = resultSet.getDate("FechaNacimiento");
                cliente = new Cliente(id, nombres, apellidos, correo, fechaNacimiento);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        mysqlHandler.closeConnection();
        return cliente;
    }
}
