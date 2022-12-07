package in.printspool.repository;

import java.util.List;

import in.printspool.model.PrintSpool;
import in.printspool.model.SpoolConfig;

public interface PrintSpoolRepository {

	List<PrintSpool> getPrintSpoolSAL(SpoolConfig spoolConfig);
	
	List<PrintSpool> getPrintSpoolSA(SpoolConfig spoolConfig);
	
	List<PrintSpool> getPrintSpoolSL(SpoolConfig spoolConfig);
	
	List<PrintSpool> getPrintSpoolAL(SpoolConfig spoolConfig);
	
	List<PrintSpool> getPrintSpoolS(SpoolConfig spoolConfig);
	
	List<PrintSpool> getPrintSpoolA(SpoolConfig spoolConfig);
	
	List<PrintSpool> getPrintSpoolL(SpoolConfig spoolConfig);
	
	List<PrintSpool> getPrintSpool(SpoolConfig spoolConfig);
	
}