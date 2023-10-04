package Clases;

import java.util.Date;

public class Cliente {
    private int Id_Cliente;
    private String Nombre;
    private String Apellido;
    private String Correo;
    private Date FechaNacimiento;

    public Cliente(int Id_Cliente, String Nombre, String Apellido, String Correo, Date FechaNacimiento) {
        this.Id_Cliente = Id_Cliente;
        this.Nombre = Nombre;
        this.Apellido = Apellido;
        this.Correo = Correo;
        this.FechaNacimiento = FechaNacimiento;
    }

    public int getId_Cliente() {

        return Id_Cliente;
    }

    public String getNombre() {
        return Nombre;
    }

    public String getApellido() {
        return Apellido;
    }

    public String getCorreo() {
        return Correo;
    }

    public Date getFechaNacimiento() {
        return FechaNacimiento;
    }
}