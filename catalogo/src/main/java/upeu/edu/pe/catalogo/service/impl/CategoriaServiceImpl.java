package upeu.edu.pe.catalogo.service.impl;

import upeu.edu.pe.catalogo.entity.Categoria;
import upeu.edu.pe.catalogo.repository.CategoriaRepository;
import upeu.edu.pe.catalogo.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CategoriaServiceImpl implements CategoriaService {
    @Autowired
    private CategoriaRepository CategoriaRepository;

    @Override
    public List<Categoria> listar() {
        return CategoriaRepository.findAll();
    }

    @Override
    public Categoria guardar(Categoria categoria) {
        return CategoriaRepository.save(categoria);
    }

    @Override
    public Categoria actualizar(Categoria categoria) {
        return CategoriaRepository.save(categoria);
    }

    @Override
    public Optional<Categoria> listarPorId(Integer id) {
        return CategoriaRepository.findById(id);
    }

    @Override
    public void eliminarPorId(Integer id) {
        CategoriaRepository.deleteById(id);
    }
}