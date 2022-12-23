package in.printspool.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ArraySchema;

// CONTROLLER DEL CRUD DE ESTRATOS
@RestController
@RequestMapping("/stratum")
@Tag(name = "Stratum", description = "Stratum CRUD")
@CrossOrigin({ "*" })
public class StratumController {

	@Autowired
	private StratumService stratumService;

	// GET para obtener todos estratos
	@Operation(summary = "Get all stratums")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Stratums obtained", content = {
			@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Stratum.class))) }),
			@ApiResponse(responseCode = "400", description = "Bad request", content = @Content) })
	@GetMapping
	private ResponseEntity<List<Stratum>> getAllStratum() {
		try {
			return ResponseEntity.ok(stratumService.getAllStratum());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	// Get para obtener un estrato por su id
	@Operation(summary = "Get stratum by id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Stratums obtained", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Stratum.class)) }),
			@ApiResponse(responseCode = "400", description = "Bad request", content = @Content) })
	@GetMapping("/{stratumId}")
	private ResponseEntity<Optional<Stratum>> getStratum(@PathVariable Long stratumId) {
		try {
			if (stratumService.getStratum(stratumId).isEmpty()) {
				return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
			}
			return ResponseEntity.ok(stratumService.getStratum(stratumId));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	/*
	 * Post para crear un nuevo estrato, no funciona si el estrato ya existe
	 */
	@Operation(summary = "Create a new stratum")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Stratum added", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Stratum.class)) }),
			@ApiResponse(responseCode = "409", description = "Confict, status is currently in the database", content = @Content),
			@ApiResponse(responseCode = "400", description = "Bad request", content = @Content) })
	@PostMapping
	private ResponseEntity<Stratum> saveStratum(@RequestBody Stratum stratum) {
		try {
			if (stratum.getId() < 1) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			} else if (!stratumService.getStratum(stratum.getId()).isEmpty()) {
				return ResponseEntity.status(HttpStatus.CONFLICT).build();
			}
			Stratum temp = stratumService.create(stratum);
			return ResponseEntity.created(new URI("/api/stratum" + temp.getId())).body(temp);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}

	}

	/*
	 * Put para editar un estrato por su id, no funciona si el estrato no existe
	 */
	@Operation(summary = "Edit a specific stratum by Id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Stratum edited", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Stratum.class)) }),
			@ApiResponse(responseCode = "204", description = "No content, stratum doesn't exist in the database", content = @Content),
			@ApiResponse(responseCode = "400", description = "Bad request", content = @Content) })
	@PutMapping("/{stratumId}")
	private ResponseEntity<Stratum> updateStratum(@RequestBody Stratum stratum, @PathVariable Long stratumId) {
		try {
			if (stratumService.getStratum(stratumId).isEmpty()) {
				return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
			}
			stratum.setId(stratumId);
			Stratum temp = stratumService.create(stratum);
			return ResponseEntity.created(new URI("/api/stratum" + temp.getId())).body(temp);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}

	}

	/*
	 * Delete para eliminar un estrato por su id, no funciona si el estrato no
	 * existe o si el estrato es 6 o menor
	 */
	@Operation(summary = "Delete a specific stratum by Id")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Stratum deleted", content = @Content),
			@ApiResponse(responseCode = "403", description = "Forbidden, you can't delete this stratum", content = @Content),
			@ApiResponse(responseCode = "417", description = "Expectation failed, this stratum doesn't exist", content = @Content),
			@ApiResponse(responseCode = "400", description = "Bad request", content = @Content) })
	@DeleteMapping("/{stratumId}")
	private ResponseEntity<String> deleteStratum(@PathVariable Long stratumId) {
		try {
			if (stratumId < 7) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN).body("It's forbidden delete stratum" + stratumId);
			} else if (stratumService.getStratum(stratumId).isEmpty()) {
				return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("This stratum doesn't exist :)");
			}
			stratumService.delete(stratumId);
			return ResponseEntity.ok("Stratum " + stratumId + " has been deleted");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}

	}

}
