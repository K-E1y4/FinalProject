package FinalProject2.repository;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import FinalProject2.model.Employee;
import FinalProject2.model.Task;


public interface TaskRepository extends JpaRepository<Task, String>{
	
	@Query("select t from Task t order by t.make_date asc")
	List<Task> findAllOrderByMake_dateAsc();
	
	//make_userが未入力、task_divisionは入力の場合
	@Query("Select task from Task task "
			+ "WHERE task.task_name LIKE %:task_name% AND "
			+ "task.task_id BETWEEN :taskId_from AND :taskId_to AND "
			+ "task.budget BETWEEN :budget_from AND :budget_to AND "
			+ "task.due_date BETWEEN :due_date_from AND :due_date_to AND "
			+ "task.make_date BETWEEN :make_date_from_timestamp AND :make_date_to_timestamp AND "
			+ "task.task_division_id = :task_division_id_int AND "
		    + "(task.end_flg = :end_flg_A OR task.end_flg = :end_flg_B)")
	Page<Task> findBySearch(String task_name, String taskId_from, String taskId_to, int budget_from,
			int budget_to, LocalDate due_date_from, LocalDate due_date_to, Timestamp make_date_from_timestamp,
			Timestamp make_date_to_timestamp, int task_division_id_int, Boolean end_flg_A, Boolean end_flg_B, Pageable pageable);
	
	//make_userが入力、task_divisionも入力の場合
		@Query("Select task from Task task "
				+ "WHERE task.task_name LIKE %:task_name% AND "
				+ "task.make_user = :make_user AND "
				+ "task.task_id BETWEEN :taskId_from AND :taskId_to AND "
				+ "task.budget BETWEEN :budget_from AND :budget_to AND "
				+ "task.due_date BETWEEN :due_date_from AND :due_date_to AND "
				+ "task.make_date BETWEEN :make_date_from_timestamp AND :make_date_to_timestamp AND "
				+ "task.task_division_id = :task_division_id_int AND "
			    + "(task.end_flg = :end_flg_A OR task.end_flg = :end_flg_B)")
		Page<Task> findBySearch(String task_name, String make_user, String taskId_from, String taskId_to, int budget_from,
				int budget_to, LocalDate due_date_from, LocalDate due_date_to, Timestamp make_date_from_timestamp,
				Timestamp make_date_to_timestamp, int task_division_id_int, Boolean end_flg_A, Boolean end_flg_B, Pageable pageable);
	
	//task_divisionは未入力、make_userは入力の場合
	@Query("Select task from Task task "
			+ "WHERE task.task_name LIKE %:task_name% AND "
			+ "task.make_user = :make_user AND "
			+ "task.task_id BETWEEN :taskId_from AND :taskId_to AND "
			+ "task.budget BETWEEN :budget_from AND :budget_to AND "
			+ "task.due_date BETWEEN :due_date_from AND :due_date_to AND "
			+ "task.make_date BETWEEN :make_date_from_timestamp AND :make_date_to_timestamp AND "
			+ "(task.end_flg = :end_flg_A OR task.end_flg = :end_flg_B)")
	Page<Task> findBySearch(String task_name, String make_user, String taskId_from, String taskId_to, int budget_from,
			int budget_to, LocalDate due_date_from, LocalDate due_date_to, Timestamp make_date_from_timestamp,
			Timestamp make_date_to_timestamp,  Boolean end_flg_A, Boolean end_flg_B, Pageable pageable);
	
	//make_userとtask_divisionが未入力
	@Query("Select task from Task task "
			+ "WHERE task.task_name LIKE %:task_name% AND "
			+ "task.task_id BETWEEN :taskId_from AND :taskId_to AND "
			+ "task.budget BETWEEN :budget_from AND :budget_to AND "
			+ "task.due_date BETWEEN :due_date_from AND :due_date_to AND "
			+ "task.make_date BETWEEN :make_date_from_timestamp AND :make_date_to_timestamp AND "
			+ "(task.end_flg = :end_flg_A OR task.end_flg = :end_flg_B)")
	Page<Task> findBySearch(String task_name, String taskId_from, String taskId_to, int budget_from, int budget_to,
			LocalDate due_date_from, LocalDate due_date_to, Timestamp make_date_from_timestamp, Timestamp make_date_to_timestamp,
			Boolean end_flg_A, Boolean end_flg_B, Pageable pageable);
	
	@Query("Select distinct emplo from Task task "
			+ "LEFT JOIN Employee emplo ON task.employee_id = emplo.employee_id")
	List<Employee> findSekininsya();
	
	@Query("Select task from Task task "
			+ "WHERE task.end_flg = false")
	List<Task> getNotFinishedTasks();

}