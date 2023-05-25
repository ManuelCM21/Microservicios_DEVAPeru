package upeu.edu.pe.catalogo.service.impl;

import upeu.edu.pe.catalogo.entity.Categoria;
import upeu.edu.pe.catalogo.entity.Producto;
import upeu.edu.pe.catalogo.repository.CategoriaRepository;
import upeu.edu.pe.catalogo.repository.ProductoRepository;
import upeu.edu.pe.catalogo.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class ProductoServiceImpl implements ProductoService {
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
}