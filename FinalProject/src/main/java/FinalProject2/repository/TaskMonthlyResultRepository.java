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
import FinalProject2.model.TaskYearlyResult;

@Repository
public interface TaskMonthlyResultRepository extends JpaRepository<TaskMonthlyResult, String>{
	
	@Query("select sum(t.bonus_point_add) from TaskMonthlyResult t where t.employee_id = :username")
	int getSumBonusPoint(@Param("username") String username);

	@Query("select t from TaskMonthlyResult t where t.employee_id = :employeeId  ORDER BY t.result_date DESC")
	List<TaskMonthlyResult> findByEmployeeId(String employeeId);

	@Query("select t from TaskMonthlyResult t where t.employee_id = :employeeId  ORDER BY t.result_date DESC")
	Page<TaskMonthlyResult> findByEmployeeId(String employeeId, Pageable pageable);

	@Query("select t from TaskMonthlyResult t where t.employee_id = :employeeId and result_date > DATEADD(MONTH, -13, GETDATE()) ORDER BY t.result_date DESC")
	List<TaskMonthlyResult> getYearlyByEmployeeId(String employeeId);

	@Query("select t from TaskMonthlyResult t where t.employee_id = :employeeId and result_date BETWEEN :yStart and :yEnd ORDER BY t.result_date DESC")
	List<TaskMonthlyResult> getYearlyByEmployeeId(String employeeId, LocalDate yStart, LocalDate yEnd);

	@Query("select t from TaskMonthlyResult t where t.result_date = :result_date and t.department_id = :department_id ORDER BY result_point DESC")
	List<TaskMonthlyResult> getMonthByDepartment(LocalDate result_date, String department_id);

	@Query("select t from TaskMonthlyResult t where t.result_point > :result_point and t.result_date = :result_date and t.department_id = :department_id")
	List<TaskMonthlyResult> getRankInDepartmentByMonth(int result_point, LocalDate result_date, String department_id);

	@Query("select t from TaskMonthlyResult t where t.result_date = :result_date ORDER BY result_point DESC")
	List<TaskMonthlyResult> getMonthAll(LocalDate result_date);

	@Query("select t from TaskMonthlyResult t where t.result_point > :result_point and t.result_date = :result_date")
	List<TaskMonthlyResult> getRankInAllByMonth(int result_point, LocalDate result_date);
	
	@Query("select t.employee_id as id ,SUM(t.result_point) as result_point, SUM(t.norma) as norma from TaskMonthlyResult t where t.department_id = :departmentId and t.result_date BETWEEN :yStart and :yEnd group by t.employee_id ORDER BY result_point DESC")
	List<String> getYearlyResultByDepartment(String departmentId, LocalDate yStart, LocalDate yEnd);

	@Query("select t.employee_id as id ,SUM(t.result_point) as result_point, SUM(t.norma) as norma from TaskMonthlyResult t where t.result_date BETWEEN :yStart and :yEnd group by t.employee_id ORDER BY result_point DESC")
	List<String> getYearlyResultAll(LocalDate yStart, LocalDate yEnd);

	@Query("select t.employee_id as id ,SUM(t.result_point) as result_point, SUM(t.norma) as norma from TaskMonthlyResult t where t.result_point > :resultPoint and t.department_id = :departmentId and t.result_date BETWEEN :yStart and :yEnd group by t.employee_id ORDER BY result_point DESC")
	List<String> getYearlyRankByDepartment(int resultPoint, String departmentId, LocalDate yStart, LocalDate yEnd);

	@Query("select t.employee_id as id ,SUM(t.result_point) as result_point, SUM(t.norma) as norma from TaskMonthlyResult t where t.result_point > :resultPoint and t.result_date BETWEEN :yStart and :yEnd group by t.employee_id ORDER BY result_point DESC")
	List<String> getYearlyRankAll(int resultPoint, LocalDate yStart, LocalDate yEnd);

	@Query("select t.employee_id as id ,SUM(t.result_point) as result_point, SUM(t.norma) as norma from TaskMonthlyResult t where t.employee_id = :employeeId and t.result_date BETWEEN :yStart and :yEnd group by t.employee_id")
	String getTaskYearlyResult(String employeeId, LocalDate yStart, LocalDate yEnd);

	//	@Query("select new FinalProject2.model.TaskYearlyResult(t.employee_id as id ,SUM(t.result_point) as result_point, SUM(t.norma) as norma) from TaskMonthlyResult t where t.employee_id = :employeeId and t.result_date BETWEEN :yStart and :yEnd group by t.employee_id")
//	TaskYearlyResult getTaskYearlyResult(String employeeId, LocalDate yStart, LocalDate yEnd);


//	@Query("select t.employee_id,STR(SUM(t.result_point)), STR(SUM(t.norma)) from TaskMonthlyResult t where t.employee_id = :employeeId and t.result_date BETWEEN :yStart and :yEnd group by t.employee_id")
//	String[] getTaskYearlyResult(String employeeId, LocalDate yStart, LocalDate yEnd);
//	@Query("select t.employee_id as id ,SUM(t.result_point) as result_point, SUM(t.norma) as norma from TaskMonthlyResult t where t.employee_id = :employeeId and t.result_date BETWEEN :yStart and :yEnd group by t.employee_id")
//	Object getTaskYearlyResult(String employeeId, LocalDate yStart, LocalDate yEnd);

}
