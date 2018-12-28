package FinalProject2.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import FinalProject2.model.Employee;
import FinalProject2.model.TaskDetail;
import FinalProject2.model.TaskHelp;
import FinalProject2.model.TaskManagement;
import FinalProject2.model.TaskManagementSearch;
import FinalProject2.model.UserAccount;
import FinalProject2.repository.EmployeeRepository;
import FinalProject2.repository.TaskDetailRepository;
import FinalProject2.repository.TaskHelpRepository;
import FinalProject2.repository.TaskManagementRepository;

@Service
@Transactional
public class TaskManagementService {

	@Autowired
	TaskManagementRepository taskMRepository;
	
	@Autowired
	TaskDetailRepository taskDetailRepository;
	
	@Autowired
	TaskHelpRepository taskHelpRepository;
	
	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	EmployeeService employeeService;
	
	//20行ごとにページングするように設定
	private static final int PAGE_SIZE=20;

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

	public String getNewTaskManagementId() {
		
		int maxID = taskMRepository.getMaxID();
		String newTaskManagementlId = String.format("%10s", maxID + 1).replace(" ", "0");
		return  newTaskManagementlId;
	}

	public void save(TaskManagement taskManagement) {
		
		String task_detail_id = taskManagement.getTask_detail_id();
		Optional<TaskDetail> taskDetail_check = taskDetailRepository.findById(task_detail_id);
		TaskDetail taskDetail = null;
		if(taskDetail_check.isPresent()) {
			taskDetail = taskDetail_check.get();
		}
		taskManagement.setTaskDetail(taskDetail);
		
		String task_management_id = taskManagement.getTask_management_id();
		Optional<TaskHelp> taskHelp_check = taskHelpRepository.findByTaskManagementId(task_management_id);
		TaskHelp taskHelp = null;
		if(taskHelp_check.isPresent()) {
			taskHelp = taskHelp_check.get();
		}
		taskManagement.setTaskHelp(taskHelp);
		
		String employee_id = taskManagement.getEmployee_id();
		Optional<Employee> employee_check = employeeRepository.findById(employee_id);
		Employee employee = null;
		if(employee_check.isPresent()) {
			employee = employee_check.get();
		}
		taskManagement.setEmployee(employee);
		
		taskMRepository.save(taskManagement);
	}

	public Page<TaskManagement> findAll(int firstPage) {
		return taskMRepository.findAll(PageRequest.of(firstPage<=0?0:firstPage, PAGE_SIZE));
	}

	public List<Employee> getTantousya() {
		return taskMRepository.getTantousya();
	}

	public Page<TaskManagement> findBySearch(int firstPage, @Valid TaskManagementSearch taskManagementSearch) {
		
		String task_id = taskManagementSearch.getTask_id();
		String taskDetail_id = taskManagementSearch.getTask_detail_id();
		String employee_id = taskManagementSearch.getEmployee_id();
		
		String due_date_from_str = taskManagementSearch.getDue_date_from();
		LocalDate due_date_from;
		if(due_date_from_str.equals("")) {
			due_date_from = mostPastDueDate();
		}else {
			due_date_from = LocalDate.parse(due_date_from_str, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		}
		String due_date_to_str = taskManagementSearch.getDue_date_to();
		LocalDate due_date_to;
		if(due_date_to_str.equals("")) {
			due_date_to = mostPresentDueDate();
		}else {
			due_date_to =  LocalDate.parse(due_date_to_str, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		}
		
		String end_flg = taskManagementSearch.getEnd_flg();
		Boolean end_flg_A = false; Boolean end_flg_B = false;
		switch(end_flg) {
		case "全て":
			end_flg_A = false;
			end_flg_B = true;
			break;
		case "未完了":
			end_flg_A = false;
			end_flg_B = false;
			break;
		case "完了":
			end_flg_A = true;
			end_flg_B = true;
			break;
		}
		
		return taskMRepository.findBySearch(task_id, taskDetail_id, employee_id, due_date_from, due_date_to, 
				end_flg_A, end_flg_B, PageRequest.of(firstPage<=0?0:firstPage, PAGE_SIZE));
	}

	private LocalDate mostPresentDueDate() {
		LocalDate mostPastDueDate = LocalDate.now();
		int compareResult;
		for(TaskManagement taskManagement: findAll()) {
			compareResult = mostPastDueDate.compareTo(taskManagement.getDue_date());
			//compare 小さいは正（古い）、大きいは負（新しい）
			if(compareResult < 0) {
				mostPastDueDate = taskManagement.getDue_date();
			}
		}
		return mostPastDueDate;
	}

	private List<TaskManagement> findAll() {
		return taskMRepository.findAll();
	}

	private LocalDate mostPastDueDate() {
		LocalDate mostPresentDueDate = LocalDate.now();
		int compareResult;
		for(TaskManagement taskManagement: findAll()) {
			compareResult = mostPresentDueDate.compareTo(taskManagement.getDue_date());
			//compare 小さいは正（古い）、大きいは負（新しい）
			if(compareResult > 0) {
				mostPresentDueDate = taskManagement.getDue_date();
			}
		}
		return mostPresentDueDate;
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
