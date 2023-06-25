package upeu.edu.pe.mensaje;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@SpringBootApplication
public class MensajeApplication {

	public static void main(String[] args) {
		SpringApplication.run(MensajeApplication.class, args);
	}
	@Bean
    public OpenAPI custoOpenAPI() {
        return new OpenAPI().info(new Info()
                .title("OPEN API MICROSERVICIOS Mensaje")
                .version("0.0.1")
                .description("servicio web catalogo")
                .termsOfService("http://swagger.io/terms")
                .license(new License().name("Apache 2.0").url("http://springdoc.org"))
        );
    }
    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        
        // Configura las propiedades del mailSender, como el host, credenciales, etc.
        mailSender.setHost("smtp.gmail.com");
        mailSender.setUsername("martinhilasaca7@gmail.com");
        mailSender.setPassword("yxjitdpujjsbdvec");
        
        // Configura otras propiedades, como el protocolo, autenticación, inicio seguro, etc.
        
        return mailSender;
    }

}
