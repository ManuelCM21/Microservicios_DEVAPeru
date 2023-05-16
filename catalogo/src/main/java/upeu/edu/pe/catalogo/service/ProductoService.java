package upeu.edu.pe.catalogo.service;
import upeu.edu.pe.catalogo.entity.Producto;

import java.util.List;
import java.util.Optional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ProductoService {
    public List<Producto> listar();

    public Producto guardar(Producto producto);

    public Producto actualizar(Producto producto);

    public Optional<Producto> listarPorId(Integer id);

    public void eliminarPorId(Integer id);

    void guardarImagen(Integer id, MultipartFile archivo) throws IOException;
}
