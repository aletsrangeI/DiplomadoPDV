/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package GUI;

import java.util.Date;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import Clases.Producto;
import Clases.Validador;
import Database.ProductoDB;

/**
 *
 * @author Brandon Emmanuel Reséndiz Granados, Ruben Lora Cruz, Alejandro Avila
 *         Rangel
 * 
 */
public class ProductoModGUI extends javax.swing.JPanel {
    private Validador _validador = new Validador();
    private ProductoDB _productoDB = new ProductoDB();
    private List<Producto> listaProductos;
    private int indiceActual = 0;

    /**
     * Creates new form ClienteModGUI
     */
    public ProductoModGUI() {
        initComponents();
        listaProductos = CargarDatos();
        mostrarProductoActual();
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
                // create object of producto class but precio is double and stock is int
                int id_Producto = Integer.parseInt(txtId_Producto.getText());
                String nombre = txtNombre.getText();
                double precio = Double.parseDouble(txtPrecio.getText());
                int stock = Integer.parseInt(txtStock.getText());
                String categoria = txtCategoria.getText();

                Producto producto = new Producto(id_Producto, nombre, precio, stock, categoria);

                if (_productoDB.create(producto)) {
                    JOptionPane.showMessageDialog(null, "Producto insertado correctamente.");
                    limpiarCampos();
                    listaProductos = CargarDatos();
                } else {
                    JOptionPane.showMessageDialog(null, "Error al insertar el producto.");
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
                int id_Producto = Integer.parseInt(txtId_Producto.getText());

                String nombre = txtNombre.getText();
                double precio = Double.parseDouble(txtPrecio.getText());
                int stock = Integer.parseInt(txtStock.getText());
                String categoria = txtCategoria.getText();

                Producto producto = new Producto(id_Producto, nombre, precio, stock, categoria);

                if (_productoDB.update(producto)) {
                    JOptionPane.showMessageDialog(null, "Producto actualizado correctamente.");
                    listaProductos = CargarDatos();
                    mostrarProductoActual();
                } else {
                    JOptionPane.showMessageDialog(null, "Error al actualizar el producto.");
                }
            }
        });

        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {

                int id_Producto = Integer.parseInt(txtId_Producto.getText());

                if (_productoDB.delete(id_Producto)) {
                    JOptionPane.showMessageDialog(null, "Producto eliminado correctamente.");
                    limpiarCampos();
                    listaProductos = CargarDatos();
                } else {
                    JOptionPane.showMessageDialog(null, "Error al eliminar el Producto.");
                }
            }
        });
    }

    private List<Producto> CargarDatos() {
        _productoDB = new ProductoDB();
        List<Producto> productos = _productoDB.readAll();
        return productos;
    }

    private void mostrarProductoActual() {
        if (!listaProductos.isEmpty()) {
            Producto producto = listaProductos.get(indiceActual);
            txtId_Producto.setText(String.valueOf(producto.getId()));
            txtNombre.setText(producto.getNombre());
            txtPrecio.setText(String.valueOf(producto.getPrecio()));
            txtStock.setText(String.valueOf(producto.getStock()));
            txtCategoria.setText(producto.getCategoria());
        }
    }

    private void btnSiguienteActionPerformed(java.awt.event.ActionEvent evt) {
        if (indiceActual < listaProductos.size() - 1) {
            indiceActual++;
            mostrarProductoActual();
            disableAllFields();
        }
    }

    private void btnAnteriorActionPerformed(java.awt.event.ActionEvent evt) {
        if (indiceActual > 0) {
            indiceActual--;
            mostrarProductoActual();
            disableAllFields();
        }
    }

    // validate if the fields are not disabled
    private boolean AllFieldsAreDisabled() {
        return !txtNombre.isEnabled() && !txtPrecio.isEnabled() && !txtStock.isEnabled()
                && !txtCategoria.isEnabled();
    }

    private boolean validarCampos() {
        boolean camposValidos = true;

        if (!_validador.validarCampoVacio(txtNombre, "Nombre")) {
            camposValidos = false;
            JOptionPane.showMessageDialog(this, "Nombre no puede estar vacío", "Error", JOptionPane.ERROR_MESSAGE);
        }

        if (!_validador.validarCampoVacio(txtPrecio, "Precio")) {
            camposValidos = false;
            JOptionPane.showMessageDialog(this, "Precio no puede estar vacío", "Error", JOptionPane.ERROR_MESSAGE);
        }

        if (!_validador.validarNumeroConDosDecimales(txtPrecio.getText())) {
            camposValidos = false;
            JOptionPane.showMessageDialog(this, "Precio debe ser un número con dos decimales", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }

        if (!_validador.validarNumeroMayorQueCero(txtPrecio.getText())) {
            camposValidos = false;
            JOptionPane.showMessageDialog(this, "Precio debe ser un mayor a cero", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }

        if (!_validador.validarCampoVacio(txtStock, "Stock")) {
            camposValidos = false;
            JOptionPane.showMessageDialog(this, "Stock no puede estar vacío", "Error", JOptionPane.ERROR_MESSAGE);
        }

        if (!_validador.validarNumero(txtStock.getText())) {
            camposValidos = false;
            JOptionPane.showMessageDialog(this, "Stock debe ser un número", "Error", JOptionPane.ERROR_MESSAGE);
        }

        if (!_validador.validarNumeroMayorQueCero(txtStock.getText())) {
            camposValidos = false;
            JOptionPane.showMessageDialog(this, "Stock debe ser un mayor a cero", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }

        if (!_validador.validarCampoVacio(txtCategoria, "Categoria")) {
            camposValidos = false;
        }

        return camposValidos;
    }

    public void limpiarCampos() {
        txtNombre.setText("");
        txtPrecio.setText("");
        txtStock.setText("");
        txtCategoria.setText(null);
    }

    public void disableAllFields() {
        txtId_Producto.setEnabled(false);
        txtNombre.setEnabled(false);
        txtPrecio.setEnabled(false);
        txtStock.setEnabled(false);
        txtCategoria.setEnabled(false);
        btnEditar.setEnabled(false);
    }

    public void enableAllFields() {
        txtNombre.setEnabled(true);
        txtPrecio.setEnabled(true);
        txtStock.setEnabled(true);
        txtCategoria.setEnabled(true);
        btnEditar.setEnabled(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("Cliente App");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(600, 400);

                ProductoModGUI productoMod = new ProductoModGUI();
                frame.add(productoMod);

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

        lblId_Producto = new javax.swing.JLabel();
        txtId_Producto = new javax.swing.JTextField();
        lblNombre = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        lbPrecio = new javax.swing.JLabel();
        txtPrecio = new javax.swing.JTextField();
        txtStock = new javax.swing.JTextField();
        lblStock = new javax.swing.JLabel();
        btnAnterior = new javax.swing.JButton();
        btnSiguiente = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        lblTitulo = new javax.swing.JLabel();
        btnEnable = new javax.swing.JButton();
        lblCategoria = new javax.swing.JLabel();
        txtCategoria = new javax.swing.JTextField();

        setPreferredSize(new java.awt.Dimension(600, 400));

        lblId_Producto.setText("Id_Producto");

        lblNombre.setText("Nombre");

        lbPrecio.setText("Precio");

        lblStock.setText("Stock");

        btnAnterior.setText("< Anterior");

        btnSiguiente.setText("Siguiente >");

        btnGuardar.setText("Guardar");

        btnEditar.setText("Editar");

        btnEliminar.setText("Eliminar");

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblTitulo.setText("Producto");

        btnEnable.setText("Activar Campos!");

        lblCategoria.setText("Categoria");

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
                                                        .addComponent(lblId_Producto,
                                                                javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(lblNombre,
                                                                javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(lbPrecio,
                                                                javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(lblStock,
                                                                javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(lblCategoria,
                                                                javax.swing.GroupLayout.Alignment.TRAILING))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(txtId_Producto, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        136, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addContainerGap())
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout
                                                .createSequentialGroup()
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(txtCategoria,
                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(txtStock,
                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE, 112,
                                                                Short.MAX_VALUE)
                                                        .addComponent(txtPrecio,
                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(txtNombre))
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGap(187, 187, 187)
                                                                .addGroup(layout.createParallelGroup(
                                                                        javax.swing.GroupLayout.Alignment.TRAILING)
                                                                        .addComponent(btnSiguiente)
                                                                        .addGroup(layout.createSequentialGroup()
                                                                                .addGroup(layout.createParallelGroup(
                                                                                        javax.swing.GroupLayout.Alignment.LEADING)
                                                                                        .addComponent(btnEditar)
                                                                                        .addComponent(btnEliminar)
                                                                                        .addComponent(btnGuardar))
                                                                                .addGap(41, 41, 41)))
                                                                .addGap(35, 35, 35))
                                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout
                                                                .createSequentialGroup()
                                                                .addPreferredGap(
                                                                        javax.swing.LayoutStyle.ComponentPlacement.RELATED)
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
                                        .addComponent(lblId_Producto)
                                        .addComponent(txtId_Producto, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(lblNombre)
                                                        .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(18, 18, 18)
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(lbPrecio)
                                                        .addComponent(txtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(18, 18, 18)
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(txtStock, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(lblStock)))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(btnEliminar)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnEditar)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnGuardar)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnEnable)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblCategoria)
                                        .addComponent(txtCategoria, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 73,
                                        Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnAnterior)
                                        .addComponent(btnSiguiente))
                                .addGap(28, 28, 28)));
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAnterior;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnEnable;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnSiguiente;
    private javax.swing.JLabel lbPrecio;
    private javax.swing.JLabel lblCategoria;
    private javax.swing.JLabel lblId_Producto;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblStock;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JTextField txtCategoria;
    private javax.swing.JTextField txtId_Producto;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtPrecio;
    private javax.swing.JTextField txtStock;
    // End of variables declaration//GEN-END:variables
}
