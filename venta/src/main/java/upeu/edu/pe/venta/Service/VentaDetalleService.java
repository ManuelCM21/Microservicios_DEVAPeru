package upeu.edu.pe.venta.service;

import java.util.List;
import java.util.Optional;

import upeu.edu.pe.venta.entity.VentaDetalle;



public interface VentaDetalleService {
    public List<VentaDetalle> listar();
    public VentaDetalle guardar (VentaDetalle ventaDetalle);
    public VentaDetalle actualizar (VentaDetalle ventaDetalle);
    public Optional<VentaDetalle>listarPorId(Integer id);
    public void eliminarPorId(Integer id);
}
