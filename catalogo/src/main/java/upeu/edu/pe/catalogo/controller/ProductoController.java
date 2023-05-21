package upeu.edu.pe.catalogo.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import upeu.edu.pe.catalogo.entity.Producto;
import upeu.edu.pe.catalogo.service.ProductoService;

@RestController
@RequestMapping("/producto")
public class ProductoController {
    @Autowired
    private ProductoService productoService;

    @GetMapping()
    public List<Producto> listar() {
        List<Producto> productos = productoService.listar();
        for (Producto producto : productos) {
            producto.setCategoria(producto.getCategoria());
        }
        return productos;
    }

    @PostMapping()
    public Producto guardar(@RequestBody Producto producto) {
        producto.setCategoria(producto.getCategoria());
        return productoService.guardar(producto);
    }

    @PostMapping("/imagen/{id}")
    public ResponseEntity<String> cargarImagen(@PathVariable Integer id, @RequestParam("imagen") MultipartFile archivo) {
        try {
            productoService.guardarImagen(id, archivo);
            return ResponseEntity.ok("Imagen cargada correctamente.");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al cargar la imagen.");
        }
    }

    @GetMapping("/{id}")
    public Producto buscarPorId(@PathVariable(required = true) Integer id) {
        Producto producto = productoService.listarPorId(id).get();
        producto.setCategoria(producto.getCategoria());
        return producto;
    }

    @PutMapping()
    public Producto actualizar(@RequestBody Producto producto) {
        producto.setCategoria(producto.getCategoria());
        return productoService.actualizar(producto);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable(required = true) Integer id) {
        productoService.eliminarPorId(id);
    }
}