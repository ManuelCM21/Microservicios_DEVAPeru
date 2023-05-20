package upeu.edu.pe.mensaje.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import upeu.edu.pe.mensaje.entity.Mensaje;

public interface MensajeRepository extends JpaRepository<Mensaje, Integer>{
    
}
