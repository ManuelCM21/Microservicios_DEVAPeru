package upeu.edu.pe.usuario.controller;

import upeu.edu.pe.usuario.service.UsuarioService;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import upeu.edu.pe.usuario.entity.Usuario;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
    @Autowired
    private UsuarioService UsuarioService;

    @GetMapping()
    public ResponseEntity<List<Usuario>> list() {
        return ResponseEntity.ok().body(UsuarioService.list());
    }

    @PostMapping()
    public ResponseEntity<Usuario> save(@RequestBody Usuario usuario) {
        return ResponseEntity.ok(UsuarioService.save(usuario));
    }

    @PutMapping()
    public ResponseEntity<Usuario> update(@RequestBody Usuario usuario) {
        return ResponseEntity.ok(UsuarioService.update(usuario));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> listById(@PathVariable(required = true) Integer id) {
        return ResponseEntity.ok(UsuarioService.listById(id).get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable(required = true) Integer id) {
        UsuarioService.deleteById(id);
        return ResponseEntity.ok("Eliminación Correcta");
    }
}
