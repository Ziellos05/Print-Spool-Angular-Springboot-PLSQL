package in.printspool.dao;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class BillsDAOImp implements BillsDAO {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Override
	public int save() {
		Date date = new Date();
    	SimpleDateFormat DateFor = new SimpleDateFormat("MM/yyyy");
    	String stringDate= DateFor.format(date);
		return jdbcTemplate.update("BEGIN APP_DATOS_IMPRESION.print_spool.spool_gen(?); END;", stringDate);
	}

}