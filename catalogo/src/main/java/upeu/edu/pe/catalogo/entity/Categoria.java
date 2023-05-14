package upeu.edu.pe.catalogo.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nombre;
    private String estado;
}