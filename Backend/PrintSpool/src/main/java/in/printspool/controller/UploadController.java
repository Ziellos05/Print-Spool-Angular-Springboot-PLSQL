package in.printspool.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import in.printspool.model.PrintSpoolCsv;
import in.printspool.model.Upload;
import in.printspool.repository.service.StratumService;
import in.printspool.repository.service.UploadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

//CONTROLLER PARA LA SUBIDA Y DOCUMENTACIÓN DE ARCHIVOS
@RestController
@RequestMapping(value = "/upload")
@Tag(name = "Upload", description = "Upload files and get history")
@CrossOrigin({"*"})
public class UploadController {
	
	@Autowired
	private UploadService uploadService;

	/* Post para la subida de archivos
	 * No funciona si el archivo pesa más de 10Mb y lanza error 413
	 * No funciona si el archivo pesa más de 20Mb y lanza error por CORS */
	@Operation(summary = "Uploads and updates info about file in database")
	@ApiResponses(value = { 
			@ApiResponse(responseCode = "200", description = "File has been uploaded successfully", content = @Content),
			@ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
			@ApiResponse(responseCode = "409", description = "Conflict", content = @Content),
			@ApiResponse(responseCode = "413", description = "Payload too large, forbidden >10MB files", content = @Content)})
	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
   private ResponseEntity<String> fileUpload(@RequestParam("file") MultipartFile file) throws Exception {
		if (file.getSize()>10000000) {
			return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).build();
		}
		List<Upload> selected = uploadService.getUploadByFilename(file.getOriginalFilename());
		if (selected.size() != 0) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	   try {
		   String filename=file.getOriginalFilename();
		   File convertFile = new File("src/main/resources/upload/"+filename);
		   convertFile.createNewFile();
		   FileOutputStream fout = new FileOutputStream(convertFile);
		   fout.write(file.getBytes());
		   fout.close();
		   uploadService.saveUpload(filename);
		   return ResponseEntity.ok("File is upload successfully");
	   } catch (Exception e) {
		   return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	   } 
   }
	
	/* Get para obtener la lista de todos los archivos subidos */
	@Operation(summary = "Get the whole data about the uploaded files")
	@ApiResponses(value = { 
			@ApiResponse(responseCode = "200", description = "Update files obtained", content = {
					@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Upload.class))) }),
			@ApiResponse(responseCode = "400", description = "Bad request", content = @Content) })
	@GetMapping
	private ResponseEntity<List<Upload>> getUploads() throws Exception {
		try {
			return ResponseEntity.ok(uploadService.getUploads());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}
	
	/* Get para descargar el archivo seleccionado */
	@Operation(summary = "Download a file from the upload folder")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "File downloaded", content = @Content),
			@ApiResponse(responseCode = "400", description = "Bad request", content = @Content) })
    @GetMapping(value = "/download")
    public ResponseEntity<Resource> downloadUploaded(@RequestParam("file") String filename) throws IOException {
        File file = new File("src/main/resources/upload/"+filename);
        String newFileName = (filename);
        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+newFileName);
        header.add("Cache-Control", "no-cache, no-store, must-revalidate");
        header.add("Pragma", "no-cache");
        header.add("Expires", "0");
        Path path = Paths.get(file.getAbsolutePath());
        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

        return ResponseEntity.ok()
                .headers(header)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("multipart/form-data"))
                .body(resource);
    }
}