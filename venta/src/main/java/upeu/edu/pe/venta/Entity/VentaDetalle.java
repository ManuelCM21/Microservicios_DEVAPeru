package upeu.edu.pe.venta.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.Data;
import upeu.edu.pe.venta.dto.Producto;

@Entity
@Data
public class VentaDetalle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Double cantidad;
    private Double precio;
    private Integer productoId;
    @Transient
    private Producto producto;

    public VentaDetalle() {
        this.cantidad = (double) 0;
        this.precio = (double) 0;
    }

}
