package upeu.edu.pe.venta.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import upeu.edu.pe.venta.dto.Producto;
import upeu.edu.pe.venta.dto.Usuario;
import upeu.edu.pe.venta.entity.Venta;
import upeu.edu.pe.venta.entity.VentaDetalle;
import upeu.edu.pe.venta.feing.ProductoFeing;
import upeu.edu.pe.venta.feing.UsuarioFeing;
import upeu.edu.pe.venta.repository.VentaRepository;
import upeu.edu.pe.venta.service.VentaService;


@Service
public class VentaServiceImpl implements VentaService{
    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private UsuarioFeing usuarioFeing;

    @Autowired
    private ProductoFeing productoFeing;

    @Override
    public List<Venta>listar(){
        return ventaRepository.findAll();
    }

    @Override
    public Venta guardar(Venta producto) {
        
        return ventaRepository.save(producto);
    }

    @Override
    public Venta actualizar(Venta producto) {
        
        return ventaRepository.save(producto);
    }

    @Override
    public Optional<Venta> listarPorId(Integer id) {
        Venta venta = ventaRepository.findById(id).get();

        
        List<VentaDetalle> ventaDetalles = venta.getDetalle().stream().map(ventaDetalle -> {
            //System.out.println(ventaDetalle.toString());
            //System.out.println("Antes de la peticion");
            Producto producto = productoFeing.listById(ventaDetalle.getProductoId()).getBody();
            //System.out.println("Despues de la peticion");
            //System.out.println(producto.toString());
            //System.out.println(producto.getNombre());
            ventaDetalle.setProducto(producto);
            return ventaDetalle;
        }).collect(Collectors.toList());
        venta.setDetalle(ventaDetalles);
        Usuario usuario= usuarioFeing.listById(venta.getUsuarioId()).getBody();
        venta.setUsuario(usuario);
        return Optional.of(venta);
    }

    @Override
    public void eliminarPorId(Integer id) {
        ventaRepository.deleteById(id);
    }
}