package com.mitienda.repuestos.service;

import com.mitienda.repuestos.dto.CartItem;
import com.mitienda.repuestos.entity.Pedido;
import com.mitienda.repuestos.entity.PedidoItem;
import com.mitienda.repuestos.entity.Producto;
import com.mitienda.repuestos.repository.PedidoRepository;
import com.mitienda.repuestos.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Transactional
    public Pedido crearPedido(Pedido pedido, List<CartItem> itemsCarrito) {

        double total = 0.0;

        for (CartItem cartItem : itemsCarrito) {
            Producto producto = productoRepository.findById(cartItem.getProductoId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado: ID " + cartItem.getProductoId()));

            // Verificar stock
            if (producto.getStock() < cartItem.getCantidad()) {
                throw new RuntimeException("Stock insuficiente para: " + producto.getNombre());
            }

            // Crear item
            PedidoItem item = new PedidoItem();
            item.setProducto(producto);
            item.setCantidad(cartItem.getCantidad());
            item.setPrecioUnitario(producto.getPrecio());
            pedido.agregarItem(item);

            total += producto.getPrecio() * cartItem.getCantidad();

            // Descontar stock
            producto.setStock(producto.getStock() - cartItem.getCantidad());
            productoRepository.save(producto);
        }

        pedido.setTotal(total);
        return pedidoRepository.save(pedido);
    }

    public List<Pedido> listarTodos() {
        return pedidoRepository.findAll();
    }
}