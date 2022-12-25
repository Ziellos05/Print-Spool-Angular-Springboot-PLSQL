package in.printspool.repository.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.printspool.model.Upload;
import in.printspool.repository.UploadRepository;

// Interface que será utilizado por el servicio de Upload
@Service
public class UploadService {

	@Autowired
	UploadRepository uploadRepository;
	// Carga el archivo y actualiza el registro en la base de datos
	public int saveUpload(String filename) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime currentTime = LocalDateTime.now();
		String createdTime = currentTime.format(formatter);
		return uploadRepository.saveUpload(filename, createdTime);
	};

	// Obtiene una lista con los registros de los archivos subidos
	public List<Upload> getUploads(){
		return uploadRepository.getUploads();
	};

	// Obtiene el registro de una sola subida por el filename
	public List<Upload> getUploadByFilename(String filename){
		return uploadRepository.getUploadByFilename(filename);
	};

}
