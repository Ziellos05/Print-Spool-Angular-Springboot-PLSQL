package in.printspool.controller;

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
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import in.printspool.model.PrintSpool;
import in.printspool.model.SpoolConfig;
import in.printspool.model.Stratum;
import in.printspool.repository.PrintSpoolRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(value = "/spool")
@Tag(name = "PrintSpool", description = "Dinamic Print Spool generator")
public class PrintSpoolController {

	@Autowired
	private PrintSpoolRepository printSpoolRepository;

	@Operation(summary = "Get dinamic print spool for the chosen date with this format: 'MM/YYYY'")
	@ApiResponses(value = { 
			@ApiResponse(responseCode = "200", description = "Print spool obtained", content = {
					@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = PrintSpool.class))) }),
			@ApiResponse(responseCode = "400", description = "Bad request", content = @Content) })
	@PostMapping
	private ResponseEntity<byte[]> getPrintSpool(@RequestBody SpoolConfig spoolConfig) {
		if (spoolConfig.getNConsumptions() == 0) {
			spoolConfig.setNConsumptions(1);
		}
		try {
			if (spoolConfig.isStratum() && spoolConfig.isAvgConsumption() && spoolConfig.isLastConsumption()) {

				List<PrintSpool> printSpool = printSpoolRepository.getPrintSpoolSAL(spoolConfig);
				ObjectMapper objectMapper = new ObjectMapper();
				String json = objectMapper.writeValueAsString(printSpool);
				byte[] jsonByte = json.getBytes();

				return new ResponseEntity<byte[]>(jsonByte, HttpStatus.OK);

			} else if (spoolConfig.isStratum() && spoolConfig.isAvgConsumption()) {

				List<PrintSpool> printSpool = printSpoolRepository.getPrintSpoolSA(spoolConfig);
				ObjectMapper objectMapper = new ObjectMapper();
				String json = objectMapper.writeValueAsString(printSpool);
				byte[] jsonByte = json.getBytes();
				JsonNode root = objectMapper.readTree(jsonByte);
				for (JsonNode jsonNode : root) {
					if (jsonNode instanceof ObjectNode) {
						ObjectNode edit = (ObjectNode) jsonNode;
						edit.remove("last");
					}
				}
				String jsonFinal = objectMapper.writeValueAsString(root);
				byte[] jsonByteFinal = jsonFinal.getBytes();

				return new ResponseEntity<byte[]>(jsonByteFinal, HttpStatus.OK);

			} else if (spoolConfig.isStratum() && spoolConfig.isLastConsumption()) {

				List<PrintSpool> printSpool = printSpoolRepository.getPrintSpoolSL(spoolConfig);
				ObjectMapper objectMapper = new ObjectMapper();
				String json = objectMapper.writeValueAsString(printSpool);
				byte[] jsonByte = json.getBytes();
				JsonNode root = objectMapper.readTree(jsonByte);
				for (JsonNode jsonNode : root) {
					if (jsonNode instanceof ObjectNode) {
						ObjectNode edit = (ObjectNode) jsonNode;
						edit.remove("avgConsumption");
					}
				}
				String jsonFinal = objectMapper.writeValueAsString(root);
				byte[] jsonByteFinal = jsonFinal.getBytes();

				return new ResponseEntity<byte[]>(jsonByteFinal, HttpStatus.OK);

			} else if (spoolConfig.isAvgConsumption() && spoolConfig.isLastConsumption()) {

				List<PrintSpool> printSpool = printSpoolRepository.getPrintSpoolAL(spoolConfig);
				ObjectMapper objectMapper = new ObjectMapper();
				String json = objectMapper.writeValueAsString(printSpool);
				byte[] jsonByte = json.getBytes();
				JsonNode root = objectMapper.readTree(jsonByte);
				for (JsonNode jsonNode : root) {
					if (jsonNode instanceof ObjectNode) {
						ObjectNode edit = (ObjectNode) jsonNode;
						edit.remove("stratum");
					}
				}
				String jsonFinal = objectMapper.writeValueAsString(root);
				byte[] jsonByteFinal = jsonFinal.getBytes();

				return new ResponseEntity<byte[]>(jsonByteFinal, HttpStatus.OK);

			} else if (spoolConfig.isStratum()) {

				List<PrintSpool> printSpool = printSpoolRepository.getPrintSpoolS(spoolConfig);
				ObjectMapper objectMapper = new ObjectMapper();
				String json = objectMapper.writeValueAsString(printSpool);
				byte[] jsonByte = json.getBytes();
				JsonNode root = objectMapper.readTree(jsonByte);
				for (JsonNode jsonNode : root) {
					if (jsonNode instanceof ObjectNode) {
						ObjectNode edit = (ObjectNode) jsonNode;
						edit.remove("avgConsumption");
						edit.remove("last");
					}
				}
				String jsonFinal = objectMapper.writeValueAsString(root);
				byte[] jsonByteFinal = jsonFinal.getBytes();

				return new ResponseEntity<byte[]>(jsonByteFinal, HttpStatus.OK);

			} else if (spoolConfig.isAvgConsumption()) {

				List<PrintSpool> printSpool = printSpoolRepository.getPrintSpoolA(spoolConfig);
				ObjectMapper objectMapper = new ObjectMapper();
				String json = objectMapper.writeValueAsString(printSpool);
				byte[] jsonByte = json.getBytes();
				JsonNode root = objectMapper.readTree(jsonByte);
				for (JsonNode jsonNode : root) {
					if (jsonNode instanceof ObjectNode) {
						ObjectNode edit = (ObjectNode) jsonNode;
						edit.remove("stratum");
						edit.remove("last");
					}
				}
				String jsonFinal = objectMapper.writeValueAsString(root);
				byte[] jsonByteFinal = jsonFinal.getBytes();

				return new ResponseEntity<byte[]>(jsonByteFinal, HttpStatus.OK);

			} else if (spoolConfig.isLastConsumption()) {

				List<PrintSpool> printSpool = printSpoolRepository.getPrintSpoolL(spoolConfig);
				ObjectMapper objectMapper = new ObjectMapper();
				String json = objectMapper.writeValueAsString(printSpool);
				byte[] jsonByte = json.getBytes();
				JsonNode root = objectMapper.readTree(jsonByte);
				for (JsonNode jsonNode : root) {
					if (jsonNode instanceof ObjectNode) {
						ObjectNode edit = (ObjectNode) jsonNode;
						edit.remove("avgConsumption");
						edit.remove("stratum");
					}
				}
				String jsonFinal = objectMapper.writeValueAsString(root);
				byte[] jsonByteFinal = jsonFinal.getBytes();

				return new ResponseEntity<byte[]>(jsonByteFinal, HttpStatus.OK);

			} else {

				List<PrintSpool> printSpool = printSpoolRepository.getPrintSpool(spoolConfig);
				ObjectMapper objectMapper = new ObjectMapper();
				String json = objectMapper.writeValueAsString(printSpool);
				byte[] jsonByte = json.getBytes();
				JsonNode root = objectMapper.readTree(jsonByte);
				for (JsonNode jsonNode : root) {
					if (jsonNode instanceof ObjectNode) {
						ObjectNode edit = (ObjectNode) jsonNode;
						edit.remove("stratum");
						edit.remove("avgConsumption");
						edit.remove("last");
					}
				}
				String jsonFinal = objectMapper.writeValueAsString(root);
				byte[] jsonByteFinal = jsonFinal.getBytes();

				return new ResponseEntity<byte[]>(jsonByteFinal, HttpStatus.OK);

			}

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}
}
