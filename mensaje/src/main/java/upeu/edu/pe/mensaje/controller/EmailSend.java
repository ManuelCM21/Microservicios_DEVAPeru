package upeu.edu.pe.mensaje.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import upeu.edu.pe.mensaje.entity.Mensaje;
import upeu.edu.pe.mensaje.service.EmailSender;

@RestController
public class EmailSend {

    @Autowired
    private EmailSender emailService;

    @PostMapping("/enviar-correo")
    public void enviarCorreo(@RequestBody Mensaje correoDTO) {
        String destinatario = correoDTO.getCorreo();
        String asunto = correoDTO.getAsunto();
        String contenido = correoDTO.getMensaje();
        emailService.enviarCorreo(destinatario, asunto, contenido);
    }
}
