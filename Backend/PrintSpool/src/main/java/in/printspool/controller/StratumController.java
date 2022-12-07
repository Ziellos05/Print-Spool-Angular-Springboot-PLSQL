package in.printspool.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.printspool.model.Stratum;
import in.printspool.repository.service.StratumService;

@RestController
@RequestMapping("/api/stratum")
public class StratumController {

	@Autowired
	private StratumService stratumService;
	
	@PostMapping
	private ResponseEntity<Stratum> saveStratum (@RequestBody Stratum stratum) {
		Stratum temp = stratumService.create(stratum);
		try {
			return ResponseEntity.created(new URI("/api/stratum"+temp.getId())).body(temp);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		
	}
	
	@GetMapping
	private ResponseEntity<List<Stratum>> getAllStratum () {
		return ResponseEntity.ok(stratumService.getAllStratum());
	}
	
	@GetMapping("/{stratumId}")
	private ResponseEntity<Optional<Stratum>> getStratum (@PathVariable Long stratumId) {
		return ResponseEntity.ok(stratumService.getStratum(stratumId));
	}
	
	@DeleteMapping("/{stratumId}")
	private ResponseEntity<Void> deleteStratum (@PathVariable Long stratumId) {
		stratumService.delete(stratumId);
		return ResponseEntity.ok().build();
	}
	
	@PutMapping("/{stratumId}")
	private ResponseEntity<Stratum> updateStratum (@RequestBody Stratum stratum, @PathVariable Long stratumId) {
		stratum.setId(stratumId);
		Stratum temp = stratumService.create(stratum);
		try {
			return ResponseEntity.created(new URI("/api/stratum"+temp.getId())).body(temp);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		
	}
	
}
