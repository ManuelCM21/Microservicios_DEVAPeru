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
import upeu.edu.pe.venta.Entity.VentaDetalle;
import upeu.edu.pe.venta.Service.VentaDetalleService;

@RestController
@RequestMapping("/ventadetalle")
public class VentaDetalleController {
    @Autowired
    private VentaDetalleService ventaDetalleService;

    @GetMapping()
    public List<VentaDetalle> listar() {
        return ventaDetalleService.listar();
    }

    @PostMapping()
    public VentaDetalle guardar(@RequestBody VentaDetalle ventaDetalle) {
        return ventaDetalleService.guardar(ventaDetalle);
    }

    @GetMapping("/{id}")
    public VentaDetalle buscarPorId(@PathVariable(required = true) Integer id) {
        return ventaDetalleService.listarPorId(id).get();
    }

    @PutMapping()
    public VentaDetalle actualizar(@RequestBody VentaDetalle ventaDetalle) {
        return ventaDetalleService.actualizar(ventaDetalle);
    }

    @DeleteMapping("/{id}")
    public void eliminarPorId(@PathVariable(required = true) Integer id) {
        ventaDetalleService.eliminarPorId(id);
    }
}
