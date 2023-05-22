package upeu.edu.pe.venta.dto;

import lombok.Data;

@Data
public class Producto {
    private Integer id;
    private String nombre;
    private String imagen;
    private Double precio;
    private Integer stock;
    private String detalle;
    private String material;
    private String largo;
    private String ancho;
    private String alto;
    private String estado;
    private Categoria categoria;
}