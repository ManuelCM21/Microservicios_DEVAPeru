package upeu.edu.pe.venta.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import upeu.edu.pe.venta.entity.Venta;
import upeu.edu.pe.venta.service.VentaService;


@RestController
@RequestMapping("/venta")
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