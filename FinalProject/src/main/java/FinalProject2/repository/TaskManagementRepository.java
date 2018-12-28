package FinalProject2.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import FinalProject2.model.Employee;
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
	
	@Query("SELECT t FROM TaskManagement t where t.task_detail_id = :task_detail_id")
	List<TaskManagement> findByTaskId(String task_detail_id);
	
	@Modifying
	@Query("Delete from TaskManagement m Where m.task_detail_id = :task_detail_id")
	void deleteByTaskDetailId(String task_detail_id);
	
	@Query("select Max(m.task_management_id) from TaskManagement m ")
	int getMaxID();
	
	@Query("Select distinct emplo from TaskManagement manage "
			+ "LEFT JOIN Employee emplo ON manage.employee_id = emplo.employee_id")
	List<Employee> getTantousya();
	
	String search = "Select manage from TaskManagement manage "
			+ "LEFT JOIN TaskDetail detail On manage.task_detail_id = detail.task_detail_id "
			+ "LEFT JOIN Task task On detail.task_id = task.task_id "
			+ "WHERE task.task_id LIKE %:task_id% AND "
			+ "detail.task_detail_id LIKE %:taskDetail_id% AND "
			+ "manage.employee_id LIKE %:employee_id% AND "
			+ "manage.due_date BETWEEN :due_date_from AND :due_date_to";
	
	@Query(search)
	Page<TaskManagement> findBySearch(String task_id, String taskDetail_id, String employee_id, LocalDate due_date_from, LocalDate due_date_to, Pageable pageable);
	
	@Query(search + " and manage.end_date IS NULL")
	Page<TaskManagement> findBySearchNotEndTask(String task_id, String taskDetail_id, String employee_id, LocalDate due_date_from, LocalDate due_date_to, Pageable pageable);
	
	@Query(search + " and manage.end_date IS not NULL")
	Page<TaskManagement> findBySearchEndTask(String task_id, String taskDetail_id, String employee_id, LocalDate due_date_from, LocalDate due_date_to, Pageable pageable);
	
	@Query("SELECT SUM(t.point) FROM TaskManagement t WHERE YEAR(t.end_date) = YEAR(GETDATE()) and MONTH(t.end_date) = MONTH(GETDATE())")
	List<Long> getRankAll();

	@Query("SELECT SUM(t.point) FROM TaskManagement t WHERE YEAR(t.end_date) = YEAR(GETDATE()) and MONTH(t.end_date) = MONTH(GETDATE()) and employee_id IN :emp")
	List<Long> getRankDepartment(List<String> emp);

	@Query("SELECT MAX(t.due_date) FROM TaskManagement t")
	LocalDate getMaxDueDate();

	@Query("SELECT MIN(t.due_date) FROM TaskManagement t")
	LocalDate getMinDueDate();

}


