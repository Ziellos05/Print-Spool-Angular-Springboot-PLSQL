package in.printspool.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

@RestController
@RequestMapping(value = "/upload")
@Tag(name = "Upload", description = "Upload files and get history")
public class UploadController {
	
	@Autowired
	private UploadService uploadService;

	@Operation(summary = "Updates info about upload in database")
	@ApiResponses(value = { 
			@ApiResponse(responseCode = "200", description = "File has been uploaded successfully", content = @Content),
			@ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
			@ApiResponse(responseCode = "413", description = "Payload too large, forbidden >10MB files", content = @Content)})
	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
   private ResponseEntity<String> fileUpload(@RequestParam("file") MultipartFile file) throws Exception {
		if (file.getSize()>10000000) {
			return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).build();
		}
	   try {
		   String link="src/main/resources/upload/"+file.getOriginalFilename();
		   File convertFile = new File(link);
		   convertFile.createNewFile();
		   FileOutputStream fout = new FileOutputStream(convertFile);
		   fout.write(file.getBytes());
		   fout.close();
		   uploadService.saveUpload(link);
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
}