package upeu.edu.pe.venta.Service;

import java.util.List;
import java.util.Optional;

import upeu.edu.pe.venta.Entity.Venta;


public interface VentaService {
    public List<Venta> listar();
    public Venta guardar (Venta venta);
    public Venta actualizar (Venta venta);
    public Optional<Venta>listarPorId(Integer id);
    public void eliminarPorId(Integer id);
}
