package in.printspool.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import in.printspool.model.Stratum;

// Interface que es implementada por el servicio de estratos
public interface StratumRepository extends JpaRepository<Stratum, Long> {

	/*
	 * JPQL Query a través de la anotación @Query para realizar un delete con una
	 * restricción, si el estrato es 6 o menor a este, no permite eliminarlo
	 */
	@Transactional
	@Modifying
	@Query("DELETE FROM Stratum s WHERE s.id =:#{#stratumId} AND s.id>6")
	void deleteStratum(@Param("stratumId") Long stratumId);

}
