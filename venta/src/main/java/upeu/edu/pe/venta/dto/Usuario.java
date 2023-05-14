package upeu.edu.pe.venta.dto;

import lombok.Data;

@Data
public class Usuario {
    private Integer id;
    private String nombre;
    private String dni;
    private String correo;
    private String contraseña;
    private String rol;
}
