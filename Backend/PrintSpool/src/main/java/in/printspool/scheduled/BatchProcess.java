package in.printspool.scheduled;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.text.SimpleDateFormat;
import java.util.Date;

/* Proceso en batch encargado de generar la facturación mensualmente a través de
 * @Scheduled annotation, utiliza JDBC para llamar a PL/SQL. La fecha de actualización
 * es el último día de cada mes a las 17:00 horas, este valor se puede configurar en
 * application.properties */
@Component
@EnableAsync
public class BatchProcess {

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Async
	@Scheduled(cron = "${cron.expression}", zone = "America/Bogota")
	public void scheduleFixedRateTaskAsync() throws InterruptedException {
		Date date = new Date();
		SimpleDateFormat DateFor = new SimpleDateFormat("MM/yyyy");
		String stringDate = DateFor.format(date);
		jdbcTemplate.update("BEGIN APP_DATOS_IMPRESION.print_spool.spool_gen(?); END;", stringDate);
		System.out.println("Bills updated for the month " + stringDate);
	}

}