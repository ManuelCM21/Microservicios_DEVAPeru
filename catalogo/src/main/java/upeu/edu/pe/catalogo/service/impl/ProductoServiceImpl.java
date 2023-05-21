package upeu.edu.pe.catalogo.service.impl;

import upeu.edu.pe.catalogo.entity.Categoria;
import upeu.edu.pe.catalogo.entity.Producto;
import upeu.edu.pe.catalogo.repository.CategoriaRepository;
import upeu.edu.pe.catalogo.repository.ProductoRepository;
import upeu.edu.pe.catalogo.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.apache.commons.io.FilenameUtils;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class ProductoServiceImpl implements ProductoService {
    private static final String DIRECTORIO_IMAGENES = "/imagenes";
    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Override
    public List<Producto> listar() {
        return productoRepository.findAll();
    }

    @Override
    public Producto guardar(Producto producto) {
        Categoria categoria = categoriaRepository.findById(producto.getCategoria().getId())
                .orElse(null);
        if (categoria == null) {
            categoria = categoriaRepository.save(producto.getCategoria());
        }
        producto.setCategoria(categoria);
        return productoRepository.save(producto);
    }

    @Override
    public Producto actualizar(Producto producto) {
        Categoria categoria = categoriaRepository.findById(producto.getCategoria().getId())
                .orElse(null);
        if (categoria == null) {
            categoria = categoriaRepository.save(producto.getCategoria());
        }
        producto.setCategoria(categoria);
        return productoRepository.save(producto);
    }

    @Override
    public Optional<Producto> listarPorId(Integer id) {
        return productoRepository.findById(id);
    }

    @Override
    public void eliminarPorId(Integer id) {
        productoRepository.deleteById(id);
    }

    @Override
    public void guardarImagen(Integer id, MultipartFile archivo) throws IOException {
        Producto producto = listarPorId(id).orElse(null);
        if (producto != null) {
            String nombreArchivo = generarNombreArchivo(archivo.getOriginalFilename());
            producto.setImagen(nombreArchivo);
            productoRepository.save(producto);

            // Guardar el archivo en el sistema dearchivos
guardarArchivo(nombreArchivo, archivo);
} else {
throw new IllegalArgumentException("Producto no encontrado.");
}
}

private void guardarArchivo(String nombreArchivo, MultipartFile archivo) throws IOException {
    Path rutaArchivo = Paths.get(DIRECTORIO_IMAGENES).resolve(nombreArchivo);
    Files.copy(archivo.getInputStream(), rutaArchivo, StandardCopyOption.REPLACE_EXISTING);
}

private String generarNombreArchivo(String nombreOriginal) {
    String extension = FilenameUtils.getExtension(nombreOriginal);
    String nombreSinExtension = FilenameUtils.removeExtension(nombreOriginal);
    String nombreUnico = UUID.randomUUID().toString();
    return nombreSinExtension + "_" + nombreUnico + "." + extension;
}
}