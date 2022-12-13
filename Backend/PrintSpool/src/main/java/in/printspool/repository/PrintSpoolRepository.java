package in.printspool.repository;

// Interface para implementar el servicio de la generación del print spool
import java.util.List;

import in.printspool.model.PrintSpool;
import in.printspool.model.SpoolConfig;

public interface PrintSpoolRepository {

	/* S: Stratum, A: Average (Promedio) y L: Last (Últimos),
	 * en referencia a la información que se solicitará dinámicamente,
	 * todas las funciones obtienen el print spool con específicamente
	 * los campos requeridos */
	
	List<PrintSpool> getPrintSpoolSAL(SpoolConfig spoolConfig);
	
	List<PrintSpool> getPrintSpoolSA(SpoolConfig spoolConfig);
	
	List<PrintSpool> getPrintSpoolSL(SpoolConfig spoolConfig);
	
	List<PrintSpool> getPrintSpoolAL(SpoolConfig spoolConfig);
	
	List<PrintSpool> getPrintSpoolS(SpoolConfig spoolConfig);
	
	List<PrintSpool> getPrintSpoolA(SpoolConfig spoolConfig);
	
	List<PrintSpool> getPrintSpoolL(SpoolConfig spoolConfig);
	
	List<PrintSpool> getPrintSpool(SpoolConfig spoolConfig);
	
}