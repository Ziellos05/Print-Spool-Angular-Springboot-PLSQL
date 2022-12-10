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
@RequestMapping("/stratum")
public class StratumController {

	@Autowired
	private StratumService stratumService;
	
	@GetMapping
	private ResponseEntity<List<Stratum>> getAllStratum () {
		try {
			return ResponseEntity.ok(stratumService.getAllStratum());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}
	
	@GetMapping("/{stratumId}")
	private ResponseEntity<Optional<Stratum>> getStratum (@PathVariable Long stratumId) {
		try {
			if (stratumService.getStratum(stratumId).isEmpty()) {
				return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
			}
			return ResponseEntity.ok(stratumService.getStratum(stratumId));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}
	
	@PostMapping
	private ResponseEntity<Stratum> saveStratum (@RequestBody Stratum stratum) {
		try {
			if (!stratumService.getStratum(stratum.getId()).isEmpty()) {
				return ResponseEntity.status(HttpStatus.CONFLICT).build();
			}
			Stratum temp = stratumService.create(stratum);
			return ResponseEntity.created(new URI("/api/stratum"+temp.getId())).body(temp);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		
	}
	
	@PutMapping("/{stratumId}")
	private ResponseEntity<Stratum> updateStratum (@RequestBody Stratum stratum, @PathVariable Long stratumId) {		
		try {
			if (stratumService.getStratum(stratumId).isEmpty()) {
				return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
			}
			stratum.setId(stratumId);
			Stratum temp = stratumService.create(stratum);
			return ResponseEntity.created(new URI("/api/stratum"+temp.getId())).body(temp);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		
	}
	
	@DeleteMapping("/{stratumId}")
	private ResponseEntity<String> deleteStratum (@PathVariable Long stratumId) {
		try {
			if (stratumId < 7) {
				return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body("Es prohibido eliminar el estrato "+stratumId);
			} else if (stratumService.getStratum(stratumId).isEmpty()) {
				return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Este estrato no existe :)");
			}
			stratumService.delete(stratumId);
			return ResponseEntity.ok("El estrato "+stratumId+" ha sido eliminado");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}

	}

	
}
