package upeu.edu.pe.venta.controller;

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
import upeu.edu.pe.venta.entity.VentaDetalle;
import upeu.edu.pe.venta.service.VentaDetalleService;



@RestController
@RequestMapping("/ventadetalledev")
public class VentaDetalleController {
    @Autowired
    private VentaDetalleService ventadetalleservice;

    @GetMapping()
    public List<VentaDetalle> listar() {
        return ventadetalleservice.listar();
    }

    @PostMapping()
    public VentaDetalle guardar(@RequestBody VentaDetalle ventaDetalle) {
        return ventadetalleservice.guardar(ventaDetalle);
    }

    @GetMapping("/{id}")
    public VentaDetalle buscarPorId(@PathVariable(required = true) Integer id) {
        return ventadetalleservice.listarPorId(id).get();
    }

    @PutMapping()
    public VentaDetalle actualizar(@RequestBody VentaDetalle ventaDetalle) {
        return ventadetalleservice.actualizar(ventaDetalle);
    }

    @DeleteMapping("/{id}")
    public void eliminarPorId(@PathVariable(required = true) Integer id) {
        ventadetalleservice.eliminarPorId(id);
    }
}