package upeu.edu.pe.catalogo.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nombre;
    private Double precio;
    private Integer stock;
    private String detalle;
    private String material;
    private String largo;
    private String ancho;
    private String alto;
    private String estado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id")
    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    private Categoria categoria;
}