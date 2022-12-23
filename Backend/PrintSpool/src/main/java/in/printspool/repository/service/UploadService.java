package in.printspool.repository.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import in.printspool.model.Upload;
import in.printspool.repository.UploadRepository;

/* Servicio encargado de gestionar información sobre archivos subidos en la BD */
@Repository
public class UploadService implements UploadRepository {

	@Autowired
	JdbcTemplate jdbcTemplate;

	// Query nativa de Oracle para actualizar un registro para cada archivo subido
	@Override
	@Transactional
	public int saveUpload(String filename) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime currentTime = LocalDateTime.now();
		String createdTime = currentTime.format(formatter);
		return jdbcTemplate.update(
				"INSERT  /*+APPEND*/ INTO APP_DATOS_IMPRESION.UPLOADS p (FILENAME, CREATED) VALUES (?, ?)",
				new Object[] { filename, createdTime });
	}

	// Query para obtener los datos sobre los archivos subidos
	@Override
	@Transactional(readOnly = true)
	public List<Upload> getUploads() {
		return jdbcTemplate.query(
				"SELECT /*+ INDEX UPLOADS_FILENAME_IDX ON APP_DATOS_IMPRESION.UPLOADS (FILENAME) */ ID, FILENAME, CREATED "
						+ "FROM APP_DATOS_IMPRESION.UPLOADS ORDER BY ID DESC",
				new BeanPropertyRowMapper<Upload>(Upload.class));
	}

	// Query para obtener los datos sobre los archivos subidos
	@Override
	@Transactional(readOnly = true)
	public List<Upload> getUploadByFilename(String filename) {
		return jdbcTemplate.query(
				"SELECT /*+ INDEX UPLOADS_FILENAME_IDX ON APP_DATOS_IMPRESION.UPLOADS (FILENAME) */  ID, FILENAME, CREATED "
						+ "FROM APP_DATOS_IMPRESION.UPLOADS WHERE FILENAME = ?",
				new BeanPropertyRowMapper<Upload>(Upload.class), new Object[] { filename });
	}
}
