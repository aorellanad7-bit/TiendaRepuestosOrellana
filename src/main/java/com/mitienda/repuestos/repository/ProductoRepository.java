package com.mitienda.repuestos.repository;

import com.mitienda.repuestos.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {
    // ¡Literalmente vacío!
    // JpaRepository ya trae programado el guardar, buscar, eliminar, etc.
}
