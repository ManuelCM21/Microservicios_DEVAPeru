package upeu.edu.pe.catalogo.service;

import upeu.edu.pe.catalogo.entity.Post;

import java.util.List;
import java.util.Optional;

public interface PostService {
    public List<Post> listar();

    public Post guardar(Post post);

    public Post actualizar(Post post);

    public Optional<Post> listarPorId(Integer id);

    public void eliminarPorId(Integer id);
}
