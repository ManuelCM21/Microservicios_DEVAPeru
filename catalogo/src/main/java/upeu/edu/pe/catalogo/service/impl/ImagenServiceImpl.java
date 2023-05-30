package upeu.edu.pe.catalogo.service.impl;

import upeu.edu.pe.catalogo.entity.Imagen;
import upeu.edu.pe.catalogo.repository.ImagenRepository;
import upeu.edu.pe.catalogo.service.ImagenService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ImagenServiceImpl implements ImagenService {
    @Autowired
    private ImagenRepository imagenRepository;

    @Override
    public List<Imagen> listar() {
        return imagenRepository.findAll();
    }

    @Override
    public Imagen guardar(Imagen imagen) {
        return imagenRepository.save(imagen);
    }

    @Override
    public Imagen actualizar(Imagen imagen) {
        return imagenRepository.save(imagen);
    }

    @Override
    public Optional<Imagen> listarPorId(Integer id) {
        return imagenRepository.findById(id);
    }

    @Override
    public void eliminarPorId(Integer id) {
        imagenRepository.deleteById(id);
    }
}