package upeu.edu.pe.catalogo.repository;

import upeu.edu.pe.catalogo.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
public interface ProductoRepository extends JpaRepository<Producto,Integer> {
}