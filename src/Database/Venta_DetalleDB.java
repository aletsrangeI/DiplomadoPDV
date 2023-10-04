package Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Clases.Detalle_Venta;

public class Venta_DetalleDB {
    public List<Detalle_Venta> readAll() {
        List<Detalle_Venta> detalle_ventas = new ArrayList<>();
        MysqlHandler mysqlHandler = MysqlHandler.getInstance();
        mysqlHandler.openConnection();
        ResultSet resultSet = mysqlHandler.readData("SELECT * FROM detalle_venta");
        try {
            while (resultSet.next()) {
                int id = resultSet.getInt("ID_Detalle");
                int id_venta = resultSet.getInt("Id_Venta");
                int id_producto = resultSet.getInt("ID_Producto");
                int cantidad = resultSet.getInt("Cantidad");
                double subtotal = resultSet.getDouble("Subtotal");
                Detalle_Venta detalle_venta = new Detalle_Venta(id, id_venta, id_producto, cantidad, subtotal);
                detalle_ventas.add(detalle_venta);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        mysqlHandler.closeConnection();
        return detalle_ventas;
    }

    public boolean create(Detalle_Venta detalle_venta) {
        MysqlHandler mysqlHandler = MysqlHandler.getInstance();
        mysqlHandler.openConnection();
        String query = "INSERT INTO detalle_venta (ID_Producto, Cantidad, Subtotal) VALUES ('"
                + "', " + detalle_venta.getID_Producto() + ", " + detalle_venta.getCantidad() + ", '"
                + detalle_venta.getSubtotal() + "')";
        boolean success = mysqlHandler.insertData(query);
        mysqlHandler.closeConnection();
        return success;
    }

    public boolean update(Detalle_Venta detalle_venta) {
        MysqlHandler mysqlHandler = MysqlHandler.getInstance();
        mysqlHandler.openConnection();

        String query = "UPDATE detalle_venta SET ID_Producto = '" + detalle_venta.getID_Producto() + "', Cantidad = "
                + detalle_venta.getCantidad()
                + ", Subtotal = " + detalle_venta.getSubtotal() + " WHERE ID_Detalle = "
                + detalle_venta.getID_Detalle();

        boolean success = mysqlHandler.updateData(query);
        mysqlHandler.closeConnection();
        return success;
    }

    public boolean delete(int id) {
        MysqlHandler mysqlHandler = MysqlHandler.getInstance();
        mysqlHandler.openConnection();
        String query = "DELETE FROM detalle_venta WHERE Id_Detalle = " + id;
        boolean success = mysqlHandler.deleteData(query);
        mysqlHandler.closeConnection();
        return success;
    }

    public Detalle_Venta getDetalle_VentaById(int id) {
        Detalle_Venta detalle_venta = null;
        MysqlHandler mysqlHandler = MysqlHandler.getInstance();
        mysqlHandler.openConnection();
        ResultSet resultSet = mysqlHandler.readData("SELECT * FROM detalle_venta WHERE Id_Detalle = " + id);
        try {
            while (resultSet.next()) {
                int id_venta = resultSet.getInt("Id_Venta");
                int id_producto = resultSet.getInt("ID_Producto");
                int cantidad = resultSet.getInt("Cantidad");
                double subtotal = resultSet.getDouble("Subtotal");
                detalle_venta = new Detalle_Venta(id, id_venta, id_producto, cantidad, subtotal);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        mysqlHandler.closeConnection();
        return detalle_venta;
    }
}
