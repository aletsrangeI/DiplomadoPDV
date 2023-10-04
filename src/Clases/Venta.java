package Clases;

import java.util.Date;

public class Venta {
    private int Id_Venta;
    private int Id_Cliente;
    private Date Fecha;
    private double Total;

    public Venta(int Id_Venta, int Id_Cliente, Date Fecha, double Total) {
        this.Id_Venta = Id_Venta;
        this.Id_Cliente = Id_Cliente;
        this.Fecha = Fecha;
        this.Total = Total;
    }

    public int getId_Venta() {
        return Id_Venta;
    }

    public int getId_Cliente() {
        return Id_Cliente;
    }

    public Date getFecha() {
        return Fecha;
    }

    public double getTotal() {
        return Total;
    }
}
