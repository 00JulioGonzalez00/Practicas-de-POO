package gm.zona_fit.controlador;

import gm.zona_fit.modelo.Cliente;
import gm.zona_fit.servicio.IClienteServicio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api")
public class GestionClientesFlores {

    @Autowired
    private IClienteServicio clienteServicio;

    private static final Logger logger = LoggerFactory.getLogger(GestionClientesFlores.class);

    @GetMapping("/refrescarClientes")
    public String refrescarClientes() {
        List<Cliente> clientes = clienteServicio.listarCliente();

        if (clientes.isEmpty()) {
            logger.info("No se encontraron clientes en la base de datos.");
            return "No se encontraron clientes en la base de datos.";
        } else {
            clientes.forEach(cliente -> logger.info(cliente.toString()));
            return "Clientes actualizados correctamente. Revisa la consola para los detalles.";
        }
    }

    // 👇 ESTE es el que necesitas para que funcione el frontend
    @GetMapping("/clientes")
    public List<Cliente> obtenerClientes() {
        return clienteServicio.listarCliente();
    }

    @PostMapping("/clientes")
    public ResponseEntity<String> guardarCliente(@RequestBody Cliente cliente) {
        if (cliente.getNombre() == null || cliente.getApellido() == null || cliente.getMembresia() == null) {
            return ResponseEntity.badRequest().body("Todos los campos son obligatorios.");
        }

        clienteServicio.guardarCliente(cliente);
        return ResponseEntity.ok("Cliente guardado correctamente.");
    }

    // Obtener cliente por ID (para editar)
    @GetMapping("/clientes/{id}")
    public ResponseEntity<Cliente> obtenerCliente(@PathVariable Integer id) {
        Cliente cliente = clienteServicio.buscarClienteId(id);
        if (cliente != null) {
            return ResponseEntity.ok(cliente);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Actualizar cliente existente
    @PutMapping("/clientes/{id}")
    public ResponseEntity<String> actualizarCliente(@PathVariable Integer id, @RequestBody Cliente clienteActualizado) {
        Cliente existente = clienteServicio.buscarClienteId(id);
        if (existente == null) {
            return ResponseEntity.notFound().build();
        }

        existente.setNombre(clienteActualizado.getNombre());
        existente.setApellido(clienteActualizado.getApellido());
        existente.setMembresia(clienteActualizado.getMembresia());

        clienteServicio.guardarCliente(existente);
        return ResponseEntity.ok("Cliente actualizado correctamente.");
    }

    // Eliminar cliente
    @DeleteMapping("/clientes/{id}")
    public ResponseEntity<String> eliminarCliente(@PathVariable Integer id) {
        Cliente cliente = clienteServicio.buscarClienteId(id);
        if (cliente == null) {
            return ResponseEntity.notFound().build();
        }

        clienteServicio.eliminarCliente(cliente);
        return ResponseEntity.ok("Cliente eliminado correctamente.");
    }


}