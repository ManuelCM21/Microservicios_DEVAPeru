package upeu.edu.pe.mensaje.service.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upeu.edu.pe.mensaje.entity.Mensaje;
import upeu.edu.pe.mensaje.repository.MensajeRepository;
import upeu.edu.pe.mensaje.service.MensajeService;

@Service
public class MensajeServiceImpl implements MensajeService {
    
    @Autowired
    private MensajeRepository mr;

    @Override
    public List<Mensaje> list(){
        return mr.findAll();
    }

    @Override
    public Mensaje save(Mensaje mensaje) {
        return mr.save(mensaje);
    }

    @Override
    public Mensaje update(Mensaje mensaje) {
        return mr.save(mensaje);
    }
    
    @Override
    public Optional<Mensaje> listById(Integer id) {
        return mr.findById(id);
    }

    @Override
    public void deleteById(Integer id) {
        mr.deleteById(id);
    }
}


