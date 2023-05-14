package upeu.edu.pe.usuario.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import upeu.edu.pe.usuario.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

}
