package upeu.edu.pe.venta.feing;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import upeu.edu.pe.venta.dto.Producto;


@FeignClient(name = "catalogo-service",path = "/producto")

public interface ProductoFeing {
    @GetMapping("/{id}")
    public ResponseEntity<Producto> getProducto(@PathVariable(required = true) Integer id) ;

    @GetMapping(value = "/{id}/stock")
    public ResponseEntity<Producto> updateStockProducto(@PathVariable  Integer id ,@RequestParam(name = "cantidad", required = true) Double cantidad);

}
