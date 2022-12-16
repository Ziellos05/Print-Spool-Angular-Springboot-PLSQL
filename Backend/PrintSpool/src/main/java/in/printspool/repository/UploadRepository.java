package in.printspool.repository;

import java.util.List;

import in.printspool.model.Upload;

// Interface que será utilizado por el servicio de Upload
public interface UploadRepository {
	
	int saveUpload(String link);
	
	List<Upload> getUploads();
	
}
