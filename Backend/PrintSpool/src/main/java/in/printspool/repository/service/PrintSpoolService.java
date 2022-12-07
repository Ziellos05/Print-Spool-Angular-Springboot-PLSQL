package in.printspool.repository.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import in.printspool.model.PrintSpool;
import in.printspool.model.SpoolConfig;
import in.printspool.repository.PrintSpoolRepository;

@Repository
public class PrintSpoolService implements PrintSpoolRepository {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Override
	public List<PrintSpool> getPrintSpoolSAL(SpoolConfig spoolConfig) {
		return jdbcTemplate.query("SELECT b.ID, "
				+ "TO_CHAR(b.PAYMENT_DUE, 'DD-MM-YYYY')"
				+ "AS PAYMENTDUE, "
				+ "b.AMOUNT, "
				+ "c.NAME, "
				+ "c.ADDRESS, "
				+ "c.STRATUM_ID "
				+ "AS STRATUM, "
				+ "c2.CUBIC_METERS "
				+ "AS CONSUMPTION, "
				+ "APP_DATOS_IMPRESION.print_spool.CAL_AVG(c.ID, ?) "
				+ "AS AVG_CONSUMPTION, "
				+ "APP_DATOS_IMPRESION.PRINT_SPOOL.GET_LAST_Z(c.ID, ?) "
				+ "AS LAST "
				+ "FROM APP_DATOS_IMPRESION.BILLS b "
				+ "INNER JOIN APP_DATOS_IMPRESION.CONSUMPTIONS c2 "
				+ "ON c2.ID = b.CONSUMPTION_ID "
				+ "INNER JOIN APP_DATOS_IMPRESION.CLIENTS c "
				+ "ON c.ID = c2.CLIENT_ID "
				+ "INNER JOIN APP_DATOS_IMPRESION.PERIODS p "
				+ "ON p.ID = c2.PERIOD_ID "
				+ "WHERE p.MONTH_YEAR = ? "
				+ "ORDER BY b.DELIVERY_RATE", 
				new BeanPropertyRowMapper<PrintSpool>(
						PrintSpool.class), new Object [] {spoolConfig.getNConsumptions(), spoolConfig.getNConsumptions(), spoolConfig.getDate()});
	}

	@Override
	public List<PrintSpool> getPrintSpoolSA(SpoolConfig spoolConfig) {
		return jdbcTemplate.query("SELECT b.ID, "
				+ "TO_CHAR(b.PAYMENT_DUE, 'DD-MM-YYYY')"
				+ "AS PAYMENTDUE, "
				+ "b.AMOUNT, "
				+ "c.NAME, "
				+ "c.ADDRESS, "
				+ "c.STRATUM_ID "
				+ "AS STRATUM, "
				+ "c2.CUBIC_METERS "
				+ "AS CONSUMPTION, "
				+ "APP_DATOS_IMPRESION.print_spool.CAL_AVG(c.ID, ?) "
				+ "AS AVG_CONSUMPTION "
				+ "FROM APP_DATOS_IMPRESION.BILLS b "
				+ "INNER JOIN APP_DATOS_IMPRESION.CONSUMPTIONS c2 "
				+ "ON c2.ID = b.CONSUMPTION_ID "
				+ "INNER JOIN APP_DATOS_IMPRESION.CLIENTS c "
				+ "ON c.ID = c2.CLIENT_ID "
				+ "INNER JOIN APP_DATOS_IMPRESION.PERIODS p "
				+ "ON p.ID = c2.PERIOD_ID "
				+ "WHERE p.MONTH_YEAR = ? "
				+ "ORDER BY b.DELIVERY_RATE", 
				new BeanPropertyRowMapper<PrintSpool>(
						PrintSpool.class), new Object [] {spoolConfig.getNConsumptions(), spoolConfig.getDate()});
	}

	@Override
	public List<PrintSpool> getPrintSpoolSL(SpoolConfig spoolConfig) {
		return jdbcTemplate.query("SELECT b.ID, "
				+ "TO_CHAR(b.PAYMENT_DUE, 'DD-MM-YYYY')"
				+ "AS PAYMENTDUE, "
				+ "b.AMOUNT, "
				+ "c.NAME, "
				+ "c.ADDRESS, "
				+ "c.STRATUM_ID "
				+ "AS STRATUM, "
				+ "c2.CUBIC_METERS "
				+ "AS CONSUMPTION, "
				+ "APP_DATOS_IMPRESION.PRINT_SPOOL.GET_LAST_Z(c.ID, ?) "
				+ "AS LAST "
				+ "FROM APP_DATOS_IMPRESION.BILLS b "
				+ "INNER JOIN APP_DATOS_IMPRESION.CONSUMPTIONS c2 "
				+ "ON c2.ID = b.CONSUMPTION_ID "
				+ "INNER JOIN APP_DATOS_IMPRESION.CLIENTS c "
				+ "ON c.ID = c2.CLIENT_ID "
				+ "INNER JOIN APP_DATOS_IMPRESION.PERIODS p "
				+ "ON p.ID = c2.PERIOD_ID "
				+ "WHERE p.MONTH_YEAR = ? "
				+ "ORDER BY b.DELIVERY_RATE", 
				new BeanPropertyRowMapper<PrintSpool>(
						PrintSpool.class), new Object [] {spoolConfig.getNConsumptions(), spoolConfig.getDate()});
	}

	@Override
	public List<PrintSpool> getPrintSpoolAL(SpoolConfig spoolConfig) {
		return jdbcTemplate.query("SELECT b.ID, "
				+ "TO_CHAR(b.PAYMENT_DUE, 'DD-MM-YYYY')"
				+ "AS PAYMENTDUE, "
				+ "b.AMOUNT, "
				+ "c.NAME, "
				+ "c.ADDRESS, "
				+ "c2.CUBIC_METERS "
				+ "AS CONSUMPTION, "
				+ "APP_DATOS_IMPRESION.print_spool.CAL_AVG(c.ID, ?) "
				+ "AS AVG_CONSUMPTION, "
				+ "APP_DATOS_IMPRESION.PRINT_SPOOL.GET_LAST_Z(c.ID, ?) "
				+ "AS LAST "
				+ "FROM APP_DATOS_IMPRESION.BILLS b "
				+ "INNER JOIN APP_DATOS_IMPRESION.CONSUMPTIONS c2 "
				+ "ON c2.ID = b.CONSUMPTION_ID "
				+ "INNER JOIN APP_DATOS_IMPRESION.CLIENTS c "
				+ "ON c.ID = c2.CLIENT_ID "
				+ "INNER JOIN APP_DATOS_IMPRESION.PERIODS p "
				+ "ON p.ID = c2.PERIOD_ID "
				+ "WHERE p.MONTH_YEAR = ? "
				+ "ORDER BY b.DELIVERY_RATE", 
				new BeanPropertyRowMapper<PrintSpool>(
						PrintSpool.class), new Object [] {spoolConfig.getNConsumptions(), spoolConfig.getNConsumptions(), spoolConfig.getDate()});
	}

	@Override
	public List<PrintSpool> getPrintSpoolS(SpoolConfig spoolConfig) {
		return jdbcTemplate.query("SELECT b.ID, "
				+ "TO_CHAR(b.PAYMENT_DUE, 'DD-MM-YYYY')"
				+ "AS PAYMENTDUE, "
				+ "b.AMOUNT, "
				+ "c.NAME, "
				+ "c.ADDRESS, "
				+ "c.STRATUM_ID "
				+ "AS STRATUM, "
				+ "c2.CUBIC_METERS "
				+ "AS CONSUMPTION "
				+ "FROM APP_DATOS_IMPRESION.BILLS b "
				+ "INNER JOIN APP_DATOS_IMPRESION.CONSUMPTIONS c2 "
				+ "ON c2.ID = b.CONSUMPTION_ID "
				+ "INNER JOIN APP_DATOS_IMPRESION.CLIENTS c "
				+ "ON c.ID = c2.CLIENT_ID "
				+ "INNER JOIN APP_DATOS_IMPRESION.PERIODS p "
				+ "ON p.ID = c2.PERIOD_ID "
				+ "WHERE p.MONTH_YEAR = ? "
				+ "ORDER BY b.DELIVERY_RATE", 
				new BeanPropertyRowMapper<PrintSpool>(
						PrintSpool.class), new Object [] {spoolConfig.getDate()});
	}

	@Override
	public List<PrintSpool> getPrintSpoolA(SpoolConfig spoolConfig) {
		return jdbcTemplate.query("SELECT b.ID, "
				+ "TO_CHAR(b.PAYMENT_DUE, 'DD-MM-YYYY')"
				+ "AS PAYMENTDUE, "
				+ "b.AMOUNT, "
				+ "c.NAME, "
				+ "c.ADDRESS, "
				+ "c2.CUBIC_METERS "
				+ "AS CONSUMPTION, "
				+ "APP_DATOS_IMPRESION.print_spool.CAL_AVG(c.ID, ?) "
				+ "AS AVG_CONSUMPTION "
				+ "FROM APP_DATOS_IMPRESION.BILLS b "
				+ "INNER JOIN APP_DATOS_IMPRESION.CONSUMPTIONS c2 "
				+ "ON c2.ID = b.CONSUMPTION_ID "
				+ "INNER JOIN APP_DATOS_IMPRESION.CLIENTS c "
				+ "ON c.ID = c2.CLIENT_ID "
				+ "INNER JOIN APP_DATOS_IMPRESION.PERIODS p "
				+ "ON p.ID = c2.PERIOD_ID "
				+ "WHERE p.MONTH_YEAR = ? "
				+ "ORDER BY b.DELIVERY_RATE", 
				new BeanPropertyRowMapper<PrintSpool>(
						PrintSpool.class), new Object [] {spoolConfig.getNConsumptions(), spoolConfig.getDate()});
	}

	@Override
	public List<PrintSpool> getPrintSpoolL(SpoolConfig spoolConfig) {
		return jdbcTemplate.query("SELECT b.ID, "
				+ "TO_CHAR(b.PAYMENT_DUE, 'DD-MM-YYYY')"
				+ "AS PAYMENTDUE, "
				+ "b.AMOUNT, "
				+ "c.NAME, "
				+ "c.ADDRESS, "
				+ "c2.CUBIC_METERS "
				+ "AS CONSUMPTION, "
				+ "APP_DATOS_IMPRESION.PRINT_SPOOL.GET_LAST_Z(c.ID, ?) "
				+ "AS LAST "
				+ "FROM APP_DATOS_IMPRESION.BILLS b "
				+ "INNER JOIN APP_DATOS_IMPRESION.CONSUMPTIONS c2 "
				+ "ON c2.ID = b.CONSUMPTION_ID "
				+ "INNER JOIN APP_DATOS_IMPRESION.CLIENTS c "
				+ "ON c.ID = c2.CLIENT_ID "
				+ "INNER JOIN APP_DATOS_IMPRESION.PERIODS p "
				+ "ON p.ID = c2.PERIOD_ID "
				+ "WHERE p.MONTH_YEAR = ? "
				+ "ORDER BY b.DELIVERY_RATE", 
				new BeanPropertyRowMapper<PrintSpool>(
						PrintSpool.class), new Object [] {spoolConfig.getNConsumptions(), spoolConfig.getDate()});
	}

	@Override
	public List<PrintSpool> getPrintSpool(SpoolConfig spoolConfig) {
		return jdbcTemplate.query("SELECT b.ID, "
				+ "TO_CHAR(b.PAYMENT_DUE, 'DD-MM-YYYY')"
				+ "AS PAYMENTDUE, "
				+ "b.AMOUNT, "
				+ "c.NAME, "
				+ "c.ADDRESS, "
				+ "c2.CUBIC_METERS "
				+ "AS CONSUMPTION "
				+ "FROM APP_DATOS_IMPRESION.BILLS b "
				+ "INNER JOIN APP_DATOS_IMPRESION.CONSUMPTIONS c2 "
				+ "ON c2.ID = b.CONSUMPTION_ID "
				+ "INNER JOIN APP_DATOS_IMPRESION.CLIENTS c "
				+ "ON c.ID = c2.CLIENT_ID "
				+ "INNER JOIN APP_DATOS_IMPRESION.PERIODS p "
				+ "ON p.ID = c2.PERIOD_ID "
				+ "WHERE p.MONTH_YEAR = ? "
				+ "ORDER BY b.DELIVERY_RATE", 
				new BeanPropertyRowMapper<PrintSpool>(
						PrintSpool.class), new Object [] {spoolConfig.getDate()});
	}

}