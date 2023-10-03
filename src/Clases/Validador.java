package Clases;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;
import javax.swing.text.JTextComponent;

public class Validador {
    public boolean validarCampoVacio(JTextComponent campo, String nombreCampo) {
        String texto = campo.getText().trim();

        if (texto.isEmpty()) {
            JOptionPane.showMessageDialog(null, "El campo " + nombreCampo + " no puede estar vacío", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false; // Campo vacío
        } else {
            return true; // Campo no vacío
        }
    }

    public boolean validarCorreo(String correo) {
        String regex = "^[A-Za-z0-9+_.-]+@(\\w+\\.\\w+\\.\\w+|\\w+\\.\\w+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(correo);
        return matcher.matches();
    }

    public boolean validarNumero(String numero) {
        String regex = "^[0-9]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(numero);
        return matcher.matches();
    }

    //validate if the number is greater than zero, number can be integer or double

    public boolean validarNumeroMayorQueCero(String numero) {
        String regex = "^[0-9]+(\\.[0-9]+)?$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(numero);
        return matcher.matches();
    }

    public boolean validarNumeroConDosDecimales(String numero) {
        String regex = "^[0-9]+(\\.[0-9]{1,2})?$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(numero);
        return matcher.matches();
    }

}
