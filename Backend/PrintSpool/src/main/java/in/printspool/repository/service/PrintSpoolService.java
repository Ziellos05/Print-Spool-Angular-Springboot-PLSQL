package in.printspool.repository.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import in.printspool.model.PrintSpool;
import in.printspool.model.PrintSpoolCsv;
import in.printspool.model.SpoolConfig;
import in.printspool.repository.PrintSpoolRepository;

// Servicio encargado de generar el print spool dinámico, utiliza JDBC
@Repository
public class PrintSpoolService implements PrintSpoolRepository {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	String standardQuery1 = "SELECT b.ID, "
			+ "TO_CHAR(b.PAYMENT_DUE, 'DD-MM-YYYY')"
			+ "AS PAYMENTDUE, "
			+ "b.AMOUNT, "
			+ "c.NAME, "
			+ "c.ADDRESS, "
			+ "c2.CUBIC_METERS "
			+ "AS CONSUMPTION ";
	String standardQuery2 = "FROM APP_DATOS_IMPRESION.BILLS b "
			+ "INNER JOIN APP_DATOS_IMPRESION.CONSUMPTIONS c2 "
			+ "ON c2.ID = b.CONSUMPTION_ID "
			+ "INNER JOIN APP_DATOS_IMPRESION.CLIENTS c "
			+ "ON c.ID = c2.CLIENT_ID "
			+ "INNER JOIN APP_DATOS_IMPRESION.PERIODS p "
			+ "ON p.ID = c2.PERIOD_ID "
			+ "WHERE p.MONTH_YEAR = ? "
			+ "ORDER BY b.DELIVERY_RATE";
	String S = ", c.STRATUM_ID "
			+ "AS STRATUM ";
	String A = ", APP_DATOS_IMPRESION.print_spool.CAL_AVG(c.ID, ?) "
			+ "AS AVG_CONSUMPTION ";
	String L = ", APP_DATOS_IMPRESION.PRINT_SPOOL.GET_LAST_Z(c.ID, ?) "
			+ "AS LAST ";
	
	
	
	/* S: Stratum, A: Average (Promedio) y L: Last (Últimos),
	 * en referencia a la información que se solicitará dinámicamente,
	 * todas las funciones obtienen el print spool con específicamente
	 * los campos requeridos, se utiliza JDBC para poder enviar queries
	 * complejas a la base de datos Oracle*/
	@Override
	public List<PrintSpool> getPrintSpoolSAL(SpoolConfig spoolConfig) {
		
		return jdbcTemplate.query(standardQuery1+S+A+L+standardQuery2, 
				new BeanPropertyRowMapper<PrintSpool>(
						PrintSpool.class), new Object [] {spoolConfig.getNConsumptions(), spoolConfig.getNConsumptions(), spoolConfig.getDate()});
	}

	@Override
	public List<PrintSpool> getPrintSpoolSA(SpoolConfig spoolConfig) {
		return jdbcTemplate.query(standardQuery1+S+A+standardQuery2, 
				new BeanPropertyRowMapper<PrintSpool>(
						PrintSpool.class), new Object [] {spoolConfig.getNConsumptions(), spoolConfig.getDate()});
	}

	@Override
	public List<PrintSpool> getPrintSpoolSL(SpoolConfig spoolConfig) {
		return jdbcTemplate.query(standardQuery1+S+L+standardQuery2, 
				new BeanPropertyRowMapper<PrintSpool>(
						PrintSpool.class), new Object [] {spoolConfig.getNConsumptions(), spoolConfig.getDate()});
	}

	@Override
	public List<PrintSpool> getPrintSpoolAL(SpoolConfig spoolConfig) {
		return jdbcTemplate.query(standardQuery1+A+L+standardQuery2, 
				new BeanPropertyRowMapper<PrintSpool>(
						PrintSpool.class), new Object [] {spoolConfig.getNConsumptions(), spoolConfig.getNConsumptions(), spoolConfig.getDate()});
	}

	@Override
	public List<PrintSpool> getPrintSpoolS(SpoolConfig spoolConfig) {
		return jdbcTemplate.query(standardQuery1+S+standardQuery2, 
				new BeanPropertyRowMapper<PrintSpool>(
						PrintSpool.class), new Object [] {spoolConfig.getDate()});
	}

	@Override
	public List<PrintSpool> getPrintSpoolA(SpoolConfig spoolConfig) {
		return jdbcTemplate.query(standardQuery1+A+standardQuery2, 
				new BeanPropertyRowMapper<PrintSpool>(
						PrintSpool.class), new Object [] {spoolConfig.getNConsumptions(), spoolConfig.getDate()});
	}

	@Override
	public List<PrintSpool> getPrintSpoolL(SpoolConfig spoolConfig) {
		return jdbcTemplate.query(standardQuery1+L+standardQuery2, 
				new BeanPropertyRowMapper<PrintSpool>(
						PrintSpool.class), new Object [] {spoolConfig.getNConsumptions(), spoolConfig.getDate()});
	}

	@Override
	public List<PrintSpool> getPrintSpool(SpoolConfig spoolConfig) {
		return jdbcTemplate.query(standardQuery1+standardQuery2, 
				new BeanPropertyRowMapper<PrintSpool>(
						PrintSpool.class), new Object [] {spoolConfig.getDate()});
	}
	
	/* Esta función inserta la información sobre el CSV generado en una tabla para poder hacer 
	 * seguimiento del archivo */
	
	@Override
	public int printSpoolCsv(String link, String dateCreation, String period) {
		return jdbcTemplate.update("INSERT INTO APP_DATOS_IMPRESION.PRINTSPOOLS p (LINK, CREATED, PERIOD_ID) "
				+ "SELECT ?, ?, p2.ID	"
				+ "FROM APP_DATOS_IMPRESION.PERIODS p2	"
				+ "WHERE p2.MONTH_YEAR = ?", 
				new Object [] {link, dateCreation, period});
	}
	
	/* Get para obtener la lista de todos los archivos creados en formato .CSV */
	@Override
	public List<PrintSpoolCsv> getPrintSpoolCsv() {
		return jdbcTemplate.query("SELECT p.ID, p2.MONTH_YEAR as PERIOD, p.LINK, p.CREATED "
				+ "FROM APP_DATOS_IMPRESION.PRINTSPOOLS p "
				+ "INNER JOIN APP_DATOS_IMPRESION.PERIODS p2 "
				+ "ON p2.ID = p.PERIOD_ID", 
				new BeanPropertyRowMapper<PrintSpoolCsv>(
						PrintSpoolCsv.class));
	}

}