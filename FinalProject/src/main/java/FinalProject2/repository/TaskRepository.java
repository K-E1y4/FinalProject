package FinalProject2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import FinalProject2.model.Task;


public interface TaskRepository extends JpaRepository<Task, String>{

}
