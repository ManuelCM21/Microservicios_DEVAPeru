package upeu.edu.pe.venta.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import upeu.edu.pe.venta.dto.Producto;


@FeignClient(name = "catalogo-service",path = "/producto")

public interface ProductoFeign {
    @GetMapping("/{id}")
    public ResponseEntity<Producto> listById(@PathVariable(required = true) Integer id) ;
}
