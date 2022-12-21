package in.printspool.repository.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import in.printspool.model.Stratum;
import in.printspool.repository.StratumRepository;

// Servicio encargado del CRUD de los estrados, utiliza JPA
@Service
public class StratumService {
	
	@Autowired
	private StratumRepository stratumRepository;
	
	public Stratum create (Stratum stratum) {
		return stratumRepository.save(stratum);
	}
	
	public List<Stratum> getAllStratum() {
		return stratumRepository.findAll(Sort.by("id"));
	}
	
	public void delete (Long id) {
		stratumRepository.deleteStratum(id);
	}
	
	public Optional<Stratum> getStratum (Long id) {
		return stratumRepository.findById(id);
	}
	
}
