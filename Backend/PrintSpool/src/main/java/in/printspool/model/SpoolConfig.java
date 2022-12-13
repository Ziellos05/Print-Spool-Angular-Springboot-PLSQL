package in.printspool.model;

// Modelo para el request body a utilizar para obtener un print spool dinámico
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpoolConfig {
	
	@NotNull
	private String date;
	private boolean stratum;
	private boolean avgConsumption;
	private boolean lastConsumption;
	private int nConsumptions;
	
}