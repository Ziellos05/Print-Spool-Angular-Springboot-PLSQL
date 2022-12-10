package in.printspool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import in.printspool.repository.BillsRepository;

@RestController
public class BillsController {

	@Autowired
	private BillsRepository eDAO;
	
	@GetMapping("/bills")
	public ResponseEntity<String> saveBills() {
		try {
			eDAO.save();
			return ResponseEntity.ok("Bills have been updated for the current month till this moment");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}
}