package FinalProject2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import FinalProject2.model.Position;

public interface PositionRepository extends JpaRepository<Position, Integer> {
	
	
}

