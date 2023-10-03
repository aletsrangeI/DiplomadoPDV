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

import Clases.Cliente;
import Clases.Validador;
import Database.ClienteDB;

/**
 *
 * @author Brandon Emmanuel Reséndiz Granados, Ruben Lora Cruz, Alejandro Avila Rangel
 * 
 */
public class ClienteModGUI extends javax.swing.JPanel {
    private Validador _validador = new Validador();
    private ClienteDB _clienteDB = new ClienteDB();
    private List<Cliente> listaClientes;
    private int indiceActual = 0;

    /**
     * Creates new form ClienteModGUI
     */
    public ClienteModGUI() {
        initComponents();
        listaClientes = CargarDatos();
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
                String nombres = txtNombre.getText();
                String apellidos = txtApellidos.getText();
                String correo = txtCorreo.getText();
                Date fechaNacimiento = dateChooser.getDate();

                Cliente cliente = new Cliente();
                cliente.setNombre(nombres);
                cliente.setApellido(apellidos);
                cliente.setCorreo(correo);
                cliente.setFechaNacimiento(fechaNacimiento);
                if (_clienteDB.insertarCliente(cliente)) {
                    JOptionPane.showMessageDialog(null, "Cliente insertado correctamente.");
                    limpiarCampos();
                    listaClientes = CargarDatos();
                } else {
                    JOptionPane.showMessageDialog(null, "Error al insertar el cliente.");
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
                String nombres = txtNombre.getText();
                String apellidos = txtApellidos.getText();
                String correo = txtCorreo.getText();
                Date fechaNacimiento = dateChooser.getDate();

                Cliente cliente = new Cliente();
                cliente.setNombre(nombres);
                cliente.setApellido(apellidos);
                cliente.setCorreo(correo);
                cliente.setFechaNacimiento(fechaNacimiento);
                cliente.setId_Cliente(Integer.parseInt(txtId_Cliente.getText()));

                if (_clienteDB.ActualizarCliente(cliente)) {
                    JOptionPane.showMessageDialog(null, "Cliente actualizado correctamente.");
                    listaClientes = CargarDatos();
                    mostrarClienteActual();
                } else {
                    JOptionPane.showMessageDialog(null, "Error al actualizar el cliente.");
                }
            }
        });

        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {

                int id_Cliente = Integer.parseInt(txtId_Cliente.getText());

                if (_clienteDB.EliminarCliente(id_Cliente)) {
                    JOptionPane.showMessageDialog(null, "Cliente eliminado correctamente.");
                    limpiarCampos();
                    listaClientes = CargarDatos();
                } else {
                    JOptionPane.showMessageDialog(null, "Error al eliminar el cliente.");
                }
            }
        });
    }

    private List<Cliente> CargarDatos() {
        _clienteDB = new ClienteDB();
        List<Cliente> clientes = _clienteDB.GetClientes();
        return clientes;
    }

    private void mostrarClienteActual() {
        if (!listaClientes.isEmpty()) {
            Cliente cliente = listaClientes.get(indiceActual);
            txtId_Cliente.setText(String.valueOf(cliente.getId_Cliente()));
            txtNombre.setText(cliente.getNombre());
            txtApellidos.setText(cliente.getApellido());
            txtCorreo.setText(cliente.getCorreo());
            dateChooser.setDate(cliente.getFechaNacimiento());
        }
    }

    private void btnSiguienteActionPerformed(java.awt.event.ActionEvent evt) {
        if (indiceActual < listaClientes.size() - 1) {
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
        return !txtNombre.isEnabled() && !txtApellidos.isEnabled() && !txtCorreo.isEnabled()
                && !dateChooser.isEnabled();
    }

    private boolean validarCampos() {
        boolean camposValidos = true;

        // Validar el campo txtNombres
        if (!_validador.validarCampoVacio(txtNombre, "Nombres")) {
            camposValidos = false;
        }

        // Validar el campo txtApellidos
        if (!_validador.validarCampoVacio(txtApellidos, "Apellidos")) {
            camposValidos = false;
        }

        // Validar el campo txtCorreos
        if (!_validador.validarCampoVacio(txtCorreo, "Correo")) {
            camposValidos = false;
        } else {
            if (!_validador.validarCorreo(txtCorreo.getText())) {
                camposValidos = false;
                JOptionPane.showMessageDialog(this, "El formato del correo electrónico no es válido.",
                        "Error de validación", JOptionPane.ERROR_MESSAGE);
            }
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
        txtNombre.setText("");
        txtApellidos.setText("");
        txtCorreo.setText("");
        dateChooser.setDate(null);
    }

    public void disableAllFields() {
        txtId_Cliente.setEnabled(false);
        txtNombre.setEnabled(false);
        txtApellidos.setEnabled(false);
        txtCorreo.setEnabled(false);
        dateChooser.setEnabled(false);
        btnEditar.setEnabled(false);
    }

    public void enableAllFields() {
        txtNombre.setEnabled(true);
        txtApellidos.setEnabled(true);
        txtCorreo.setEnabled(true);
        dateChooser.setEnabled(true);
        btnEditar.setEnabled(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("Cliente App");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(600, 400);

                ClienteModGUI clienteMod = new ClienteModGUI();
                frame.add(clienteMod);

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
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblId_Cliente = new javax.swing.JLabel();
        txtId_Cliente = new javax.swing.JTextField();
        lblNombre = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        lblApellido = new javax.swing.JLabel();
        txtApellidos = new javax.swing.JTextField();
        txtCorreo = new javax.swing.JTextField();
        lblCorreo = new javax.swing.JLabel();
        dateChooser = new com.toedter.calendar.JDateChooser();
        lblFechaNacimiento = new javax.swing.JLabel();
        btnAnterior = new javax.swing.JButton();
        btnSiguiente = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        lblTitulo = new javax.swing.JLabel();
        btnEnable = new javax.swing.JButton();

        setPreferredSize(new java.awt.Dimension(600, 400));

        lblId_Cliente.setText("Id_Cliente");

        lblNombre.setText("Nombre");

        lblApellido.setText("Apellidos");

        lblCorreo.setText("Correo");

        lblFechaNacimiento.setText("Fecha Nacimiento");

        btnAnterior.setText("< Anterior");

        btnSiguiente.setText("Siguiente >");

        btnGuardar.setText("Guardar");

        btnEditar.setText("Editar");

        btnEliminar.setText("Eliminar");

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblTitulo.setText("Cliente");

        btnEnable.setText("Activar Campos!");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(40, 40, 40)
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(lblFechaNacimiento)
                                                        .addComponent(btnAnterior))
                                                .addGap(16, 16, 16))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout
                                                .createSequentialGroup()
                                                .addContainerGap()
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(lblId_Cliente,
                                                                javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(lblNombre,
                                                                javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(lblApellido,
                                                                javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(lblCorreo,
                                                                javax.swing.GroupLayout.Alignment.TRAILING))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(txtCorreo,
                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(txtApellidos,
                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(txtNombre)
                                                        .addComponent(dateChooser, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                112, Short.MAX_VALUE))
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
                                                                .addGap(53, 53, 53))))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(txtId_Cliente, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        136, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                                        .addComponent(lblId_Cliente)
                                        .addComponent(txtId_Cliente, javax.swing.GroupLayout.PREFERRED_SIZE,
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
                                                        .addComponent(lblApellido)
                                                        .addComponent(txtApellidos,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(18, 18, 18)
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(txtCorreo, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(lblCorreo))
                                                .addGap(27, 27, 27)
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(dateChooser,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(lblFechaNacimiento)))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(btnEliminar)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnEditar)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnGuardar)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnEnable)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 66,
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
    private com.toedter.calendar.JDateChooser dateChooser;
    private javax.swing.JLabel lblApellido;
    private javax.swing.JLabel lblCorreo;
    private javax.swing.JLabel lblFechaNacimiento;
    private javax.swing.JLabel lblId_Cliente;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JTextField txtApellidos;
    private javax.swing.JTextField txtCorreo;
    private javax.swing.JTextField txtId_Cliente;
    private javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables
}
