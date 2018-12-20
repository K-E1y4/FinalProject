package FinalProject2.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import FinalProject2.model.TaskMonthlyResult;
import FinalProject2.repository.TaskMonthlyResultRepository;

@Service
@Transactional
public class TaskMonthlyResultService{

	@Autowired
	TaskMonthlyResultRepository taskMRR;

	private final int pageSize = 1;
	
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

	public List<TaskMonthlyResult> getYearByEmployeeId(String employeeId) {
		return taskMRR.getYearByEmployeeId(employeeId);
	}
	
	public List<TaskMonthlyResult> getYearByEmployeeId(String employeeId, String year) {
		String yStart = year + "-04-01";
		String yEnd = Integer.toString(Integer.parseInt(year)+1) + "-03-31";
		LocalDate yS = LocalDate.parse(yStart, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		LocalDate yE = LocalDate.parse(yEnd, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		return taskMRR.getYearByEmployeeId(employeeId, yS, yE);
	}
}

