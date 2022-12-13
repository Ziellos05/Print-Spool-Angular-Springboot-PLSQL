package in.printspool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import in.printspool.repository.BillsRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RestControllerAdvice
@RequestMapping(value = "/bills")
@Tag(name = "Bills", description = "Bills generator")
public class BillsController {

	@Autowired
	private BillsRepository eDAO;
	
	@Operation(summary = "Update bills data to the current date")
	@ApiResponses(value = { 
	  @ApiResponse(responseCode = "200", description = "Bills have been updated for the current month till this moment", 
		content = @Content),
	  @ApiResponse(responseCode = "400", description = "Bad request", 
	    content = @Content) })
	@GetMapping()
	public ResponseEntity<String> saveBills() {
		try {
			eDAO.save();
			return ResponseEntity.ok("Bills have been updated for the current month till this moment");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}
}