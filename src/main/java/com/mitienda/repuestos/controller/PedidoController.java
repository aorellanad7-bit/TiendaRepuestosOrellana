package com.mitienda.repuestos.controller;

import com.mitienda.repuestos.dto.CartItem;
import com.mitienda.repuestos.entity.Pedido;
import com.mitienda.repuestos.entity.EstadoPedido;
import com.mitienda.repuestos.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
@CrossOrigin(origins = "*")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private com.mitienda.repuestos.repository.PedidoRepository pedidoRepository;  // ← AÑADIDO

    @PostMapping
    public ResponseEntity<?> crearPedido(@RequestBody PedidoRequest request) { // Cambiado a <?> para enviar mensajes de error
        try {
            // Validación manual rápida para evitar errores de base de datos
            if (request.getClienteNombre() == null || request.getItems() == null || request.getItems().isEmpty()) {
                return ResponseEntity.badRequest().body("Datos incompletos");
            }

            Pedido pedido = new Pedido();
            pedido.setClienteNombre(request.getClienteNombre());
            // Si el email llega nulo, le ponemos uno por defecto para que no explote la DB
            pedido.setClienteEmail(request.getClienteEmail() != null ? request.getClienteEmail() : "sin@email.com");
            pedido.setClienteTelefono(request.getClienteTelefono());
            pedido.setDireccion(request.getDireccion());

            Pedido pedidoGuardado = pedidoService.crearPedido(pedido, request.getItems());
            return ResponseEntity.ok(pedidoGuardado);
        } catch (Exception e) {
            e.printStackTrace(); // Esto es lo que verás en los logs de Render
            return ResponseEntity.internalServerError().body("Error interno: " + e.getMessage());
        }
    }

    @GetMapping
    public List<Pedido> listarPedidos() {
        return pedidoService.listarTodos();
    }

    // ==================== CAMBIAR ESTADO ====================
    @PutMapping("/{id}/estado")
    public ResponseEntity<Pedido> cambiarEstado(
            @PathVariable Integer id,
            @RequestParam String estado) {

        return pedidoRepository.findById(id)
                .map(pedido -> {
                    pedido.setEstado(EstadoPedido.valueOf(estado.toUpperCase()));
                    return ResponseEntity.ok(pedidoRepository.save(pedido));
                })
                .orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Pedido> getPedido(@PathVariable Integer id) {
        return pedidoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}

// Clase auxiliar
class PedidoRequest {
    private String clienteNombre;
    private String clienteEmail;
    private String clienteTelefono;
    private String direccion;
    private List<CartItem> items;

    public String getClienteNombre() { return clienteNombre; }
    public void setClienteNombre(String clienteNombre) { this.clienteNombre = clienteNombre; }

    public String getClienteEmail() { return clienteEmail; }
    public void setClienteEmail(String clienteEmail) { this.clienteEmail = clienteEmail; }

    public String getClienteTelefono() { return clienteTelefono; }
    public void setClienteTelefono(String clienteTelefono) { this.clienteTelefono = clienteTelefono; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public List<CartItem> getItems() { return items; }
    public void setItems(List<CartItem> items) { this.items = items; }
}