package org.drklingmann.assignment.domain.repository;

import java.util.List;

import org.drklingmann.assignment.domain.entities.ApplicationEntity;
import org.drklingmann.assignment.domain.entities.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface ApplicationRepository extends
	JpaRepository<ApplicationEntity, Long> {
	
	@Query("SELECT a FROM ApplicationEntity a WHERE a.state = ?1")
	List<ApplicationEntity> findAllByState(State state);

	@Query("SELECT a FROM ApplicationEntity a WHERE a.state = ?1 and a.name like ?2")
	List<ApplicationEntity> findAllByStateAndName(State state, String name);
}
