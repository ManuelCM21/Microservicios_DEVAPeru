package upeu.edu.pe.venta.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import upeu.edu.pe.venta.Entity.VentaDetalle;



public interface VentaDetalleRepository extends JpaRepository<VentaDetalle, Integer> {
    
}
