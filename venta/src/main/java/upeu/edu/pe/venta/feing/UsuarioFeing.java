package upeu.edu.pe.venta.feing;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import upeu.edu.pe.venta.dto.Usuario;
@FeignClient(name = "usuario-service", path = "/usuario")
public interface UsuarioFeing {
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> listById(@PathVariable(required = true) Integer id);
}
