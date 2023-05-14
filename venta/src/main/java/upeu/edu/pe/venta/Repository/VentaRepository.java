package upeu.edu.pe.venta.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import upeu.edu.pe.venta.Entity.Venta;

public interface VentaRepository extends JpaRepository<Venta, Integer> {
    
}
