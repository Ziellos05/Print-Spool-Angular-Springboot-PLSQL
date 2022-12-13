package in.printspool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.*;

@SpringBootApplication
@OpenAPIDefinition(servers = { @Server(url = "http://localhost:8080") },
		info = @Info(title = "Print Spool Manager", 
								version = "1.0.0",
								contact = @Contact(url = "https://github.com/Ziellos05", name = "Roland Ortega", email = "roland.ortega@segurosbolivar.com"),
								description = "This API generates the periodic print spool for a water supply network company, in addition it updates the terms and conditions of any stratum."))
// @EnableWebMvc
public class PrintSpoolApplication {

	public static void main(String[] args) {
		SpringApplication.run(PrintSpoolApplication.class, args);
	}

}
