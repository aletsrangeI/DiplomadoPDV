/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package GUI;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Clases.Validador;

/**
 *
 * @author Brandon Emmanuel Reséndiz Granados, Ruben Lora Cruz, Alejandro Avila Rangel
 * 
 */
public class DetalleVentaGUI extends javax.swing.JPanel {

    private Validador _validador = new Validador();

    public DetalleVentaGUI() {
        initComponents();
        _validador = new Validador();

        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                validarCampos();
            }
        });
    }

    private void validarCampos() {
        boolean camposValidos = true;
        if (!_validador.validarCampoVacio(txtSubtotal, "Subtotal")) {
            camposValidos = false;
        } else if (!txtSubtotal.getText().matches("^\\d+(\\.\\d{1,2})?$")) {
            JOptionPane.showMessageDialog(null, "El subtotal debe ser un número con dos decimales.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            camposValidos = false;
        } else if (Double.parseDouble(txtSubtotal.getText()) <= 0) {
            JOptionPane.showMessageDialog(null, "El subtotal debe ser mayor que cero.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            camposValidos = false;
        }

        if (!_validador.validarCampoVacio(txtCantidad, "Cantidad")) {
            camposValidos = false;
        } else if (!txtCantidad.getText().matches("^\\d+$")) {
            JOptionPane.showMessageDialog(null, "La cantidad debe ser un número entero.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            camposValidos = false;
        } else if (Integer.parseInt(txtCantidad.getText()) <= 0) {
            JOptionPane.showMessageDialog(null, "La cantidad debe ser mayor que cero.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            camposValidos = false;
        }
    }

    public void limpiarCampos() {
        txtCantidad.setText("");
        txtSubtotal.setText("");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTitulo = new javax.swing.JLabel();
        lblVenta = new javax.swing.JLabel();
        cmbVenta = new javax.swing.JComboBox<>();
        lblProducto = new javax.swing.JLabel();
        cmbProducto = new javax.swing.JComboBox<>();
        lblCantidad = new javax.swing.JLabel();
        txtCantidad = new javax.swing.JTextField();
        lblSubtotal = new javax.swing.JLabel();
        txtSubtotal = new javax.swing.JTextField();
        btnGuardar = new javax.swing.JButton();

        setPreferredSize(new java.awt.Dimension(600, 400));

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        lblTitulo.setText("Detalle Venta");

        lblVenta.setText("Venta");

        cmbVenta.setModel(
                new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        lblProducto.setText("Producto");

        cmbProducto.setModel(
                new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        lblCantidad.setText("Cantidad");

        lblSubtotal.setText("Subtotal");

        btnGuardar.setText("Guardar");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(185, 185, 185)
                                                .addComponent(lblTitulo))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(218, 218, 218)
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(lblProducto)
                                                        .addComponent(lblVenta)
                                                        .addGroup(layout
                                                                .createParallelGroup(
                                                                        javax.swing.GroupLayout.Alignment.LEADING)
                                                                .addComponent(lblSubtotal)
                                                                .addComponent(lblCantidad)))
                                                .addGap(18, 18, 18)
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING,
                                                                false)
                                                        .addComponent(cmbVenta, 0, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                Short.MAX_VALUE)
                                                        .addComponent(cmbProducto, 0,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(txtCantidad)
                                                        .addComponent(txtSubtotal)))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(251, 251, 251)
                                                .addComponent(btnGuardar)))
                                .addContainerGap(189, Short.MAX_VALUE)));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(44, 44, 44)
                                .addComponent(lblTitulo)
                                .addGap(50, 50, 50)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblVenta)
                                        .addComponent(cmbVenta, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblProducto)
                                        .addComponent(cmbProducto, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(15, 15, 15)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblCantidad)
                                        .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblSubtotal)
                                        .addComponent(txtSubtotal, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(44, 44, 44)
                                .addComponent(btnGuardar)
                                .addContainerGap(52, Short.MAX_VALUE)));
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("Venta");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(600, 400);

                DetalleVentaGUI detalleVentaPanel = new DetalleVentaGUI();
                frame.add(detalleVentaPanel);

                frame.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGuardar;
    private javax.swing.JComboBox<String> cmbProducto;
    private javax.swing.JComboBox<String> cmbVenta;
    private javax.swing.JLabel lblCantidad;
    private javax.swing.JLabel lblProducto;
    private javax.swing.JLabel lblSubtotal;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblVenta;
    private javax.swing.JTextField txtCantidad;
    private javax.swing.JTextField txtSubtotal;
    // End of variables declaration//GEN-END:variables
}
