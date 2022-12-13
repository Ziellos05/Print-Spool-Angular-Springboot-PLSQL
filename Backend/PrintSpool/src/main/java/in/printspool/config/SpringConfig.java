package in.printspool.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

// ARCHIVO USADO PARA CONFIGURAR VARIAS FUNCIONES EN LA APLICACIÓN

@Configuration
// Anotación que permite el uso de Scheduler de Springboot
@EnableScheduling
// Anotación que permite configurar el doc de swagger-ui generado
@OpenAPIDefinition(servers = { @Server(url = "http://localhost:8080") },
info = @Info(title = "Print Spool Manager", 
						version = "1.0.0",
						contact = @Contact(url = "https://github.com/Ziellos05", name = "Roland Ortega", email = "roland.ortega@segurosbolivar.com"),
						description = "This API generates the periodic print spool for a water supply network company, in addition it updates the terms and conditions of any stratum."))
public class SpringConfig {

}
