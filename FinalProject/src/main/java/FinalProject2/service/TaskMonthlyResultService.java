package FinalProject2.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import FinalProject2.model.AnalysisDepartment;
import FinalProject2.model.AnalysisEmployee;
import FinalProject2.model.AnalysisForm;
import FinalProject2.model.Employee;
import FinalProject2.model.TaskMonthlyResult;
import FinalProject2.model.TaskMonthlyResultWithRank;
import FinalProject2.model.TaskYearlyResult;
import FinalProject2.model.TaskYearlyResultWithRank;
import FinalProject2.repository.TaskMonthlyResultRepository;
import FinalProject2.utility.UtilityMethod;

@Service
@Transactional
public class TaskMonthlyResultService{

	@Autowired
	TaskMonthlyResultRepository taskMRR;
	
	@Autowired
	EmployeeService employeeService;

	private final int PAGESIZE = 3;
	
	UtilityMethod util = new UtilityMethod();

	@Autowired
	DepartmentService departmentService;

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
		try {
			return taskMRR.getSumBonusPoint(username);
		}catch (Exception e) {
		}
		return 0;
	}

	public List<TaskMonthlyResult> findByEmployeeId(String employeeId) {
		return taskMRR.findByEmployeeId(employeeId);
	}

	public Page<TaskMonthlyResult> findByEmployeeId(int pageNum, String employeeId) {
		return taskMRR.findByEmployeeId(employeeId, PageRequest.of(pageNum<=0?0:pageNum, PAGESIZE));
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
			tMRwR.setaRankDepartment(aRankDepartment);
			tMRwR.setaRankAll(aRankAll);
			tMRwR.setParamDepartment(paramDepartment);
			tMRwR.setParamAll(paramAll);
			yearResultWithRank.add(tMRwR);
		}
		return yearResultWithRank;
	}

	public TaskYearlyResultWithRank getYearlyRank(String employeeId, String year) {
		LocalDate[] years = util.getYear(year);
		TaskYearlyResult taskYearResult = taskMRR.getTaskYearlyResult(employeeId, years[0], years[1]);
		int pRankDepartment = 0;
		int pRankAll = 0;
		int aRankDepartment = 0;
		int aRankAll = 0;
		int paramDepartment = 0;
		int paramAll = 0;
		if(taskYearResult != null) {
			Employee employee = employeeService.findOne(employeeId).get();
			pRankDepartment = 1 + taskMRR.getYearlyRankByDepartment((int) taskYearResult.getResult_point(), employee.getEmploymentInfo().getDepartment().getDepartment_id(), years[0], years[1]).size();
			pRankAll = 1 + taskMRR.getYearlyRankAll((int) taskYearResult.getResult_point(), years[0], years[1]).size();
			aRankDepartment = 1;
			aRankAll = 1;
			paramDepartment = taskMRR.getYearlyResultByDepartment(employee.getEmploymentInfo().getDepartment().getDepartment_id(), years[0], years[1]).size();
			paramAll = taskMRR.getYearlyResultAll(years[0], years[1]).size();
		}
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
		TaskYearlyResult taskYearResult = taskMRR.getTaskYearlyResult(employeeId, years[0], years[1]);
		int pRankDepartment = 0;
		int pRankAll = 0;
		int aRankDepartment = 0;
		int aRankAll = 0;
		int paramDepartment = 0;
		int paramAll = 0;
		if(taskYearResult != null) {
			Employee employee = employeeService.findOne(employeeId).get();
			pRankDepartment = 1 + taskMRR.getYearlyRankByDepartment((int) taskYearResult.getResult_point(), employee.getEmploymentInfo().getDepartment().getDepartment_id(), years[0], years[1]).size();
			pRankAll = 1 + taskMRR.getYearlyRankAll((int) taskYearResult.getResult_point(), years[0], years[1]).size();
			aRankDepartment = 1;
			aRankAll = 1;
			paramDepartment = taskMRR.getYearlyResultByDepartment(employee.getEmploymentInfo().getDepartment().getDepartment_id(), years[0], years[1]).size();
			paramAll = taskMRR.getYearlyResultAll(years[0], years[1]).size();
		}
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

	
	public List<AnalysisDepartment> getAnalysisDepartmentList() {
		LocalDate[] years = util.getYear();
		List<AnalysisDepartment> yAnalysisDListS = taskMRR.getAnalysisDepartmentListOrderByAchieve(years[0], years[1]);
		return yAnalysisDListS;
}

	public List<AnalysisEmployee> getAnalysisEmployeeList() {
		LocalDate[] years = util.getYear();
		List<String> activeEmployeeIdList = employeeService.getActiveEmployeeIdList();
		List<AnalysisEmployee> yAnalysisEListS = taskMRR.getAnalysisEmployeeListOrderByAchieve(activeEmployeeIdList, years[0], years[1]);
		return yAnalysisEListS;
	}


	public List<AnalysisDepartment> getAnalysisDepartmentList(AnalysisForm af) {
		LocalDate[] years = new LocalDate[2];
		if(af.equals(null)) {
			years = util.getYear();
		} else {
			years = util.getYear(af.getdYear(), af.getdMonth());
			if(af.geteSort().equals("point")) {
				List<AnalysisDepartment> analysisDepartmentPage = taskMRR.getAnalysisDepartmentListOrderByResult(years[0], years[1]);
				return analysisDepartmentPage;
			}
		}
		List<AnalysisDepartment> analysisDepartmentPage = taskMRR.getAnalysisDepartmentListOrderByAchieve(years[0], years[1]);
		return analysisDepartmentPage;
	}

	public Page<AnalysisEmployee> getAnalysisEmployeePage(int pageNum, int pageSize) {
		LocalDate[] years = util.getYear();
		List<String> employeeIdList = employeeService.getActiveEmployeeIdList();
		Page<AnalysisEmployee> analysisEmployeePage = taskMRR.getAnalysisEmployeePageOrderByAchieve(employeeIdList, years[0], years[1],  PageRequest.of(pageNum<=0?0:pageNum, pageSize));
		return analysisEmployeePage;
	}
	
	public Page<AnalysisEmployee> getAnalysisEmployeePage(AnalysisForm af, int pageNum, int pageSize) {
		LocalDate[] years = new LocalDate[2];
		List<String> employeeIdList = new ArrayList<>();
		years = util.getYear(af.geteYear(), af.geteMonth());
		if(af.geteDepartment().equals("")) {
			employeeIdList = employeeService.getActiveEmployeeIdList();
		} else {
			employeeIdList = employeeService.getDepartmentEmployeeIdList(af.geteDepartment());
		}
		if(employeeIdList.isEmpty()) {
			return null;
		}
		if(af.geteSort().equals("point")) {
			Page<AnalysisEmployee> analysisEmployeePage = taskMRR.getAnalysisEmployeePageOrderByResult(employeeIdList, years[0], years[1],  PageRequest.of(pageNum<=0?0:pageNum, pageSize));
			return analysisEmployeePage;
		}
		Page<AnalysisEmployee> analysisEmployeePage = taskMRR.getAnalysisEmployeePageOrderByAchieve(employeeIdList, years[0], years[1],  PageRequest.of(pageNum<=0?0:pageNum, pageSize));
		return analysisEmployeePage;
	}
	
	public List<String> getYearList() {
		LocalDate oldestDate = taskMRR.getOldestDate();
		int oYear = 0;
		if(oldestDate.getMonth().getValue() < 4) {
			oYear = oldestDate.getYear() - 1;
		} else {
			oYear = oldestDate.getYear();
		}
		
		LocalDate nowDate = LocalDate.now();
		int nYear = 0;
		if(nowDate.getMonthValue() < 4) {
			nYear = nowDate.getYear() - 1;
		} else {
			nYear = nowDate.getYear();
		}
		
		List<String> yearList = new ArrayList<>(); 
		for(int i = nYear; i >= oYear; i--) {
			yearList.add(Integer.toString(i));
		}
		return yearList;
	}

}

