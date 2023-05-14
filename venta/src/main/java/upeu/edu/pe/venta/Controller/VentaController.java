package upeu.edu.pe.venta.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import upeu.edu.pe.venta.Entity.Venta;
import upeu.edu.pe.venta.Service.VentaService;

@RestController
@RequestMapping("/ventadev")
public class VentaController {
    @Autowired
    private VentaService ventaService;

    @GetMapping()
    public List<Venta> listar() {
        return ventaService.listar();
    }

    @PostMapping()
    public Venta guardar(@RequestBody Venta venta) {
        return ventaService.guardar(venta);
    }

    @GetMapping("/{id}")
    public Venta buscarPorId(@PathVariable(required = true) Integer id) {
        return ventaService.listarPorId(id).get();
    }

    @PutMapping()
    public Venta actualizar(@RequestBody Venta venta) {
        return ventaService.actualizar(venta);
    }

    @DeleteMapping("/{id}")
    public void eliminarPorId(@PathVariable(required = true) Integer id) {
        ventaService.eliminarPorId(id);
    }
}