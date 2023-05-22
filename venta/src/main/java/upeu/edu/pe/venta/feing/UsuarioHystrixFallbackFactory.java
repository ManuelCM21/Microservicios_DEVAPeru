package upeu.edu.pe.venta.feing;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import upeu.edu.pe.venta.dto.Usuario;

@Component
public class UsuarioHystrixFallbackFactory implements UsuarioFeing{

    public ResponseEntity<Usuario> getusuario(Integer id) {
        Usuario usuario = Usuario.builder()
                .nombre("none")
                .dni("none")
                .correo("none")
                .contraseña("none").rol("none").build();
        return ResponseEntity.ok(usuario);
    }

}
