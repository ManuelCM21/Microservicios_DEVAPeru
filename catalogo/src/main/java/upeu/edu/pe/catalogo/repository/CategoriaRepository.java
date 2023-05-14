package upeu.edu.pe.catalogo.repository;

import upeu.edu.pe.catalogo.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
}