package FinalProject2.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import FinalProject2.model.Employee;
import FinalProject2.model.TaskMonthlyResult;
import FinalProject2.model.TaskMonthlyResultWithRank;
import FinalProject2.model.TaskYearlyResult;
import FinalProject2.model.TaskYearlyResultWithRank;
import FinalProject2.repository.EmployeeRepository;
import FinalProject2.repository.TaskMonthlyResultRepository;
import FinalProject2.utility.UtilityMethod;

@Service
@Transactional
public class TaskMonthlyResultService{

	@Autowired
	TaskMonthlyResultRepository taskMRR;
	
	@Autowired
	EmployeeService employeeService;

	private final int pageSize = 3;
	
	UtilityMethod util = new UtilityMethod();

	public List<TaskMonthlyResult> findAll() {
	    return taskMRR.findAll();
	}
	
	public Optional<TaskMonthlyResult> findOne(String employee_id) {
        return taskMRR.findById(employee_id);
    }
	
    public TaskMonthlyResult save(TaskMonthlyResult employee) {
        return taskMRR.save(employee);
    }

    @Transactional
    public void delete(String employee_id) {
    	taskMRR.deleteById(employee_id);
    }

	public int getSumBonusPoint(String username) {
		return taskMRR.getSumBonusPoint(username);

	}

	public List<TaskMonthlyResult> findByEmployeeId(String employeeId) {
		return taskMRR.findByEmployeeId(employeeId);
	}

	public Page<TaskMonthlyResult> findByEmployeeId(int pageNum, String employeeId) {
		return taskMRR.findByEmployeeId(employeeId, PageRequest.of(pageNum<=0?0:pageNum, pageSize));
	}

	public List<TaskMonthlyResult> getYearlyByEmployeeId(String employeeId) {
		return taskMRR.getYearlyByEmployeeId(employeeId);
	}
	
	public List<TaskMonthlyResult> getYearlyByEmployeeId(String employeeId, String year) {
		LocalDate[] years = util.getYear(year);
		return taskMRR.getYearlyByEmployeeId(employeeId, years[0], years[1]);
	}

	public List<TaskMonthlyResultWithRank> getMonthlyRank(List<TaskMonthlyResult> resultList) {
		List<TaskMonthlyResultWithRank> yearResultWithRank = new ArrayList<>();
		for (TaskMonthlyResult taskM : resultList) {
			int pRankDepartment = 1 + taskMRR.getRankInDepartmentByMonth(taskM.getResult_point(), taskM.getResult_date(), taskM.getDepartment_id()).size();
			int pRankAll = 1 + taskMRR.getRankInAllByMonth(taskM.getResult_point(), taskM.getResult_date()).size();
			int aRankDepartment = 1;
			int aRankAll = 1;
			int paramDepartment = taskMRR.getMonthByDepartment(taskM.getResult_date(), taskM.getDepartment_id()).size();
			int paramAll = taskMRR.getMonthAll(taskM.getResult_date()).size();
			
			TaskMonthlyResultWithRank tMRwR = new TaskMonthlyResultWithRank();
			tMRwR.setTaskMonthlyResult(taskM);
			tMRwR.setpRankDepartment(pRankDepartment);
			tMRwR.setpRankAll(pRankAll);
//			tMRwR.setaRankDepartment(aRankDepartment);
//			tMRwR.setaRankAll(aRankAll);
			tMRwR.setParamDepartment(paramDepartment);
			tMRwR.setParamAll(paramAll);
			yearResultWithRank.add(tMRwR);
		}
		return yearResultWithRank;
	}

	public TaskYearlyResultWithRank getYearlyRank(String employeeId, String year) {
		LocalDate[] years = util.getYear(year);
		TaskYearlyResult taskYearResult = new TaskYearlyResult();
		String aaa = taskMRR.getTaskYearlyResult(employeeId, years[0], years[1]);
		String[] taskyear = aaa.split(",", 0);
		taskYearResult.setId(taskyear[0]);
		taskYearResult.setResult_point(Integer.parseInt(taskyear[1]));
		taskYearResult.setNorma(Integer.parseInt(taskyear[2]));
		Employee employee = employeeService.findOne(employeeId).get();
		int pRankDepartment = 1 + taskMRR.getYearlyRankByDepartment(taskYearResult.getResult_point(), employee.getEmploymentInfo().getDepartment().getDepartment_id(), years[0], years[1]).size();
		int pRankAll = 1 + taskMRR.getYearlyRankAll(taskYearResult.getResult_point(), years[0], years[1]).size();
		int aRankDepartment = 1;
		int aRankAll = 1;
		List<String> aaaa = taskMRR.getYearlyResultByDepartment(employee.getEmploymentInfo().getDepartment().getDepartment_id(), years[0], years[1]);
		int paramDepartment = taskMRR.getYearlyResultByDepartment(employee.getEmploymentInfo().getDepartment().getDepartment_id(), years[0], years[1]).size();
		int paramAll = taskMRR.getYearlyResultAll(years[0], years[1]).size();

		TaskYearlyResultWithRank taskYearRank = new TaskYearlyResultWithRank();
		taskYearRank.setTaskYearlyResult(taskYearResult);
		taskYearRank.setpRankDepartment(pRankDepartment);
		taskYearRank.setpRankAll(pRankAll);
		taskYearRank.setaRankDepartment(aRankDepartment);
		taskYearRank.setaRankAll(aRankAll);
		taskYearRank.setParamDepartment(paramDepartment);
		taskYearRank.setParamAll(paramAll);

		return taskYearRank;
	}
	
	public TaskYearlyResultWithRank getYearlyRank(String employeeId) {
		LocalDate[] years = util.getLast12();
		TaskYearlyResult taskYearResult = new TaskYearlyResult();
		String aaa = taskMRR.getTaskYearlyResult(employeeId, years[0], years[1]);
		String[] taskyear = aaa.split(",", 0);
		taskYearResult.setId(taskyear[0]);
		taskYearResult.setResult_point(Integer.parseInt(taskyear[1]));
		taskYearResult.setNorma(Integer.parseInt(taskyear[2]));
		Employee employee = employeeService.findOne(employeeId).get();
		int pRankDepartment = 1 + taskMRR.getYearlyRankByDepartment(taskYearResult.getResult_point(), employee.getEmploymentInfo().getDepartment().getDepartment_id(), years[0], years[1]).size();
		int pRankAll = 1 + taskMRR.getYearlyRankAll(taskYearResult.getResult_point(), years[0], years[1]).size();
		int aRankDepartment = 1;
		int aRankAll = 1;
		List<String> aaaa = taskMRR.getYearlyResultByDepartment(employee.getEmploymentInfo().getDepartment().getDepartment_id(), years[0], years[1]);
		int paramDepartment = taskMRR.getYearlyResultByDepartment(employee.getEmploymentInfo().getDepartment().getDepartment_id(), years[0], years[1]).size();
		int paramAll = taskMRR.getYearlyResultAll(years[0], years[1]).size();

		TaskYearlyResultWithRank taskYearRank = new TaskYearlyResultWithRank();
		taskYearRank.setTaskYearlyResult(taskYearResult);
		taskYearRank.setpRankDepartment(pRankDepartment);
		taskYearRank.setpRankAll(pRankAll);
		taskYearRank.setaRankDepartment(aRankDepartment);
		taskYearRank.setaRankAll(aRankAll);
		taskYearRank.setParamDepartment(paramDepartment);
		taskYearRank.setParamAll(paramAll);

		return taskYearRank;
	}
}

