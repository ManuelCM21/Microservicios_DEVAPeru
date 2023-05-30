package upeu.edu.pe.catalogo.repository;

import upeu.edu.pe.catalogo.entity.Imagen;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ImagenRepository extends JpaRepository<Imagen, Integer> {
}