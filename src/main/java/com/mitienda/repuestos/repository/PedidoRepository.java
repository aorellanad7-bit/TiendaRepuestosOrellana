package com.mitienda.repuestos.repository;

import com.mitienda.repuestos.entity.Pedido;
import com.mitienda.repuestos.entity.EstadoPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

    List<Pedido> findByEstado(EstadoPedido estado);
}
