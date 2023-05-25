package upeu.edu.pe.venta.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import upeu.edu.pe.venta.Entity.Venta;


public interface VentaRepository extends JpaRepository<Venta,Integer>{
    public List<Venta> findByCustomerId(Integer ventaid );
    public Venta findBySerie(String serie);
}
