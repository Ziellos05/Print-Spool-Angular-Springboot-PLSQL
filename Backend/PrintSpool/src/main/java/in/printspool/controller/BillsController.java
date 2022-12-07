package in.printspool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import in.printspool.repository.BillsRepository;

@RestController
public class BillsController {

	@Autowired
	private BillsRepository eDAO;
	
	@PostMapping("/api/bills")
	public String saveBills() {
		eDAO.save();
		return "Bills have been updated for the current month till this moment";
	}
}