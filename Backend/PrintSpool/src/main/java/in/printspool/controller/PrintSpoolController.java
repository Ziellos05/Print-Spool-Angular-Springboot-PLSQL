package in.printspool.controller;

import java.io.IOException;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import in.printspool.model.PrintSpool;
import in.printspool.model.PrintSpoolCsv;
import in.printspool.model.SpoolConfig;
import in.printspool.repository.PrintSpoolRepository;
import in.printspool.util.PrintSpoolUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

// CONTROLLER PARA LA GENERACIÓN DEL PRINT SPOOL
@RestController
@RequestMapping(value = "/spool")
@Tag(name = "PrintSpool", description = "Dinamic Print Spool generator")
public class PrintSpoolController {

	@Autowired
	private PrintSpoolRepository printSpoolRepository;

	/* El siguiente método genera dinámicamente el print spool a recibir en formato
	 * JSON, cuenta con varios condicionales para traer los campos solicitados, 
	 * parsea a tipo byte[] para poder eliminar campos vacíos o nulos y retorna únicamente
	 * el print spool con la información requerida*/
	@Operation(summary = "Get dinamic print spool download link for the chosen date with this format: 'MM/YYYY'")
	@ApiResponses(value = { 
			@ApiResponse(responseCode = "200", description = "Print spool obtained", content = {
					@Content(mediaType = "text/plain", schema = @Schema(implementation = String.class)) }),
			@ApiResponse(responseCode = "400", description = "Bad request", content = @Content) })
	@PostMapping
	private ResponseEntity<String> getPrintSpool(@RequestBody SpoolConfig spoolConfig) {
		
		
		if (spoolConfig.getNConsumptions() == 0) {
			spoolConfig.setNConsumptions(1);
		}
		
		/* Ver los comentarios del primer else if para entender como funciona el programa, cada caso tiene la misma lógica */
		
		try {
			
			if (spoolConfig.getDate().length() != 7) { 
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}
				//Retorna todos los campos
			if (spoolConfig.isStratum() && spoolConfig.isAvgConsumption() && spoolConfig.isLastConsumption()) {
				List<PrintSpool> printSpool = printSpoolRepository.getPrintSpoolSAL(spoolConfig);
				PrintSpoolUtil util = new PrintSpoolUtil();
				JsonNode root = util.jsonNodeGenerator(printSpool);				
				List<String> csvList = util.csvGenerator(root, spoolConfig.getDate());
				printSpoolRepository.printSpoolCsv(csvList.get(0), csvList.get(1), spoolConfig.getDate());
				return new ResponseEntity<String>(csvList.get(0), HttpStatus.OK);
				// No retorna los últimos consumos
			} else if (spoolConfig.isStratum() && spoolConfig.isAvgConsumption()) {
				
				/* Llamado a la base de datos para obtener el print spool */
				List<PrintSpool> printSpool = printSpoolRepository.getPrintSpoolSA(spoolConfig);
				PrintSpoolUtil util = new PrintSpoolUtil();
				
				/* Transformación de la data al formato JsonNode para poder editar y quitar los campos que no se requieran en la respuesta */
				JsonNode root = util.jsonNodeGenerator(printSpool);
				for (JsonNode jsonNode : root) {
					if (jsonNode instanceof ObjectNode) {
						ObjectNode edit = (ObjectNode) jsonNode;
						edit.remove("last");
					}
				}			
				
				/* Generación del .JSON, .CSV y agregado de la información a la tabla que hace seguimiento a los archivos creados */
				
				List<String> csvList = util.csvGenerator(root, spoolConfig.getDate());
				printSpoolRepository.printSpoolCsv(csvList.get(0), csvList.get(1), spoolConfig.getDate());
				
				/* Retorna link en el local para acceder al archivo y poder "descargarlo" */
				return new ResponseEntity<String>(csvList.get(0), HttpStatus.OK);
				// No retorna el promedio
			} else if (spoolConfig.isStratum() && spoolConfig.isLastConsumption()) {
				List<PrintSpool> printSpool = printSpoolRepository.getPrintSpoolSL(spoolConfig);
				PrintSpoolUtil util = new PrintSpoolUtil();
				JsonNode root = util.jsonNodeGenerator(printSpool);
				for (JsonNode jsonNode : root) {
					if (jsonNode instanceof ObjectNode) {
						ObjectNode edit = (ObjectNode) jsonNode;
						edit.remove("avgConsumption");
					}
				}							
				List<String> csvList = util.csvGenerator(root, spoolConfig.getDate());
				printSpoolRepository.printSpoolCsv(csvList.get(0), csvList.get(1), spoolConfig.getDate());
				return new ResponseEntity<String>(csvList.get(0), HttpStatus.OK);
				// No retorna el estrato
			} else if (spoolConfig.isAvgConsumption() && spoolConfig.isLastConsumption()) {
				List<PrintSpool> printSpool = printSpoolRepository.getPrintSpoolAL(spoolConfig);
				PrintSpoolUtil util = new PrintSpoolUtil();
				JsonNode root = util.jsonNodeGenerator(printSpool);
				for (JsonNode jsonNode : root) {
					if (jsonNode instanceof ObjectNode) {
						ObjectNode edit = (ObjectNode) jsonNode;
						edit.remove("stratum");
					}
				}							
				List<String> csvList = util.csvGenerator(root, spoolConfig.getDate());
				printSpoolRepository.printSpoolCsv(csvList.get(0), csvList.get(1), spoolConfig.getDate());
				return new ResponseEntity<String>(csvList.get(0), HttpStatus.OK);
				// No retorna promedio ni últimos consumos
			} else if (spoolConfig.isStratum()) {
				List<PrintSpool> printSpool = printSpoolRepository.getPrintSpoolS(spoolConfig);
				PrintSpoolUtil util = new PrintSpoolUtil();
				JsonNode root = util.jsonNodeGenerator(printSpool);
				for (JsonNode jsonNode : root) {
					if (jsonNode instanceof ObjectNode) {
						ObjectNode edit = (ObjectNode) jsonNode;
						edit.remove("avgConsumption");
						edit.remove("last");
					}
				}							
				List<String> csvList = util.csvGenerator(root, spoolConfig.getDate());
				printSpoolRepository.printSpoolCsv(csvList.get(0), csvList.get(1), spoolConfig.getDate());
				return new ResponseEntity<String>(csvList.get(0), HttpStatus.OK);
				// No retorna estrato ni últimos consumos
			} else if (spoolConfig.isAvgConsumption()) {
				List<PrintSpool> printSpool = printSpoolRepository.getPrintSpoolA(spoolConfig);
				PrintSpoolUtil util = new PrintSpoolUtil();
				JsonNode root = util.jsonNodeGenerator(printSpool);
				for (JsonNode jsonNode : root) {
					if (jsonNode instanceof ObjectNode) {
						ObjectNode edit = (ObjectNode) jsonNode;
						edit.remove("stratum");
						edit.remove("last");
					}
				}						
				List<String> csvList = util.csvGenerator(root, spoolConfig.getDate());
				printSpoolRepository.printSpoolCsv(csvList.get(0), csvList.get(1), spoolConfig.getDate());
				return new ResponseEntity<String>(csvList.get(0), HttpStatus.OK);
				// No retorna estrato ni promedio
			} else if (spoolConfig.isLastConsumption()) {
				List<PrintSpool> printSpool = printSpoolRepository.getPrintSpoolL(spoolConfig);
				PrintSpoolUtil util = new PrintSpoolUtil();
				JsonNode root = util.jsonNodeGenerator(printSpool);
				for (JsonNode jsonNode : root) {
					if (jsonNode instanceof ObjectNode) {
						ObjectNode edit = (ObjectNode) jsonNode;
						edit.remove("avgConsumption");
						edit.remove("stratum");
					}
				}						
				List<String> csvList = util.csvGenerator(root, spoolConfig.getDate());
				printSpoolRepository.printSpoolCsv(csvList.get(0), csvList.get(1), spoolConfig.getDate());
				return new ResponseEntity<String>(csvList.get(0), HttpStatus.OK);
				// No retorna estrato, promedio ni último consumo
			} else {
				List<PrintSpool> printSpool = printSpoolRepository.getPrintSpool(spoolConfig);
				PrintSpoolUtil util = new PrintSpoolUtil();
				JsonNode root = util.jsonNodeGenerator(printSpool);
				for (JsonNode jsonNode : root) {
					if (jsonNode instanceof ObjectNode) {
						ObjectNode edit = (ObjectNode) jsonNode;
						edit.remove("stratum");
						edit.remove("avgConsumption");
						edit.remove("last");
					}
				}				
				List<String> csvList = util.csvGenerator(root, spoolConfig.getDate());
				printSpoolRepository.printSpoolCsv(csvList.get(0), csvList.get(1), spoolConfig.getDate());
				return new ResponseEntity<String>(csvList.get(0), HttpStatus.OK);
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}
	
	/* Get para obtener la lista de todos los archivos creados en formato .CSV */
	@Operation(summary = "Get the whole data about the created .CSV files")
	@ApiResponses(value = { 
			@ApiResponse(responseCode = "200", description = "Print spool files obtained", content = {
					@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = PrintSpoolCsv.class))) }),
			@ApiResponse(responseCode = "400", description = "Bad request", content = @Content) })
	@GetMapping
	private ResponseEntity<List<PrintSpoolCsv>> getPrintSpoolCsv() throws Exception {
		try {
			return ResponseEntity.ok(printSpoolRepository.getPrintSpoolCsv());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}
}
