package upeu.edu.pe.mensaje.entity;

import java.sql.Date;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Mensaje {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nombre;
    private Date fecha;
    private String correo;
    private Integer telefono;
    private String asunto;
}
