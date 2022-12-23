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
 * archivos subidos */

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "UPLOADS", schema = "APP_DATOS_IMPRESION")
public class Upload {

	@Id
	@NotNull
	@Column(name = "ID")
	private Long id;

	@Column(name = "FILENAME")
	@NotNull
	private String filename;

	@Column(name = "CREATED")
	@NotNull
	private String created;

}
