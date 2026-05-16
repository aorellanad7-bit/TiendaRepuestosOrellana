package com.mitienda.repuestos.controller;

import com.mitienda.repuestos.entity.Producto;
import com.mitienda.repuestos.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;   // ← AÑADE ESTA LÍNEA
import org.springframework.web.multipart.MultipartFile;  // ← También esta para la subida
import java.io.File;
import java.util.List;
import com.mitienda.repuestos.entity.EstadoPedido;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "*")
public class ProductoController {

    @Autowired
    private ProductoRepository repositorio;

    // --- LEER (GET) ---
    @GetMapping
    public List<Producto> obtenerTodosLosProductos() {
        return repositorio.findAll();
    }

    // --- CREAR (POST) ---
    @PostMapping
    public Producto guardarProducto(@RequestBody Producto nuevoProducto) {
        return repositorio.save(nuevoProducto);
    }

    // --- ACTUALIZAR (PUT) ---
    @PutMapping("/{id}")
    public Producto actualizar(@PathVariable Integer id, @RequestBody Producto datosActualizados) {
        return repositorio.findById(id).map(producto -> {
            producto.setNombre(datosActualizados.getNombre());
            producto.setPrecio(datosActualizados.getPrecio());
            producto.setStock(datosActualizados.getStock());
            producto.setCategoriaId(datosActualizados.getCategoriaId());
            producto.setImagenUrl(datosActualizados.getImagenUrl());
            return repositorio.save(producto);
        }).orElseGet(() -> {
            datosActualizados.setId(id);
            return repositorio.save(datosActualizados);
        });
    }

    // --- ELIMINAR (DELETE) ---
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        repositorio.deleteById(id);
    }

    // AÑADE ESTO AL FINAL DE LA CLASE ProductoController
    @PostMapping("/upload")
    public ResponseEntity<String> subirImagen(@RequestParam("file") MultipartFile file) {
        try {
            String uploadDir = "src/main/resources/static/img/productos/";
            File directorio = new File(uploadDir);
            if (!directorio.exists()) {
                directorio.mkdirs();
            }

            String nombreArchivo = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            File destino = new File(uploadDir + nombreArchivo);
            file.transferTo(destino);

            return ResponseEntity.ok("/img/productos/" + nombreArchivo);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error al subir la imagen");
        }
    }


}