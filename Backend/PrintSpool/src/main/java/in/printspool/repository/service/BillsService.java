package in.printspool.repository.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import in.printspool.repository.BillsRepository;

/* Servicio encargado de generar la facturación, utiliza JDBC */
@Repository
public class BillsService implements BillsRepository {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	// Llamado a un método PL/SQL a través de JDBC para actualizar la facturación hasta la fecha actual
	@Override
	@Transactional
	public int save() {
		Date date = new Date();
    	SimpleDateFormat DateFor = new SimpleDateFormat("MM/yyyy");
    	String stringDate= DateFor.format(date);
		return jdbcTemplate.update("BEGIN APP_DATOS_IMPRESION.print_spool.spool_gen(?); END;", stringDate);
	}

}