package Clases;

public class Detalle_Venta {
    private int ID_Detalle;
    private int ID_Venta;
    private int ID_Producto;
    private int Cantidad;
    private double Subtotal;

    public Detalle_Venta(int ID_Detalle, int ID_Venta, int ID_Producto, int Cantidad, double Subtotal) {
        this.ID_Detalle = ID_Detalle;
        this.ID_Venta = ID_Venta;
        this.ID_Producto = ID_Producto;
        this.Cantidad = Cantidad;
        this.Subtotal = Subtotal;
    }

    public int getID_Detalle() {
        return ID_Detalle;
    }

    public int getID_Venta() {
        return ID_Venta;
    }

    public int getID_Producto() {
        return ID_Producto;
    }

    public int getCantidad() {
        return Cantidad;
    }

    public double getSubtotal() {
        return Subtotal;
    }
}
