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
    public Venta guardar(Venta venta) {
        Venta ventaDB = ventaRepository.findBySerie( venta.getSerie() );
        if (ventaDB !=null){
            return  ventaDB;
        }
        ventaDB = ventaRepository.save(venta);
        ventaDB.getDetalle().forEach( ventaItem -> {
            productoFeing.updateStockProducto( ventaItem.getProductoId(), ventaItem.getCantidad() * -1);
        });

        return ventaDB;
    }

    @Override
    public Venta actualizar(Venta venta) {
        Venta ventaDB = getventa(venta.getId());
        if (ventaDB == null){
            return  null;
        }
        ventaDB.setUsuarioId(venta.getUsuarioId());
        ventaDB.setDescripcion(venta.getDescripcion());
        ventaDB.setSerie(venta.getSerie());
        ventaDB.getDetalle().clear();
        ventaDB.setDetalle(venta.getDetalle());
        return ventaRepository.save(ventaDB);
    }

    @Override
    public Optional<Venta> listarPorId(Integer id) {
        Venta venta = ventaRepository.findById(id).get();

        
        List<VentaDetalle> ventaDetalles = venta.getDetalle().stream().map(ventaDetalle -> {
            //System.out.println(ventaDetalle.toString());
            //System.out.println("Antes de la peticion");
            Producto producto = productoFeing.getProducto(ventaDetalle.getProductoId()).getBody();
            //System.out.println("Despues de la peticion");
            //System.out.println(producto.toString());
            //System.out.println(producto.getNombre());
            ventaDetalle.setProducto(producto);
            return ventaDetalle;
        }).collect(Collectors.toList());
        venta.setDetalle(ventaDetalles);
        Usuario usuario= usuarioFeing.getusuario(venta.getUsuarioId()).getBody();
        venta.setUsuario(usuario);
        return Optional.of(venta);
    }

    @Override
    public void eliminarPorId(Integer id) {
        ventaRepository.deleteById(id);
    }

    @Override
    public Venta getventa(Integer id) {

            Venta venta= ventaRepository.findById(id).orElse(null);
            if (null != venta ){
                Usuario usuario = usuarioFeing.getusuario(venta.getUsuarioId()).getBody();
                venta.setUsuario(usuario);
                List<VentaDetalle> listItem=venta.getDetalle().stream().map(ventadetalle -> {
                    Producto producto = productoFeing.getProducto(ventadetalle.getProductoId()).getBody();
                    ventadetalle.setProducto(producto);
                    return ventadetalle;
                }).collect(Collectors.toList());
                venta.setDetalle(listItem);
            }
            return venta ;
        
    }

}