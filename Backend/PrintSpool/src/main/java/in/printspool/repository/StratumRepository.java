package in.printspool.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import in.printspool.model.Stratum;

public interface StratumRepository extends JpaRepository<Stratum, Long>{
	
	@Transactional
	@Modifying
	@Query("DELETE FROM Stratum s WHERE s.id =:#{#stratumId} AND s.id>6")
	void deleteStratum(@Param("stratumId") Long stratumId);

}
