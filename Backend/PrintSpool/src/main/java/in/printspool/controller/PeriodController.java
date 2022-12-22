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

import in.printspool.model.Period;
import in.printspool.model.Stratum;
import in.printspool.repository.service.PeriodService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ArraySchema;

// CONTROLLER DEL CRUD DE ESTRATOS
@RestController
@RequestMapping("/period")
@Tag(name = "Period", description = "Period getter")
@CrossOrigin({"*"})
public class PeriodController {

	@Autowired
	private PeriodService periodService;

	// GET para obtener todos estratos
	@Operation(summary = "Get all periods", hidden= true)
	@ApiResponses(value = { 
			@ApiResponse(responseCode = "200", description = "Periods obtained", content = {
					@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Period.class))) }),
			@ApiResponse(responseCode = "400", description = "Bad request", content = @Content) })
	@GetMapping
	private ResponseEntity<List<Period>> getAllPeriods() {
		try {
			return ResponseEntity.ok(periodService.getAllPeriods());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

}