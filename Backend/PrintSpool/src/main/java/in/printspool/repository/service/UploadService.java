package in.printspool.repository.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import in.printspool.model.Upload;
import in.printspool.repository.UploadRepository;

/* Servicio encargado de gestionar información sobre archivos subidos en la BD */
@Repository
public class UploadService implements UploadRepository {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	// Query nativa de Oracle para actualizar un registro para cada archivo subido
	@Override
	public int saveUpload(String link) {
		return jdbcTemplate.update("INSERT INTO APP_DATOS_IMPRESION.UPLOADS p (LINK, CREATED) VALUES (?, ?)", new Object [] {link, java.time.LocalDateTime.now().toString()});
	}
	
// Query para obtener los datos sobre los archivos subidos
	@Override
	public List<Upload> getUploads() {
		return jdbcTemplate.query("SELECT ID, LINK, CREATED "
				+ "FROM APP_DATOS_IMPRESION.UPLOADS", 
				new BeanPropertyRowMapper<Upload>(
						Upload.class));
	}

}
