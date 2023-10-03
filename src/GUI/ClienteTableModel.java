package GUI;

import javax.swing.table.AbstractTableModel;
import Clases.Cliente;
import java.util.List;

/**
 *
 * @author Brandon Emmanuel Reséndiz Granados, Ruben Lora Cruz, Alejandro Avila Rangel
 * 
 */
public class ClienteTableModel extends AbstractTableModel {
    private final List<Cliente> clientes;
    private final String[] columnNames = { "ID_Cliente", "Nombre", "Apellidos", "Email", "Fecha de Nacimiento",
            "Editar" };

    public ClienteTableModel(List<Cliente> clientes) {
        this.clientes = clientes;
    }

    @Override
    public int getRowCount() {
        return clientes.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int colIndex) {
        Cliente cliente = clientes.get(rowIndex);
        switch (colIndex) {
            case 0:
                return cliente.getId_Cliente();
            case 1:
                return cliente.getNombre();
            case 2:
                return cliente.getApellido();
            case 3:
                return cliente.getCorreo();
            case 4:
                return cliente.getFechaNacimiento();
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int colIndex) {
        return columnNames[colIndex];
    }

    // add column to table
    public void addColumn(String columnName) {
        columnNames[columnNames.length - 1] = columnName;
        fireTableStructureChanged();
    }

}
