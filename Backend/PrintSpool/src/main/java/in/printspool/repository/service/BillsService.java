package in.printspool.repository.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.printspool.repository.BillsRepository;

// Interfase para implementar el servicio de la generación de facturas
@Service
public class BillsService {

	@Autowired
	BillsRepository billsRepository;
	// Generación de la facturación hasta la actualidad
	public int save() {
		Date date = new Date();
		SimpleDateFormat DateFor = new SimpleDateFormat("MM/yyyy");
		String stringDate = DateFor.format(date);
		return billsRepository.save(stringDate);
	};

}