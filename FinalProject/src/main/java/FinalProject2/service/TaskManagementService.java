package FinalProject2.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import FinalProject2.model.Employee;
import FinalProject2.model.TaskManagement;
import FinalProject2.model.UserAccount;
import FinalProject2.repository.TaskManagementRepository;

@Service
@Transactional
public class TaskManagementService {

	@Autowired
	TaskManagementRepository taskMRepository;
	
	@Autowired
	EmployeeService employeeService;

	public List<TaskManagement> findUserMonthly(UserAccount user) {
		return taskMRepository.findUserMonthly(user.getUsername());
	}

	public List<TaskManagement> findAllByEmployeeId(UserAccount user) {
		List<TaskManagement> taskMList = new ArrayList<>();
		taskMList.addAll(taskMRepository.findExpiredByEmployeeId(user.getUsername()));
		taskMList.addAll(taskMRepository.findNormalByEmployeeId(user.getUsername()));
		taskMList.addAll(taskMRepository.findEndedByEmployeeId(user.getUsername()));
		return taskMList;
	}

	public List<TaskManagement> findAllByEmployeeId(String employeeId) {
		return taskMRepository.findAllByEmployeeId(employeeId);
	}

	public List<TaskManagement> findNormalByEmployeeId(String employeeId) {
		return taskMRepository.findNormalByEmployeeId(employeeId);
	}

	public List<TaskManagement> findExpiredByEmployeeId(String employeeId) {
		return taskMRepository.findExpiredByEmployeeId(employeeId);
	}

	public List<TaskManagement> findEndedByEmployeeId(String employeeId) {
		return taskMRepository.findEndedByEmployeeId(employeeId);
	}

	public List<TaskManagement> findNormalByEmployeeId(String employeeId, String startDate, String dueDate) {
		return taskMRepository.findNormalByEmployeeId(employeeId, startDate, dueDate);
	}

	public List<TaskManagement> findExpiredByEmployeeId(String employeeId, String startDate, String dueDate) {
		return taskMRepository.findExpiredByEmployeeId(employeeId, startDate, dueDate);
	}

	public List<TaskManagement> findEndedByEmployeeId(String employeeId, String startDate, String dueDate) {
		return taskMRepository.findEndedByEmployeeId(employeeId, startDate, dueDate);
	}

	@Transactional
	public void taskDone(UserAccount user, String id) {
		taskMRepository.taskDone(user.getUsername(), id);
	}

	public int getPoint(String employeeId) {
		try {
			return taskMRepository.getPoint(employeeId);
		}catch (Exception e) {
			return 0;
		}
	}

	public int willGetPoint(String employeeId) {
		try {
			return taskMRepository.willGetPoint(employeeId);
		}catch (Exception e) {
			return 0;
		}
	}

	public int[] getRanks(int getPoint, String employeeId) {
		int[] ranks = new int[4];
		List<Employee> departEmployee = employeeService.findByDepartment(employeeService.findOne(employeeId).get().getEmploymentInfo().getDepartment().getDepartment_id());
		ranks[0] = getRankDepartment(getPoint, departEmployee);
		ranks[1] = departEmployee.size();
		ranks[2] = getRankAll(getPoint);
		ranks[3] = employeeService.getActiveEmployeesNumber();
		return ranks;
	}

	private int getRankDepartment(int getPoint, List<Employee> departEmployee) {
		List<String> empIdList = new ArrayList<>();
		departEmployee.forEach(emp -> empIdList.add(emp.getEmployee_id()));
		List<Long> rankDepartList = taskMRepository.getRankDepartment(empIdList);
		int i = 1;
		if(rankDepartList.get(0) != null) {
			for (Long x : rankDepartList) {
				if(x > getPoint) {
					i++;
				}
			}	
		}
		return i;
	}

	public int getRankAll(int getPoint) {
		List<Long> rankAllList = taskMRepository.getRankAll();
		int i = 1;
		if(rankAllList.get(0) != null) {
			for (Long x : rankAllList) {
				if(x > getPoint) {
					i++;
				}
			}
		}
		return i;
	}

}
