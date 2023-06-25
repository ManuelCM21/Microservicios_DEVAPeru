package upeu.edu.pe.mensaje.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Entity
@Data
public class Mensaje {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nombre;
    private String correo;
    private Integer telefono;
    private String asunto;
      private String mensaje;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;

    @PrePersist
    @PreUpdate
    public void prePersistAndUpdate() {
        LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        if (fechaCreacion == null) {
            fechaCreacion = now;
        }
        fechaActualizacion = now;
    }
}
