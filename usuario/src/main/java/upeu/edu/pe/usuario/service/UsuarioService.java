package upeu.edu.pe.usuario.service;

import java.util.List;
import java.util.Optional;

import upeu.edu.pe.usuario.entity.Usuario;

public interface UsuarioService {
    public List<Usuario> list();

    public Usuario save(Usuario usuario);

    public Usuario update(Usuario usuario);

    public Optional<Usuario> listById(Integer id);

    public void deleteById(Integer id);
}
