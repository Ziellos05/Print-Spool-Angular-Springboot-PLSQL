package in.printspool.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import in.printspool.model.PrintSpool;
import in.printspool.model.SpoolConfig;
import in.printspool.model.Stratum;
import in.printspool.repository.PrintSpoolRepository;

@RestController
public class PrintSpoolController {

	@Autowired
	private PrintSpoolRepository printSpoolRepository;
	
	@GetMapping(value="/api/spool")
	public ResponseEntity<List<PrintSpool>> getPrintSpool(@RequestBody SpoolConfig spoolConfig) {
		SpoolConfig x = spoolConfig;
		if (x.isStratum() && x.isAvgConsumption() && x.isLastConsumption()) {
			
			try {
				return ResponseEntity.ok(printSpoolRepository.getPrintSpoolSAL(spoolConfig));
			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}
			
		} else if (x.isStratum() && x.isAvgConsumption()) {
			
			try {
				return ResponseEntity.ok(printSpoolRepository.getPrintSpoolSA(spoolConfig));
			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}
			
		} else if (x.isStratum() && x.isLastConsumption()) {
			
			try {
				return ResponseEntity.ok(printSpoolRepository.getPrintSpoolSL(spoolConfig));
			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}
			
		} else if (x.isAvgConsumption() && x.isLastConsumption()) {
			
			try {
				return ResponseEntity.ok(printSpoolRepository.getPrintSpoolAL(spoolConfig));
			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}
			
		} else if (x.isStratum()) {
			
			try {
				return ResponseEntity.ok(printSpoolRepository.getPrintSpoolS(spoolConfig));
			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}
			
		} else if (x.isAvgConsumption()) {
			
			try {
				return ResponseEntity.ok(printSpoolRepository.getPrintSpoolA(spoolConfig));
			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}
			
		} else if (x.isLastConsumption()) {
			
			try {
				return ResponseEntity.ok(printSpoolRepository.getPrintSpoolL(spoolConfig));
			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}
			
		} else {
			
			try {
				return ResponseEntity.ok(printSpoolRepository.getPrintSpool(spoolConfig));
			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}
		}
	}
}
