/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package GUI;

import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import Clases.Detalle_Venta;
import Clases.Producto;
import Clases.Validador;
import Clases.ValoresCombo;
import Clases.Venta;
import Database.ProductoDB;
import Database.VentaDB;
import Database.Venta_DetalleDB;

/**
 *
 * @author Brandon Emmanuel Reséndiz Granados, Ruben Lora Cruz, Alejandro Avila
 *         Rangel
 * 
 */
public class DetalleVentaModGUI extends javax.swing.JPanel {
    private Validador _validador = new Validador();
    private VentaDB _ventaDB = new VentaDB();
    private Venta_DetalleDB _detalle_Venta = new Venta_DetalleDB();
    private ProductoDB _productoDB = new ProductoDB();
    private List<Detalle_Venta> listaDetalleVenta;
    private int indiceActual = 0;

    /**
     * Creates new form ClienteModGUI
     */
    public DetalleVentaModGUI() {
        initComponents();

        List<ValoresCombo> valoresComboList = new ArrayList<>();
        List<Venta> ventas = _ventaDB.readAll();

        for (Venta venta : ventas) {
            valoresComboList.add(new ValoresCombo(venta.getId_Venta(), String.valueOf(venta.getId_Venta())));
        }

        ValoresCombo[] valoresComboArray = valoresComboList.toArray(new ValoresCombo[0]);

        DefaultComboBoxModel<ValoresCombo> comboBoxModel = new DefaultComboBoxModel<>(valoresComboArray);
        cmbVenta.setModel(comboBoxModel);

        List<ValoresCombo> valoresComboList2 = new ArrayList<>();
        List<Producto> productos = _productoDB.readAll();
        for (Producto producto : productos) {
            valoresComboList2.add(new ValoresCombo(producto.getId(), producto.getNombre()));
        }
        ValoresCombo[] valoresComboArray2 = valoresComboList2.toArray(new ValoresCombo[0]);
        DefaultComboBoxModel<ValoresCombo> comboBoxModel2 = new DefaultComboBoxModel<>(valoresComboArray2);
        cmbProducto.setModel(comboBoxModel2);

        listaDetalleVenta = CargarDatos();

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

                // Get selected value from cmbVenta and assign it to venta
                int venta = ((ValoresCombo) cmbVenta.getSelectedItem()).getId();
                // Get selected value from cmbProducto and assign it to producto
                int producto = ((ValoresCombo) cmbProducto.getSelectedItem()).getId();
                int cantidad = Integer.parseInt(txtCantidad.getText());
                double subtotal = Double.parseDouble(txtSubtotal.getText());
                Detalle_Venta detalle_Venta = new Detalle_Venta(0, venta, producto, cantidad, subtotal);

                if (_detalle_Venta.create(detalle_Venta)) {
                    JOptionPane.showMessageDialog(null, "Venta insertado correctamente.");
                    limpiarCampos();
                    listaDetalleVenta = CargarDatos();
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

                int detalleId = Integer.parseInt(txtId_Detalle.getText());
                // Get selected value from cmbVenta and assign it to venta
                int venta = ((ValoresCombo) cmbVenta.getSelectedItem()).getId();
                // Get selected value from cmbProducto and assign it to producto
                int producto = ((ValoresCombo) cmbProducto.getSelectedItem()).getId();
                int cantidad = Integer.parseInt(txtCantidad.getText());
                double subtotal = Double.parseDouble(txtSubtotal.getText());
                Detalle_Venta detalle_Venta = new Detalle_Venta(detalleId, venta, producto, cantidad, subtotal);

                if (_detalle_Venta.update(detalle_Venta)) {
                    JOptionPane.showMessageDialog(null, "Venta actualizada correctamente.");
                    listaDetalleVenta = CargarDatos();
                    mostrarClienteActual();
                } else {
                    JOptionPane.showMessageDialog(null, "Error al actualizar la venta.");
                }
            }
        });

        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {

                int detalleId = Integer.parseInt(txtId_Detalle.getText());

                if (_detalle_Venta.delete(detalleId)) {
                    JOptionPane.showMessageDialog(null, "Venta eliminada correctamente.");
                    limpiarCampos();
                    listaDetalleVenta = CargarDatos();
                } else {
                    JOptionPane.showMessageDialog(null, "Error al eliminar la venta.");
                }
            }
        });
    }

    private List<Detalle_Venta> CargarDatos() {
        _detalle_Venta = new Venta_DetalleDB();
        List<Detalle_Venta> venta_detalle = _detalle_Venta.readAll();
        return venta_detalle;
    }

    private void mostrarClienteActual() {
        if (!listaDetalleVenta.isEmpty()) {
            Detalle_Venta venta_detalle = listaDetalleVenta.get(indiceActual);
            txtId_Detalle.setText(String.valueOf(venta_detalle.getID_Detalle()));
            cmbVenta.setSelectedIndex(venta_detalle.getID_Venta() - 1);
            cmbProducto.setSelectedIndex(venta_detalle.getID_Producto() - 1);
            txtCantidad.setText(String.valueOf(venta_detalle.getCantidad()));
            txtSubtotal.setText(String.valueOf(venta_detalle.getSubtotal()));
        }
    }

    private void btnSiguienteActionPerformed(java.awt.event.ActionEvent evt) {
        if (indiceActual < listaDetalleVenta.size() - 1) {
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
        return !txtId_Detalle.isEnabled() && !cmbVenta.isEnabled() && !cmbProducto.isEnabled();
    }

    private boolean validarCampos() {
        boolean camposValidos = true;

        // validar campos vacios cantidad y subtotal

        if (!_validador.validarCampoVacio(txtCantidad, "Cantidad")) {
            camposValidos = false;
        }

        if (!_validador.validarCampoVacio(txtSubtotal, "Subtotal")) {
            camposValidos = false;
        }

        if (!_validador.validarNumero(txtCantidad.getText())) {
            JOptionPane.showMessageDialog(null, "El campo Cantidad no es valido.");
            camposValidos = false;
        }

        if (!_validador.validarNumeroMayorQueCero(txtCantidad.getText())) {
            JOptionPane.showMessageDialog(null, "El campo Cantidad debe ser mayor que cero.");
            camposValidos = false;
        }

        if (!_validador.validarNumeroConDosDecimales(txtSubtotal.getText())) {
            JOptionPane.showMessageDialog(null, "El campo Subtotal no es valido.");
            camposValidos = false;
        }

        if (!_validador.validarNumeroMayorQueCero(txtSubtotal.getText())) {
            JOptionPane.showMessageDialog(null, "El campo Subtotal debe ser mayor que cero.");
            camposValidos = false;
        }

        return camposValidos;
    }

    public void limpiarCampos() {
        txtCantidad.setText("");
        txtSubtotal.setText("");
    }

    public void disableAllFields() {
        txtId_Detalle.setEnabled(false);
        cmbVenta.setEnabled(false);
        cmbProducto.setEnabled(false);
        txtCantidad.setEnabled(false);
        txtSubtotal.setEnabled(false);
        btnEditar.setEnabled(false);
    }

    public void enableAllFields() {
        txtId_Detalle.setEnabled(true);
        cmbProducto.setEnabled(true);
        cmbVenta.setEnabled(true);
        txtCantidad.setEnabled(true);
        txtSubtotal.setEnabled(true);
        btnEditar.setEnabled(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("Cliente App");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(600, 400);

                DetalleVentaModGUI detalleMod = new DetalleVentaModGUI();
                frame.add(detalleMod);

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
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblId_Detalle = new javax.swing.JLabel();
        txtId_Detalle = new javax.swing.JTextField();
        lblVenta = new javax.swing.JLabel();
        btnAnterior = new javax.swing.JButton();
        btnSiguiente = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        lblTitulo = new javax.swing.JLabel();
        btnEnable = new javax.swing.JButton();
        cmbVenta = new javax.swing.JComboBox<>();
        lblProducto = new javax.swing.JLabel();
        cmbProducto = new javax.swing.JComboBox<>();
        lblCantidad = new javax.swing.JLabel();
        txtCantidad = new javax.swing.JTextField();
        lblSubtotal = new javax.swing.JLabel();
        txtSubtotal = new javax.swing.JTextField();

        setPreferredSize(new java.awt.Dimension(600, 400));

        lblId_Detalle.setText("Id_Detalle");

        lblVenta.setText("Venta");

        btnAnterior.setText("< Anterior");

        btnSiguiente.setText("Siguiente >");

        btnGuardar.setText("Guardar");

        btnEditar.setText("Editar");

        btnEliminar.setText("Eliminar");

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblTitulo.setText("Venta");

        btnEnable.setText("Activar Campos!");

        cmbVenta.setModel(new DefaultComboBoxModel<>(new ValoresCombo[0]));

        lblProducto.setText("Producto");

        cmbProducto.setModel(new DefaultComboBoxModel<>(new ValoresCombo[0]));

        lblCantidad.setText("Cantidad");

        lblSubtotal.setText("Subtotal");

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
                                                        .addComponent(lblId_Detalle,
                                                                javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(lblVenta,
                                                                javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(lblProducto,
                                                                javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(lblCantidad,
                                                                javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(lblSubtotal,
                                                                javax.swing.GroupLayout.Alignment.TRAILING))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(txtId_Detalle, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        136, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addContainerGap())
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                                                false)
                                                        .addComponent(txtSubtotal,
                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(cmbVenta,
                                                                javax.swing.GroupLayout.Alignment.LEADING, 0, 136,
                                                                Short.MAX_VALUE)
                                                        .addComponent(cmbProducto,
                                                                javax.swing.GroupLayout.Alignment.LEADING, 0,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(txtCantidad,
                                                                javax.swing.GroupLayout.Alignment.LEADING))
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
                                                                .addGap(53, 53, 53))))))
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
                                        .addComponent(lblId_Detalle)
                                        .addComponent(txtId_Detalle, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(lblVenta)
                                                        .addComponent(cmbVenta, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(18, 18, 18)
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(lblProducto)
                                                        .addComponent(cmbProducto,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(18, 18, 18)
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(lblCantidad)
                                                        .addComponent(txtCantidad,
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
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblSubtotal)
                                        .addComponent(txtSubtotal, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 61,
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
    private javax.swing.JComboBox<ValoresCombo> cmbProducto;
    private javax.swing.JComboBox<ValoresCombo> cmbVenta;
    private javax.swing.JLabel lblCantidad;
    private javax.swing.JLabel lblId_Detalle;
    private javax.swing.JLabel lblProducto;
    private javax.swing.JLabel lblSubtotal;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblVenta;
    private javax.swing.JTextField txtCantidad;
    private javax.swing.JTextField txtId_Detalle;
    private javax.swing.JTextField txtSubtotal;
    // End of variables declaration//GEN-END:variables
}
