package upeu.edu.pe.venta.Entity;

import java.text.DateFormat;

import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class Factura {
    private Integer id;
    private DateFormat fecha;
    private String descripcion;
}
