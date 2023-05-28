package upeu.edu.pe.venta.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import upeu.edu.pe.venta.entity.VentaDetalle;
import upeu.edu.pe.venta.repository.VentaDetalleRepository;
import upeu.edu.pe.venta.service.VentaDetalleService;

@Service
public class VentaDetalleImpl implements VentaDetalleService{
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
