package upeu.edu.pe.venta.Service.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import upeu.edu.pe.venta.Entity.VentaDetalle;
import upeu.edu.pe.venta.Repository.VentaDetalleRepository;
import upeu.edu.pe.venta.Service.VentaDetalleService;

public class VentaDetalleServiceImpl implements VentaDetalleService{
    @Autowired
    private VentaDetalleRepository ventaRepository;

    @Override
    public List<VentaDetalle>listar(){
        return ventaRepository.findAll();
    }

    @Override
    public VentaDetalle guardar(VentaDetalle ventaDetalle) {
        
        return ventaRepository.save(ventaDetalle);
    }

    @Override
    public VentaDetalle actualizar(VentaDetalle ventaDetalle) {
        
        return ventaRepository.save(ventaDetalle);
    }

    @Override
    public Optional<VentaDetalle> listarPorId(Integer id) {
        return ventaRepository.findById(id);
    }

    @Override
    public void eliminarPorId(Integer id) {
        
        ventaRepository.deleteById(id);
    }
}
