package FinalProject2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import FinalProject2.model.TaskManagement;


public interface TaskManagementRepository extends JpaRepository<TaskManagement, String>{

	@Query("select t from TaskManagement t where t.employee_id = :username and year(t.due_date) = year(GETDATE()) and month(t.due_date) = month(GETDATE()) order by t.due_date")
	List<TaskManagement> findUserMonthly(String username);

	@Query("select t from TaskManagement t where t.employee_id = :employeeId")
	List<TaskManagement> findAllByEmployeeId(String employeeId);

	@Query("select t from TaskManagement t where t.employee_id = :employeeId and GETDATE() <= t.due_date and t.end_date is null ORDER BY t.due_date")
	List<TaskManagement> findNormalByEmployeeId(String employeeId);

	@Query("select t from TaskManagement t where t.employee_id = :employeeId and GETDATE() >= t.due_date and t.end_date is null ORDER BY t.due_date")
	List<TaskManagement> findExpiredByEmployeeId(String employeeId);

	@Query("select t from TaskManagement t where t.employee_id = :employeeId and t.end_date is not null ORDER BY t.end_date DESC")
	List<TaskManagement> findEndedByEmployeeId(String employeeId);
	
	@Query("select t from TaskManagement t where t.employee_id = :employeeId and GETDATE() <= t.due_date and t.end_date is null and t.start_date LIKE CONCAT('%',:startDate,'%') and t.due_date LIKE CONCAT('%',:dueDate,'%') ORDER BY t.due_date")
	List<TaskManagement> findNormalByEmployeeId(String employeeId, String startDate, String dueDate);

	@Query("select t from TaskManagement t where t.employee_id = :employeeId and GETDATE() >= t.due_date and t.end_date is null and t.start_date LIKE CONCAT('%',:startDate,'%') and t.due_date LIKE CONCAT('%',:dueDate,'%') ORDER BY t.due_date")
	List<TaskManagement> findExpiredByEmployeeId(String employeeId, String startDate, String dueDate);
	
	@Query("select t from TaskManagement t where t.employee_id = :employeeId and t.end_date is not null and t.start_date LIKE CONCAT('%',:startDate,'%') and t.due_date LIKE CONCAT('%',:dueDate,'%') ORDER BY t.end_date DESC")
	List<TaskManagement> findEndedByEmployeeId(String employeeId, String startDate, String dueDate);

	@Modifying
	@Query("UPDATE TaskManagement t SET t.end_date = GETDATE(), t.update_date = GETDATE(), t.update_user = :username WHERE t.task_management_id = :id")
	void taskDone(String username, String id);

	@Query("SELECT SUM(t.point) FROM TaskManagement t where t.employee_id = :employeeId and YEAR(t.end_date) = YEAR(GETDATE()) and MONTH(t.end_date) = MONTH(GETDATE())GROUP BY t.point")
	int getPoint(String employeeId);

	@Query("SELECT SUM(t.point) FROM TaskManagement t where t.employee_id = :employeeId and t.end_date IS NULL and YEAR(t.due_date) = YEAR(GETDATE()) and MONTH(t.due_date) = MONTH(GETDATE()) GROUP BY t.point")
	int willGetPoint(String employeeId);

	@Query("SELECT SUM(t.point) FROM TaskManagement t WHERE YEAR(t.end_date) = YEAR(GETDATE()) and MONTH(t.end_date) = MONTH(GETDATE())")
	List<Long> getRankAll();

	@Query("SELECT SUM(t.point) FROM TaskManagement t WHERE YEAR(t.end_date) = YEAR(GETDATE()) and MONTH(t.end_date) = MONTH(GETDATE()) and employee_id IN :emp")
	List<Long> getRankDepartment(List<String> emp);

}
