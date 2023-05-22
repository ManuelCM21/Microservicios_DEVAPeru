package upeu.edu.pe.venta.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import upeu.edu.pe.venta.entity.Venta;

public interface VentaRepository extends JpaRepository<Venta,Integer>{
    public List<Venta> findByCustomerId(Integer ventaid );
    public Venta findBySerie(String serie);
}
