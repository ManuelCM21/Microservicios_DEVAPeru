package upeu.edu.pe.catalogo.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import upeu.edu.pe.catalogo.entity.Post;
import upeu.edu.pe.catalogo.repository.PostRepository;
import upeu.edu.pe.catalogo.service.PostService;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;

    @Override
    public List<Post> listar() {
        return postRepository.findAll();
    }

    @Override
    public Post guardar(Post post) {
        return postRepository.save(post);
    }

    @Override
    public Post actualizar(Post post) {
        return postRepository.save(post);
    }

    @Override
    public Optional<Post> listarPorId(Integer id) {
        return postRepository.findById(id);
    }

    @Override
    public void eliminarPorId(Integer id) {
        postRepository.deleteById(id);
    }
}
