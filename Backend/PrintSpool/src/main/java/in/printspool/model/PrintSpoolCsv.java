package in.printspool.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/* Modelo para la información que se agregará y recibirá de la tabla que hace seguimiento a los
 * archivos creados */

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "PRINTSPOOLS", schema = "APP_DATOS_IMPRESION")
public class PrintSpoolCsv {
	
	@Id
	@NotNull
	@Column (name = "ID")
	private Long id;
	 
	@Column (name = "PERIOD")
	@NotNull
	private String period;
	
	@Column (name = "LINK")
	@NotNull
	private String link;
	
	@Column (name = "CREATED")
	@NotNull
	private String created;
	

}

