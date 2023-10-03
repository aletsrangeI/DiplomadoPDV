package Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Clases.Producto;

public class ProductoDB {
    public List<Producto> readAll() {
        List<Producto> productos = new ArrayList<>();
        MysqlHandler mysqlHandler = MysqlHandler.getInstance();
        mysqlHandler.openConnection();
        ResultSet resultSet = mysqlHandler.readData("SELECT * FROM producto");
        try {
            while (resultSet.next()) {
                int id = resultSet.getInt("Id_Producto");
                String nombre = resultSet.getString("Nombre");
                double precio = resultSet.getDouble("Precio");
                int stock = resultSet.getInt("Stock");
                String categoria = resultSet.getString("Categoria");
                Producto producto = new Producto(id, nombre, precio, stock, categoria);
                productos.add(producto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        mysqlHandler.closeConnection();
        return productos;
    }

    public boolean create(Producto producto) {
        MysqlHandler mysqlHandler = MysqlHandler.getInstance();
        mysqlHandler.openConnection();
        String query = "INSERT INTO producto (Nombre, Precio, Stock, Categoria) VALUES ('" + producto.getNombre()
                + "', " + producto.getPrecio() + ", " + producto.getStock() + ", '" + producto.getCategoria() + "')";
        boolean success = mysqlHandler.insertData(query);
        mysqlHandler.closeConnection();
        return success;
    }

    public boolean update(Producto producto) {
        MysqlHandler mysqlHandler = MysqlHandler.getInstance();
        mysqlHandler.openConnection();
        String query = "UPDATE producto SET Nombre = '" + producto.getNombre() + "', Precio = " + producto.getPrecio()
                + ", Stock = " + producto.getStock() + ", Categoria = '" + producto.getCategoria()
                + "' WHERE Id_Producto = " + producto.getId();
        boolean success = mysqlHandler.updateData(query);
        mysqlHandler.closeConnection();
        return success;
    }

    public boolean delete(int id) {
        MysqlHandler mysqlHandler = MysqlHandler.getInstance();
        mysqlHandler.openConnection();
        String query = "DELETE FROM producto WHERE Id_Producto = " + id;
        boolean success = mysqlHandler.deleteData(query);
        mysqlHandler.closeConnection();
        return success;
    }

    public Producto getProductoById(int id) {
        Producto producto = null;
        MysqlHandler mysqlHandler = MysqlHandler.getInstance();
        mysqlHandler.openConnection();
        ResultSet resultSet = mysqlHandler.readData("SELECT * FROM producto WHERE Id_Producto = " + id);
        try {
            while (resultSet.next()) {
                String nombre = resultSet.getString("Nombre");
                double precio = resultSet.getDouble("Precio");
                int stock = resultSet.getInt("Stock");
                String categoria = resultSet.getString("Categoria");
                producto = new Producto(id, nombre, precio, stock, categoria);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        mysqlHandler.closeConnection();
        return producto;
    }
}