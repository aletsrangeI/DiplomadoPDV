package Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Clases.Venta;

public class VentaDB {

    public List<Venta> readAll() {
        List<Venta> ventas = new ArrayList<>();
        MysqlHandler mysqlHandler = MysqlHandler.getInstance();
        mysqlHandler.openConnection();
        ResultSet resultSet = mysqlHandler.readData("SELECT * FROM venta");
        try {
            while (resultSet.next()) {
                int id = resultSet.getInt("ID_Venta");
                int id_cliente = resultSet.getInt("ID_Cliente");
                Date fecha = resultSet.getDate("Fecha");
                double total = resultSet.getDouble("Total");
                Venta venta = new Venta(id, id_cliente, fecha, total);
                ventas.add(venta);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        mysqlHandler.closeConnection();
        return ventas;
    }

    public boolean create(Venta venta) {
        MysqlHandler mysqlHandler = MysqlHandler.getInstance();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = formatter.format(venta.getFecha());

        mysqlHandler.openConnection();
        String query = "INSERT INTO venta (ID_Cliente, Fecha, Total) VALUES ('" + venta.getId_Cliente()
                + "', " + "'" + formattedDate + "'" + ", " + venta.getTotal() + ")";
        boolean success = mysqlHandler.insertData(query);
        mysqlHandler.closeConnection();
        return success;
    }

    public boolean update(Venta venta) {
        MysqlHandler mysqlHandler = MysqlHandler.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = formatter.format(venta.getFecha());
        mysqlHandler.openConnection();
        String query = "UPDATE venta SET ID_Cliente = '" + venta.getId_Cliente() + "', Fecha = " + "'" + formattedDate
                + "'"
                + ", Total = " + venta.getTotal() + " WHERE ID_Venta = " + venta.getId_Venta();
        boolean success = mysqlHandler.updateData(query);
        mysqlHandler.closeConnection();
        return success;
    }

    public boolean delete(int id) {
        MysqlHandler mysqlHandler = MysqlHandler.getInstance();
        mysqlHandler.openConnection();
        String query = "DELETE FROM venta WHERE Id_Venta = " + id;
        boolean success = mysqlHandler.deleteData(query);
        mysqlHandler.closeConnection();
        return success;
    }

    public Venta getVentaById(int id) {
        Venta venta = null;
        MysqlHandler mysqlHandler = MysqlHandler.getInstance();
        mysqlHandler.openConnection();
        ResultSet resultSet = mysqlHandler.readData("SELECT * FROM venta WHERE Id_Venta = " + id);
        try {
            while (resultSet.next()) {
                int id_cliente = resultSet.getInt("ID_Cliente");
                Date fecha = resultSet.getDate("Fecha");
                double total = resultSet.getDouble("Total");
                venta = new Venta(id, id_cliente, fecha, total);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        mysqlHandler.closeConnection();
        return venta;
    }
}
