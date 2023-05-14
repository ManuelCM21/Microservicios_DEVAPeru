package upeu.edu.pe.venta.Service.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import upeu.edu.pe.venta.Entity.Venta;
import upeu.edu.pe.venta.Repository.VentaRepository;
import upeu.edu.pe.venta.Service.VentaService;

@Service
public class VentaServiceImpl implements VentaService{
    @Autowired
    private VentaRepository ventaRepository;

    @Override
    public List<Venta>listar(){
        return ventaRepository.findAll();
    }

    @Override
    public Venta guardar(Venta venta) {
        
        return ventaRepository.save(venta);
    }

    @Override
    public Venta actualizar(Venta venta) {
        
        return ventaRepository.save(venta);
    }

    @Override
    public Optional<Venta> listarPorId(Integer id) {
        return ventaRepository.findById(id);
    }

    @Override
    public void eliminarPorId(Integer id) {
        
        ventaRepository.deleteById(id);
    }
}
