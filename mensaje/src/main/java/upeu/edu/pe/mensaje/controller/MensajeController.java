package upeu.edu.pe.mensaje.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import upeu.edu.pe.mensaje.entity.Mensaje;
import upeu.edu.pe.mensaje.service.MensajeService;

@RestController
@RequestMapping("/mensaje")
public class MensajeController {
    @Autowired
    private MensajeService ms;

    @GetMapping()
    public ResponseEntity<List<Mensaje>> listar() {
        return ResponseEntity.ok().body(ms.list());
    }

    @PostMapping()
    public ResponseEntity<Mensaje> guardar(@RequestBody Mensaje Mensaje) {
        return ResponseEntity.ok(ms.save(Mensaje));
    }

    @PutMapping()
    public ResponseEntity<Mensaje> actualizar(@RequestBody Mensaje Mensaje) {
        return ResponseEntity.ok(ms.update(Mensaje));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Mensaje> buscarPorId(@PathVariable(required = true) Integer id) {
        return ResponseEntity.ok(ms.listById(id).get());
    }

    @DeleteMapping("/{id}")
    public String eliminar(@PathVariable(required = true) Integer id) {
        ms.deleteById(id);
        return "Eliminado";
    }

}
