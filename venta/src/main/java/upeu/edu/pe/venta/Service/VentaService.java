package upeu.edu.pe.venta.service;

import java.util.List;
import java.util.Optional;

import upeu.edu.pe.venta.entity.Venta;

public interface VentaService {
    public List<Venta> listar();
    public Venta guardar (Venta venta);
    public Venta actualizar (Venta venta);
    public Optional<Venta>listarPorId(Integer id);
    public void eliminarPorId(Integer id);
}