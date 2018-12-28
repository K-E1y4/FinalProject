package FinalProject2.repository;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import FinalProject2.model.TaskDetail;


public interface TaskDetailRepository extends JpaRepository<TaskDetail, String>{
	
	@Query("SELECT detail FROM TaskDetail detail WHERE detail.task_id = :task_id")
	public List<TaskDetail> findByTaskId(String task_id);

	@Query("select t from TaskDetail t order by t.make_date asc")
	public List<TaskDetail> findAllOrderByMake_dateAsc();
	
	@Query("Select taskDetail from TaskDetail taskDetail "
			+ "LEFT OUTER JOIN Task task ON taskDetail.task_id = task.task_id "
			+ "WHERE taskDetail.task_detail_id LIKE %:taskDetail_id% AND "
			+ "taskDetail.task_id LIKE %:task_id% AND "
			+ "task.employee_id LIKE %:sekininsya% AND "
			+ "taskDetail.due_date BETWEEN :due_date_from AND :due_date_to AND "
			+ "taskDetail.make_date BETWEEN :make_date_from AND :make_date_to AND "
		    + "(taskDetail.end_flg = :end_flg_A OR taskDetail.end_flg = :end_flg_B)")
	public Page<TaskDetail> findBySearch(String task_id, String taskDetail_id, String sekininsya, LocalDate due_date_from,
			LocalDate due_date_to, Timestamp make_date_from, Timestamp make_date_to, Boolean end_flg_A,
			Boolean end_flg_B, Pageable pageable);
	
	@Modifying
	@Query("Delete from TaskDetail taskDetail Where taskDetail.task_detail_id = :task_detail_id")
	public void deleteById(String task_detail_id);
	
	@Query("Select d from TaskDetail d Where end_flg = true AND d.task_id = :task_id")
	public List<TaskDetail> getTaskDetailFinishedList(String task_id);
	
	@Query("Select d from TaskDetail d Where end_flg = false AND d.task_id = :task_id")
	public List<TaskDetail> getTaskDetailNotFinishedList(String task_id);
	
	@Query("Select distinct detail From TaskManagement manage LEFT JOIN TaskDetail detail On manage.task_detail_id = detail.task_detail_id")
	public List<TaskDetail> findTaskManagementAdded();
	
}
