package FinalProject2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import FinalProject2.model.Department;

public interface DepartmentRepository extends JpaRepository<Department, String> {

}
