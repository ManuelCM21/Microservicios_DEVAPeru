package upeu.edu.pe.venta.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import upeu.edu.pe.venta.entity.Venta;


public interface VentaRepository extends JpaRepository<Venta, Integer> {
    
}
