package in.printspool.model;

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
@Table
public class PrintSpool {
	
	@Id
	@NotNull
	private Long id;
	
	@NotNull
	private String paymentDue;

	@NotNull
	private float amount;

	@NotNull
	private String name;

	@NotNull
	private String address;

	private int stratum;

	@NotNull
	private float consumption;

	private float avgConsumption;

	private String last;

}
