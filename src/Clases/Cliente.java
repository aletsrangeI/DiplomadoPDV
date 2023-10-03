package Clases;

import java.util.Date;

public class Cliente {
    private int Id_Cliente;
    private String Nombre;
    private String Apellido;
    private String Correo;
    private Date FechaNacimiento;

    public int getId_Cliente() {

        return Id_Cliente;
    }

    public void setId_Cliente(int Id_Cliente) {

        this.Id_Cliente = Id_Cliente;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getApellido() {
        return Apellido;
    }

    public void setApellido(String Apellido) {
        this.Apellido = Apellido;
    }

    public String getCorreo() {
        return Correo;
    }

    public void setCorreo(String Correo) {
        this.Correo = Correo;
    }

    public Date getFechaNacimiento() {
        return FechaNacimiento;
    }

    public void setFechaNacimiento(Date FechaNacimiento) {
        this.FechaNacimiento = FechaNacimiento;
    }
}
