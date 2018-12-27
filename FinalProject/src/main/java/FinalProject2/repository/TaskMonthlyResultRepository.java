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

import FinalProject2.model.AnalysisDepartment;
import FinalProject2.model.AnalysisEmployee;
import FinalProject2.model.TaskMonthlyResult;
import FinalProject2.model.TaskYearlyResult;
import FinalProject2.model.testE;

@Repository
public interface TaskMonthlyResultRepository extends JpaRepository<TaskMonthlyResult, String>{
	
	String analysisEmployeeList = 
			"select " +
			"new FinalProject2.model.AnalysisEmployee(\n" + 
			"	e.employee_id\n" + 
			"	,e.employee_name\n" + 
			"	,d.department_id\n" + 
			"	,d.department_name\n" + 
			"	,p.position_id\n" + 
			"	,p.position_name\n" + 
			"	,SUM(t.result_point) as result\n" + 
			"	,SUM(t.norma) as norma\n" + 
			"	,(100 * CONVERT(bigint, SUM(t.result_point)) / CONVERT(bigint, SUM(t.norma))) as achieve" +
			"	)\n" + 
			"from\n" + 
			"	TaskMonthlyResult t\n" + 
			"	INNER JOIN Employee  e on t.employee_id = e.employee_id\n" + 
			"	INNER JOIN EmploymentInfo i on e.employee_id = i.employee_id\n" + 
			"	INNER JOIN Department d on i.department = d\n" + 
			"	INNER JOIN Position p on i.position = p\n" + 
			"\n" + 
			"WHERE\n" + 
			"	t.employee_id IN :activeEmployeeIdList AND\n" + 
			"	t.result_date BETWEEN :yStart and :yEnd \n" + 
			"GROUP BY\n" + 
			"	e.employee_id\n" + 
			"	,e.employee_name\n" + 
			"	,d.department_id\n" + 
			"	,d.department_name\n" + 
			"	,p.position_id\n" + 
			"	,p.position_name\n";
	
	String countAnalysisEmployee1 =
			"select\n" +
			"	count(a)\n" + 
			"from\n" +
				"(select\n" +
				"	e.employee_id\n" + 
				"	,e.employee_name\n" + 
				"	,d.department_id\n" + 
				"	,d.department_name\n" + 
				"	,p.position_id\n" + 
				"	,p.position_name\n" + 
				"	,SUM(t.result_point) as result\n" + 
				"	,SUM(t.norma) as norma\n" + 
				"	,(100 * CONVERT(bigint, SUM(t.result_point)) / CONVERT(bigint, SUM(t.norma))) as achieve\n" +
				"from\n" + 
				"	TaskMonthlyResult t\n" + 
				"	INNER JOIN Employee  e on t.employee_id = e.employee_id\n" + 
				"	INNER JOIN EmploymentInfo i on e.employee_id = i.employee_id\n" + 
				"	INNER JOIN Department d on i.department = d\n" + 
				"	INNER JOIN Position p on i.position = p\n" + 
				"\n" + 
				"WHERE\n" + 
				"	t.employee_id IN :activeEmployeeIdList AND\n" + 
				"	t.result_date BETWEEN :yStart and :yEnd \n" + 
				"GROUP BY\n" + 
				"	e.employee_id\n" + 
				"	,e.employee_name\n" + 
				"	,d.department_id\n" + 
				"	,d.department_name\n" + 
				"	,p.position_id\n" + 
				"	,p.position_name\n" +
			") as a";
	String countAnalysisEmployee =
			"select\n" +
			"	count(DISTINCT t.employee_id)\n" + 
				"from\n" + 
				"	TaskMonthlyResult t\n" + 
				"\n" + 
				"WHERE\n" + 
				"	t.employee_id IN :activeEmployeeIdList AND\n" + 
				"	t.result_date BETWEEN :yStart and :yEnd \n";
	
	String analysisDepartmentList=
			"select new FinalProject2.model.AnalysisDepartment(\n" + 
					"	d.department_id\n" + 
					"	,d.department_name\n" + 
					"	,SUM(t.result_point) as result\n" + 
					"	,SUM(t.norma) as norma\n" + 
					"	,(100 * CONVERT(bigint, SUM(t.result_point)) / CONVERT(bigint, SUM(t.norma))) as achieve)\n" + 
					"from\n" + 
					"	TaskMonthlyResult t\n" + 
					"	INNER JOIN Employee  e on t.employee_id = e.employee_id\n" + 
					"	INNER JOIN EmploymentInfo i on e.employee_id = i.employee_id\n" + 
					"	INNER JOIN Department d on i.department = d\n" + 
					"WHERE\n" + 
					"	t.result_date BETWEEN :yStart and :yEnd \n" + 
					"GROUP BY\n" + 
					"	d.department_id\n" + 
					"	,d.department_name\n";
	
	String countAnalysisDepartment = 
					"select" +
					"	count(a)\n" +
					"from (" +
						"select\n" + 
						"	,SUM(t.result_point) as result\n" + 
						"	,(100 * CONVERT(bigint, SUM(t.result_point)) / CONVERT(bigint, SUM(t.norma))) as achieve\n" +
						"from\n" + 
						"	TaskMonthlyResult t\n" + 
						"	INNER JOIN Employee  e on t.employee_id = e.employee_id\n" + 
						"	INNER JOIN EmploymentInfo i on e.employee_id = i.employee_id\n" + 
						"	INNER JOIN Department d on i.department = d\n" + 
						"WHERE\n" + 
						"	t.result_date BETWEEN :yStart and :yEnd \n" + 
						"GROUP BY\n" + 
						"	d.department_id\n" + 
						"	,d.department_name\n" +
					") as a";
	
	String orderByResult =  "ORDER BY\n" + 
							"	result DESC\n" + 
							"	,achieve DESC";
	
	String orderByAchieve = "ORDER BY\n" + 
							"	achieve DESC\n" + 
							"	,result DESC";
			
			
	
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

	@Query("select MIN(t.result_date) FROM TaskMonthlyResult t ")
	LocalDate getOldestDate();


	@Query(analysisEmployeeList + orderByResult)
	List<AnalysisEmployee> getAnalysisEmployeeListOrderByResult(List<String> activeEmployeeIdList, LocalDate yStart, LocalDate yEnd);
	
	@Query(analysisEmployeeList + orderByAchieve)
	List<AnalysisEmployee> getAnalysisEmployeeListOrderByAchieve(List<String> activeEmployeeIdList, LocalDate yStart, LocalDate yEnd);
	
	@Query(analysisDepartmentList + orderByResult)
	List<AnalysisDepartment> getAnalysisDepartmentListOrderByResult(LocalDate yStart, LocalDate yEnd);
	
	@Query(analysisDepartmentList + orderByAchieve)
	List<AnalysisDepartment> getAnalysisDepartmentListOrderByAchieve(LocalDate yStart, LocalDate yEnd);
	
	
	@Query(value = analysisEmployeeList + orderByResult,
			countQuery = countAnalysisEmployee)
	Page<AnalysisEmployee> getAnalysisEmployeePageOrderByResult(List<String> activeEmployeeIdList, LocalDate yStart, LocalDate yEnd, Pageable pageable);
	
	@Query(value = analysisEmployeeList + orderByAchieve,
			countQuery = countAnalysisEmployee)
	Page<AnalysisEmployee> getAnalysisEmployeePageOrderByAchieve(List<String> activeEmployeeIdList, LocalDate yStart, LocalDate yEnd, Pageable pageable);
	
//	@Query(analysisDepartmentList + orderByResult)
//	Page<AnalysisDepartment> getAnalysisDepartmentPageOrderByResult(LocalDate yStart, LocalDate yEnd, Pageable pageable);
//	
//	@Query(analysisDepartmentList + orderByAchieve)
//	Page<AnalysisDepartment> getAnalysisDepartmentPageOrderByAchieve(LocalDate yStart, LocalDate yEnd, Pageable pageable);
	
}
