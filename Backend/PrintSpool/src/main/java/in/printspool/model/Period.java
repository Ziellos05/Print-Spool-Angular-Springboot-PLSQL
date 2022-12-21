package in.printspool.model;

// Modelo para los estratos, relacionado con la tabla STRATUMS en la base de datos
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "PERIODS", schema = "APP_DATOS_IMPRESION")
public class Period {
	
	@Id
	@NotNull
	@Column (name = "ID")
	private Long id;
	 
	@Column (name = "MONTH_YEAR")
	@NotNull
	private String monthYear;
	

}