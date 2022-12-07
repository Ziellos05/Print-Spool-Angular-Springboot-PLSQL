package in.printspool.repository.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.printspool.model.Stratum;
import in.printspool.repository.StratumRepository;

@Service
public class StratumService {
	
	@Autowired
	private StratumRepository stratumRepository;
	
	public Stratum create (Stratum stratum) {
		return stratumRepository.save(stratum);
	}
	
	public List<Stratum> getAllStratum() {
		return stratumRepository.findAll();
	}
	
	public void delete (Long id) {
		stratumRepository.deleteStratum(id);
	}
	
	public Optional<Stratum> getStratum (Long id) {
		return stratumRepository.findById(id);
	}
	
}
