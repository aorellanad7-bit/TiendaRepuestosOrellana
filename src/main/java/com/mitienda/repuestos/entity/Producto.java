package com.mitienda.repuestos.entity;
import jakarta.persistence.*;

@Entity
@Table(name = "productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // ¡Cambiado a Integer!

    private String nombre;
    private double precio;
    private int stock;

    @Column(name = "imagen_url")
    private String imagenUrl;

    @Column(name = "categoria_id")
    private Integer categoriaId;

    // --- GETTERS Y SETTERS (Para que Spring pueda leer y escribir) ---

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; } // ¡Cambiado a Integer!

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    public String getImagenUrl() { return imagenUrl; }
    public void setImagenUrl(String imagenUrl) { this.imagenUrl = imagenUrl; }

    public Integer getCategoriaId() { return categoriaId; }
    public void setCategoriaId(Integer categoriaId) { this.categoriaId = categoriaId; } // ¡EL CULPABLE CORREGIDO!
}