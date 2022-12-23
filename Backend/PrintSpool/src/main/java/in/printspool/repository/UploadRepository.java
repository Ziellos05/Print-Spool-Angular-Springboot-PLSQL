package in.printspool.repository;

import java.util.List;

import in.printspool.model.Upload;

// Interface que será utilizado por el servicio de Upload
public interface UploadRepository {

	// Carga el archivo y actualiza el registro en la base de datos
	int saveUpload(String link);

	// Obtiene una lista con los registros de los archivos subidos
	List<Upload> getUploads();

	// Obtiene el registro de una sola subida por el filename
	List<Upload> getUploadByFilename(String filename);

}
