package upeu.edu.pe.catalogo.service;

import upeu.edu.pe.catalogo.entity.Categoria;
import java.util.List;
import java.util.Optional;

public interface CategoriaService {
    public List<Categoria> listar();

    public Categoria guardar(Categoria categoria);

    public Categoria actualizar(Categoria categoria);

    public Optional<Categoria> listarPorId(Integer id);

    public void eliminarPorId(Integer id);
}
