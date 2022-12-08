package in.printspool.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import in.printspool.model.PrintSpool;
import in.printspool.model.SpoolConfig;
import in.printspool.repository.PrintSpoolRepository;

@RestController
public class PrintSpoolController {

	@Autowired
	private PrintSpoolRepository printSpoolRepository;
	
	@GetMapping(value="/api/spool")
	public ResponseEntity<byte[]> getPrintSpool(@RequestBody SpoolConfig spoolConfig) {
		SpoolConfig x = spoolConfig;
		if (x.isStratum() && x.isAvgConsumption() && x.isLastConsumption()) {
			try {
				
				List<PrintSpool> printSpool = printSpoolRepository.getPrintSpoolSAL(spoolConfig);
				ObjectMapper objectMapper = new ObjectMapper();
				String json = objectMapper.writeValueAsString(printSpool);
				byte[] jsonByte = json.getBytes();
				
				return new ResponseEntity<byte[]>(jsonByte, HttpStatus.OK);
			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}
			
		} else if (x.isStratum() && x.isAvgConsumption()) {
			try {
				
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
			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}

		} else if (x.isStratum() && x.isLastConsumption()) {
			try {
				
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
			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}

		} else if (x.isAvgConsumption() && x.isLastConsumption()) {
			try {
				
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
			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}

		} else if (x.isStratum()) {
			try {
				
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
			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}

		} else if (x.isAvgConsumption()) {
			try {
				
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
			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}

		} else if (x.isLastConsumption()) {
			try {
				
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
			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}

		} else {
			try {
				
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
			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}

		}
	}
}
