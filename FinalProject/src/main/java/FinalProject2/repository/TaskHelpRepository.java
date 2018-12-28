package FinalProject2.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import FinalProject2.model.TaskHelp;


public interface TaskHelpRepository extends JpaRepository<TaskHelp, String>{
	
	@Query("select h from TaskHelp h where task_management_id = :task_management_id")
	Optional<TaskHelp> findByTaskManagementId(String task_management_id);
	
}