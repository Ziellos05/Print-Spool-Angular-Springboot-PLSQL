package in.printspool.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import in.printspool.model.PrintSpool;
import in.printspool.model.PrintSpoolCsv;
import in.printspool.model.SpoolConfig;
import in.printspool.repository.service.PrintSpoolService;
import in.printspool.util.PrintSpoolUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

//CONTROLLER PARA LA GENERACIÓN DEL PRINT SPOOL
@RestController
@RequestMapping(value = "/spool")
@Tag(name = "PrintSpool", description = "Dinamic Print Spool generator")
@CrossOrigin({ "*" })
public class PrintSpoolController {

	
	@Autowired
	private PrintSpoolService printSpoolService;

	/*
	 * El siguiente método genera dinámicamente el print spool a recibir en formato
	 * JSON, cuenta con varios condicionales para traer los campos solicitados,
	 * parsea a tipo byte[] para poder eliminar campos vacíos o nulos y retorna
	 * únicamente el print spool con la información requerida
	 */
	@Operation(summary = "Get dinamic print spool download link for the chosen date with this format: 'MM/YYYY'")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Print spool obtained", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = PrintSpoolCsv.class)) }),
			@ApiResponse(responseCode = "400", description = "Bad request", content = @Content) })
	@PostMapping
	private ResponseEntity<PrintSpoolCsv> getPrintSpool(@RequestBody SpoolConfig spoolConfig) {

		if (spoolConfig.getNConsumptions() == 0) {
			spoolConfig.setNConsumptions(1);
		}

		/*
		 * Ver los comentarios del primer else if para entender como funciona el
		 * programa, cada caso tiene la misma lógica
		 */

		try {

			if (spoolConfig.getDate().length() != 7) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}
			// Retorna todos los campos
			if (spoolConfig.isStratum() && spoolConfig.isAvgConsumption() && spoolConfig.isLastConsumption()) {
				return new ResponseEntity<PrintSpoolCsv>(printSpoolService.getPrintSpoolSAL(spoolConfig),
						HttpStatus.OK);
				// No retorna los últimos consumos
			} else if (spoolConfig.isStratum() && spoolConfig.isAvgConsumption()) {

				return new ResponseEntity<PrintSpoolCsv>(printSpoolService.getPrintSpoolSA(spoolConfig),
						HttpStatus.OK);

				// No retorna el promedio
			} else if (spoolConfig.isStratum() && spoolConfig.isLastConsumption()) {
				return new ResponseEntity<PrintSpoolCsv>(printSpoolService.getPrintSpoolSL(spoolConfig),
						HttpStatus.OK);
				// No retorna el estrato
			} else if (spoolConfig.isAvgConsumption() && spoolConfig.isLastConsumption()) {
				return new ResponseEntity<PrintSpoolCsv>(printSpoolService.getPrintSpoolAL(spoolConfig),
						HttpStatus.OK);
				// No retorna promedio ni últimos consumos
			} else if (spoolConfig.isStratum()) {
				return new ResponseEntity<PrintSpoolCsv>(printSpoolService.getPrintSpoolS(spoolConfig),
						HttpStatus.OK);
				// No retorna estrato ni últimos consumos
			} else if (spoolConfig.isAvgConsumption()) {
				return new ResponseEntity<PrintSpoolCsv>(printSpoolService.getPrintSpoolA(spoolConfig),
						HttpStatus.OK);
				// No retorna estrato ni promedio
			} else if (spoolConfig.isLastConsumption()) {
				return new ResponseEntity<PrintSpoolCsv>(printSpoolService.getPrintSpoolL(spoolConfig),
						HttpStatus.OK);
				// No retorna estrato, promedio ni último consumo
			} else {
				return new ResponseEntity<PrintSpoolCsv>(printSpoolService.getPrintSpool(spoolConfig),
						HttpStatus.OK);
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	/* Get para obtener la lista de todos los archivos creados en formato .CSV */
	@Operation(summary = "Get the whole data about the created .CSV files")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Print spool files obtained", content = {
			@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = PrintSpoolCsv.class))) }),
			@ApiResponse(responseCode = "400", description = "Bad request", content = @Content) })
	@GetMapping
	private ResponseEntity<List<PrintSpoolCsv>> getPrintSpoolCsv() throws Exception {
		try {
			return ResponseEntity.ok(printSpoolService.getPrintSpoolCsv());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	/* Get para descargar el archivo .CSV del print spool seleccionado */
	@Operation(summary = "Download a plain text file with the print spool data")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "File downloaded", content = @Content),
			@ApiResponse(responseCode = "400", description = "Bad request", content = @Content) })
	@GetMapping(value = "/download")
	public ResponseEntity<Resource> downloadCSV(@RequestParam("file") String filename) throws IOException {
		File file = new File("src/main/resources/download/" + filename);
		String newFileName = (filename);
		HttpHeaders header = new HttpHeaders();
		header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + newFileName);
		header.add("Cache-Control", "no-cache, no-store, must-revalidate");
		header.add("Pragma", "no-cache");
		header.add("Expires", "0");
		Path path = Paths.get(file.getAbsolutePath());
		ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

		return ResponseEntity.ok().headers(header).contentLength(file.length())
				.contentType(MediaType.parseMediaType("text/csv")).body(resource);
	}
}
