/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package GUI;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import Clases.Cliente;
import Clases.Validador;
import Clases.ValoresCombo;
import Clases.Venta;
import Database.ClienteDB;
import Database.VentaDB;

/**
 *
 * @author Brandon Emmanuel Reséndiz Granados, Ruben Lora Cruz, Alejandro Avila
 *         Rangel
 * 
 */
public class VentaModGUI extends javax.swing.JPanel {
    private Validador _validador = new Validador();
    private VentaDB _ventaDB = new VentaDB();
    private ClienteDB _clienteDB = new ClienteDB();
    private List<Venta> listaVentas;
    private int indiceActual = 0;

    /**
     * Creates new form ClienteModGUI
     */
    public VentaModGUI() {
        initComponents();

        List<ValoresCombo> valoresComboList = new ArrayList<>();
        List<Cliente> clientes = _clienteDB.readAll();

        for (Cliente cliente : clientes) {
            valoresComboList.add(new ValoresCombo(cliente.getId_Cliente(), cliente.getNombre()));
        }

        ValoresCombo[] valoresComboArray = valoresComboList.toArray(new ValoresCombo[0]);

        DefaultComboBoxModel<ValoresCombo> comboBoxModel = new DefaultComboBoxModel<>(valoresComboArray);
        cmbCliente.setModel(comboBoxModel);

        listaVentas = CargarDatos();
        mostrarClienteActual();
        disableAllFields();
        btnEditar.setEnabled(false);

        btnEnable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                enableAllFields();
            }
        });

        btnSiguiente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSiguienteActionPerformed(evt);
            }
        });

        btnAnterior.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnteriorActionPerformed(evt);
            }
        });

        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if (!validarCampos()) {
                    return;
                }

                // Get selected value from cmbCliente and assign it to id_Cliente
                int id_Cliente = ((ValoresCombo) cmbCliente.getSelectedItem()).getId();
                double total = Double.parseDouble(txtTotal.getText());
                Date fecha = dateChooser.getDate();

                Venta venta = new Venta(0, id_Cliente, fecha, total);

                if (_ventaDB.create(venta)) {
                    JOptionPane.showMessageDialog(null, "Venta insertado correctamente.");
                    limpiarCampos();
                    listaVentas = CargarDatos();
                } else {
                    JOptionPane.showMessageDialog(null, "Error al insertar la venta.");
                }
            }
        });

        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {

                if (AllFieldsAreDisabled()) {
                    JOptionPane.showMessageDialog(null, "Los campos están deshabilitados.");
                    return;
                }

                // validate if the fields are not disable
                if (!validarCampos()) {
                    return;
                }

                int id_Venta = Integer.parseInt(txtId_Venta.getText());
                int id_Cliente = ((ValoresCombo) cmbCliente.getSelectedItem()).getId();
                double total = Double.parseDouble(txtTotal.getText());
                Date fecha = dateChooser.getDate();

                Venta venta = new Venta(id_Venta, id_Cliente, fecha, total);

                if (_ventaDB.update(venta)) {
                    JOptionPane.showMessageDialog(null, "Venta actualizada correctamente.");
                    listaVentas = CargarDatos();
                    mostrarClienteActual();
                } else {
                    JOptionPane.showMessageDialog(null, "Error al actualizar la venta.");
                }
            }
        });

        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {

                int id_Venta = Integer.parseInt(txtId_Venta.getText());

                if (_ventaDB.delete(id_Venta)) {
                    JOptionPane.showMessageDialog(null, "Venta eliminada correctamente.");
                    limpiarCampos();
                    listaVentas = CargarDatos();
                } else {
                    JOptionPane.showMessageDialog(null, "Error al eliminar la venta.");
                }
            }
        });
    }

    private List<Venta> CargarDatos() {
        _ventaDB = new VentaDB();
        List<Venta> ventas = _ventaDB.readAll();
        return ventas;
    }

    private void mostrarClienteActual() {
        if (!listaVentas.isEmpty()) {
            Venta venta = listaVentas.get(indiceActual);
            txtId_Venta.setText(String.valueOf(venta.getId_Venta()));
            txtTotal.setText(String.valueOf(venta.getTotal()));
            dateChooser.setDate(venta.getFecha());
            cmbCliente.setSelectedIndex(venta.getId_Cliente() - 1);
        }
    }

    private void btnSiguienteActionPerformed(java.awt.event.ActionEvent evt) {
        if (indiceActual < listaVentas.size() - 1) {
            indiceActual++;
            mostrarClienteActual();
            disableAllFields();
        }
    }

    private void btnAnteriorActionPerformed(java.awt.event.ActionEvent evt) {
        if (indiceActual > 0) {
            indiceActual--;
            mostrarClienteActual();
            disableAllFields();
        }
    }

    // validate if the fields are not disabled
    private boolean AllFieldsAreDisabled() {
        return !txtTotal.isEnabled() && !txtTotal.isEnabled()
                && !dateChooser.isEnabled();
    }

    private boolean validarCampos() {
        boolean camposValidos = true;

        // validate empty fields
        if (!_validador.validarCampoVacio(txtTotal, "Total")) {
            camposValidos = false;
        }

        if (!_validador.validarNumeroConDosDecimales(txtTotal.getText())) {
            JOptionPane.showMessageDialog(this, "El total no es válido.",
                    "Error de validación", JOptionPane.ERROR_MESSAGE);
            camposValidos = false;
        }

        Date fechaElegida = dateChooser.getDate();
        if (fechaElegida == null) {
            JOptionPane.showMessageDialog(this, "La fecha de nacimiento no ha sido seleccionada.",
                    "Error de validación", JOptionPane.ERROR_MESSAGE);
            camposValidos = false;
        }
        return camposValidos;
    }

    public void limpiarCampos() {
        txtTotal.setText("");
        dateChooser.setDate(null);
    }

    public void disableAllFields() {
        txtId_Venta.setEnabled(false);
        txtTotal.setEnabled(false);
        dateChooser.setEnabled(false);
        cmbCliente.setEnabled(false);
        btnEditar.setEnabled(false);
    }

    public void enableAllFields() {
        txtTotal.setEnabled(true);
        dateChooser.setEnabled(true);
        cmbCliente.setEnabled(true);
        btnEditar.setEnabled(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("Cliente App");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(600, 400);

                VentaModGUI ventaMod = new VentaModGUI();
                frame.add(ventaMod);

                frame.setVisible(true);
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblId_Venta = new javax.swing.JLabel();
        txtId_Venta = new javax.swing.JTextField();
        lblNombre = new javax.swing.JLabel();
        lblTotal = new javax.swing.JLabel();
        txtTotal = new javax.swing.JTextField();
        dateChooser = new com.toedter.calendar.JDateChooser();
        lblFechaNacimiento = new javax.swing.JLabel();
        btnAnterior = new javax.swing.JButton();
        btnSiguiente = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        lblTitulo = new javax.swing.JLabel();
        btnEnable = new javax.swing.JButton();
        cmbCliente = new javax.swing.JComboBox<>();

        setPreferredSize(new java.awt.Dimension(600, 400));

        lblId_Venta.setText("Id_Venta");

        lblNombre.setText("Cliente");

        lblTotal.setText("Total");

        lblFechaNacimiento.setText("Fecha");

        btnAnterior.setText("< Anterior");

        btnSiguiente.setText("Siguiente >");

        btnGuardar.setText("Guardar");

        btnEditar.setText("Editar");

        btnEliminar.setText("Eliminar");

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblTitulo.setText("Venta");

        btnEnable.setText("Activar Campos!");

        cmbCliente.setModel(new DefaultComboBoxModel<>(new ValoresCombo[0]));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(52, 52, 52)
                                                .addComponent(btnAnterior)
                                                .addGap(16, 16, 16))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout
                                                .createSequentialGroup()
                                                .addContainerGap()
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(lblId_Venta,
                                                                javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(lblNombre,
                                                                javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(lblTotal,
                                                                javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(lblFechaNacimiento,
                                                                javax.swing.GroupLayout.Alignment.TRAILING))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(txtTotal,
                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(dateChooser, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(cmbCliente,
                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE, 136,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGap(163, 163, 163)
                                                                .addGroup(layout.createParallelGroup(
                                                                        javax.swing.GroupLayout.Alignment.TRAILING)
                                                                        .addGroup(layout.createSequentialGroup()
                                                                                .addGroup(layout.createParallelGroup(
                                                                                        javax.swing.GroupLayout.Alignment.LEADING)
                                                                                        .addComponent(btnEditar)
                                                                                        .addComponent(btnEliminar)
                                                                                        .addComponent(btnGuardar))
                                                                                .addGap(41, 41, 41))
                                                                        .addComponent(btnSiguiente))
                                                                .addGap(35, 35, 35))
                                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout
                                                                .createSequentialGroup()
                                                                .addPreferredGap(
                                                                        javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                        Short.MAX_VALUE)
                                                                .addComponent(btnEnable)
                                                                .addGap(53, 53, 53))))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(txtId_Venta, javax.swing.GroupLayout.PREFERRED_SIZE, 136,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addContainerGap())))
                        .addGroup(layout.createSequentialGroup()
                                .addGap(267, 267, 267)
                                .addComponent(lblTitulo)
                                .addGap(0, 0, Short.MAX_VALUE)));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(27, 27, 27)
                                .addComponent(lblTitulo)
                                .addGap(33, 33, 33)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblId_Venta)
                                        .addComponent(txtId_Venta, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(lblNombre)
                                                        .addComponent(cmbCliente,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(18, 18, 18)
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(lblTotal)
                                                        .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(18, 18, 18)
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(lblFechaNacimiento)
                                                        .addComponent(dateChooser,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(btnEliminar)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnEditar)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnGuardar)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnEnable)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 101,
                                        Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnAnterior)
                                        .addComponent(btnSiguiente))
                                .addGap(34, 34, 34)));
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAnterior;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnEnable;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnSiguiente;
    private javax.swing.JComboBox<ValoresCombo> cmbCliente;
    private com.toedter.calendar.JDateChooser dateChooser;
    private javax.swing.JLabel lblFechaNacimiento;
    private javax.swing.JLabel lblId_Venta;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JTextField txtId_Venta;
    private javax.swing.JTextField txtTotal;
    // End of variables declaration//GEN-END:variables
}
