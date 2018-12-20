package FinalProject2.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import FinalProject2.model.TaskManagement;
import FinalProject2.model.UserAccount;
import FinalProject2.repository.TaskManagementRepository;

@Service
@Transactional
public class TaskManagementService {

	@Autowired
	TaskManagementRepository taskMRepository;

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
		return taskMRepository.getPoint(employeeId);
	}

	public int willGetPoint(String employeeId) {
		return taskMRepository.willGetPoint(employeeId);
	}

}
