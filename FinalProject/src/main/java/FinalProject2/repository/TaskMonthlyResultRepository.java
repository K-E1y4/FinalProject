package FinalProject2.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import FinalProject2.model.TaskMonthlyResult;

@Repository
public interface TaskMonthlyResultRepository extends JpaRepository<TaskMonthlyResult, String>{
	
	@Query("select sum(t.bonus_point_add) from TaskMonthlyResult t where t.employee_id = :username")
	int getSumBonusPoint(@Param("username") String username);

	@Query("select t from TaskMonthlyResult t where t.employee_id = :employeeId  ORDER BY t.result_date DESC")
	List<TaskMonthlyResult> findByEmployeeId(String employeeId);

	@Query("select t from TaskMonthlyResult t where t.employee_id = :employeeId  ORDER BY t.result_date DESC")
	Page<TaskMonthlyResult> findByEmployeeId(String employeeId, Pageable pageable);

	@Query("select t from TaskMonthlyResult t where t.employee_id = :employeeId and result_date > DATEADD(MONTH, -12, GETDATE()) ORDER BY t.result_date DESC")
	List<TaskMonthlyResult> getYearByEmployeeId(String employeeId);

	@Query("select t from TaskMonthlyResult t where t.employee_id = :employeeId and result_date BETWEEN :yStart and :yEnd ORDER BY t.result_date DESC")
	List<TaskMonthlyResult> getYearByEmployeeId(String employeeId, LocalDate yStart, LocalDate yEnd);

}
