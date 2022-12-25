package in.printspool.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import in.printspool.model.Period;
import in.printspool.model.Stratum;

// Interface que es implementada por el servicio de estratos
@Repository
public interface PeriodRepository extends JpaRepository<Period, Long> {

}
