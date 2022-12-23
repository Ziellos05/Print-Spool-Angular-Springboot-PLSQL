package in.printspool.repository;

// Interfase para implementar el servicio de la generación de facturas
public interface BillsRepository {

	// Generación de la facturación hasta la actualidad
	int save();

}