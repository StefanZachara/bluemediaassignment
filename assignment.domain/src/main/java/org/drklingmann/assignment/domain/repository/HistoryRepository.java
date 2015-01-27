package org.drklingmann.assignment.domain.repository;

import org.drklingmann.assignment.domain.entities.History;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryRepository extends JpaRepository<History, Long> {

}
