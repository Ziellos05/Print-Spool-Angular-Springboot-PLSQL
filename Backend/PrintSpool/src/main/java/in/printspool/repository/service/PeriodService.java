package in.printspool.repository.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import in.printspool.model.Period;
import in.printspool.model.Stratum;
import in.printspool.repository.PeriodRepository;
import in.printspool.repository.StratumRepository;

// Servicio encargado del CRUD de los estrados, utiliza JPA
@Service
public class PeriodService {
	
	@Autowired
	private PeriodRepository periodRepository;
	
	// Obtiene todos los periodos actualmente en la base de datos
	@Transactional(readOnly=true)
	public List<Period> getAllPeriods() {
		return periodRepository.findAll(Sort.by("id").descending());
	}
	
}
