// package Database;

// import java.sql.ResultSet;
// import java.text.SimpleDateFormat;
// import java.util.ArrayList;
// import java.util.List;

// import Clases.Cliente;
// /**
//  *
//  * @author Brandon Emmanuel Reséndiz Granados, Ruben Lora Cruz, Alejandro Avila Rangel
//  * 
//  */
// public class ClienteDBAccess {
//     public AccessHandler _dbHandler = new AccessHandler();

//     public ClienteDBAccess() {
//         _dbHandler = new AccessHandler();
//     }

//     public boolean insertarCliente(Cliente cliente) {
//         boolean inserted = false;

//         SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//         String fechaFormateada = dateFormat.format(cliente.getFechaNacimiento());
//         try {
//             String query = "INSERT INTO Cliente (Nombre, Apellido, Correo, FechaNacimiento) VALUES ("
//                     + "'" + cliente.getNombre() + "', '" + cliente.getApellido() + "', '"
//                     + cliente.getCorreo() + "', '" + fechaFormateada + "')";
//             if (_dbHandler.conectar()) {
//                 _dbHandler.ejecutarComando(query);
//                 _dbHandler.desconectar();
//                 inserted = true;
//             }
//         } catch (Exception e) {
//             System.out.println("Error: " + e.getMessage());
//         }
//         return inserted;
//     }

//     public List<Cliente> GetClientes() {
//         List<Cliente> clientes = new ArrayList<>();
//         ResultSet resultSet = null;
//         try {
//             String query = "SELECT * FROM Cliente";
//             if (_dbHandler.conectar()) {
//                 resultSet = _dbHandler.leerDatos(query);
//                 _dbHandler.desconectar();

//                 while (resultSet.next()) {
//                     Cliente cliente = new Cliente(); // create a new instance for each record
//                     cliente.setId_Cliente(resultSet.getInt("Id_Cliente"));
//                     cliente.setNombre(resultSet.getString("Nombre"));
//                     cliente.setApellido(resultSet.getString("Apellido"));
//                     cliente.setCorreo(resultSet.getString("Correo"));
//                     cliente.setFechaNacimiento(resultSet.getDate("FechaNacimiento"));
//                     clientes.add(cliente);
//                 }
//             }
//         } catch (Exception e) {
//             System.out.println("Error: " + e.getMessage());
//         }
//         return clientes;
//     }

//     public boolean EliminarCliente(int Id) {
//         boolean deleted = false;
//         try {
//             String query = "DELETE FROM Cliente WHERE Id_Cliente = " + Id;
//             if (_dbHandler.conectar()) {
//                 _dbHandler.ejecutarComando(query);
//                 _dbHandler.desconectar();
//                 deleted = true;
//             }
//         } catch (Exception e) {
//             System.out.println("Error: " + e.getMessage());
//         }
//         return deleted;
//     }

//     public boolean ActualizarCliente(Cliente cliente) {
//         boolean updated = false;
//         SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//         String fechaFormateada = dateFormat.format(cliente.getFechaNacimiento());

//         try {
//             String query = "UPDATE Cliente SET Nombre = '" + cliente.getNombre() + "', Apellido = '"
//                     + cliente.getApellido() + "', Correo = '" + cliente.getCorreo() + "', FechaNacimiento = '"
//                     + fechaFormateada + "' WHERE Id_Cliente = " + cliente.getId_Cliente();
//             if (_dbHandler.conectar()) {
//                 _dbHandler.ejecutarComando(query);
//                 _dbHandler.desconectar();
//                 updated = true;
//             }
//         } catch (Exception e) {
//             System.out.println("Error: " + e.getMessage());
//         }
//         return updated;
//     }

//     public Cliente GetClienteById(int Id) {
//         Cliente cliente = new Cliente();
//         ResultSet resultSet = null;
//         try {
//             String query = "SELECT * FROM Cliente WHERE Id_Cliente = " + Id;
//             if (_dbHandler.conectar()) {
//                 resultSet = _dbHandler.leerDatos(query);
//                 _dbHandler.desconectar();

//                 while (resultSet.next()) {
//                     cliente.setNombre(resultSet.getString("Nombre"));
//                     cliente.setApellido(resultSet.getString("Apellido"));
//                     cliente.setCorreo(resultSet.getString("Correo"));
//                     cliente.setFechaNacimiento(resultSet.getDate("FechaNacimiento"));
//                 }
//             }
//         } catch (Exception e) {
//             System.out.println("Error: " + e.getMessage());
//         }
//         return cliente;
//     }
// }
