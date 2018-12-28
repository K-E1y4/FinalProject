package FinalProject2.service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import FinalProject2.model.Employee;
import FinalProject2.model.Task;
import FinalProject2.model.TaskDetail;
import FinalProject2.model.TaskDivision;
import FinalProject2.model.TaskSearch;
import FinalProject2.model.UserAccount;
import FinalProject2.repository.TaskRepository;

@Service
@Transactional
public class TaskService {
	
	//20行ごとにページングするように設定
	private static final int PAGE_SIZE=20;

	@Autowired
	TaskRepository taskRepository;
	
	@Autowired
	HttpSession session;
	
	@Autowired
	EmployeeService employeeService;
	
	@Autowired
	TaskDivisionService taskDivisionService;
	
	@Autowired
	TaskDetailService taskDetailService;
	
	
	//pagination実装のためにListからPageに戻り値の型を変更。キャストも追加
	public Page<Task> findAll(int page) {
			return taskRepository.findAll(PageRequest.of(page<=0?0:page, PAGE_SIZE));
	}
	
	//検索条件を元に検索
	public Page<Task> findBySearch(int page_number, TaskSearch taskSearch) {
		
				String task_name = taskSearch.getTask_name();
				
				List<Task> taskList = findAll();
				//taskId_fromが未入力であれば、taskListの最初のIDをtaskId_toに格納
				String taskId_from = taskSearch.getTaskId_from();
				if(taskId_from.equals("0")) {
					taskId_from = taskList.get(0).getTask_id();
				}
				
				String taskId_to = taskSearch.getTaskId_to();
				if(taskId_to.equals("0")) { //taskId_toが未入力であれば、taskListの最後のIDをtaskId_toに格納
					taskId_to = taskList.get(taskList.size() - 1).getTask_id();
				}
				String taskId_to_ZeroPad = ZeroPad(10, taskId_to);
				int budget_from = Integer.parseInt(taskSearch.getBudget_from());
				int budget_to = Integer.parseInt(taskSearch.getBudget_to());
				if(budget_to == 0) {
					budget_to = maxPoint();
				}
				
				String due_date_from_str = taskSearch.getDue_date_from();
				LocalDate due_date_from;
				if(due_date_from_str.equals("")) {
					due_date_from = mostPastDueDate();
				}else {
					due_date_from = LocalDate.parse(due_date_from_str, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
				}
				String due_date_to_str = taskSearch.getDue_date_to();
				LocalDate due_date_to;
				if(due_date_to_str.equals("")) {
					due_date_to = mostPresentDueDate();
				}else {
					due_date_to =  LocalDate.parse(due_date_to_str, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
				}
				
				String make_date_from_str = taskSearch.getMake_date_from();
				Timestamp  make_date_from;
				if(make_date_from_str.equals("")) {
					make_date_from = mostPastMakeDate();
				} else {
					make_date_from = stringToTimestamp(make_date_from_str);
				}
				String make_date_to_str = taskSearch.getMake_date_to();
				Timestamp make_date_to;
				if(make_date_to_str.equals("")) {
					make_date_to = mostPresentMakeDate();
				} else {
					make_date_to = stringToTimestamp(make_date_to_str);
				}
				
				String end_flg = taskSearch.getEnd_flg();
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
				
				String task_division_id = taskSearch.getTaskDivision();
				String make_user = taskSearch.getMake_user();
				int task_division_id_int = Integer.parseInt(task_division_id);
				if(task_division_id.equals("0")) {
						if(make_user.equals("")) {
							return taskRepository.findBySearch(task_name, ZeroPad(10, taskId_from), ZeroPad(10, taskId_to), 
									budget_from, budget_to, due_date_from, due_date_to, make_date_from, 
									make_date_to, end_flg_A, end_flg_B, PageRequest.of(page_number<=0?0:page_number, PAGE_SIZE));
						} else {
							return taskRepository.findBySearch(task_name, make_user, ZeroPad(10, taskId_from), ZeroPad(10, taskId_to), 
									budget_from, budget_to, due_date_from, due_date_to, make_date_from, 
									make_date_to, end_flg_A, end_flg_B, PageRequest.of(page_number<=0?0:page_number, PAGE_SIZE));
						}	
				} else {
						if(make_user.equals("")) {
							return taskRepository.findBySearch(task_name, ZeroPad(10, taskId_from), ZeroPad(10, taskId_to), 
									budget_from, budget_to, due_date_from, due_date_to, make_date_from, 
									make_date_to, task_division_id_int, end_flg_A, end_flg_B, PageRequest.of(page_number<=0?0:page_number, PAGE_SIZE));
						} else {
							return taskRepository.findBySearch(task_name, make_user, ZeroPad(10, taskId_from), ZeroPad(10, taskId_to), 
									budget_from, budget_to, due_date_from, due_date_to, make_date_from, 
									make_date_to, task_division_id_int, end_flg_A, end_flg_B, PageRequest.of(page_number<=0?0:page_number, PAGE_SIZE));
						}
			    }
			
		}

	private String ZeroPad(int i, String taskId_to) {
		
		String taskId_to_ZeroPad = String.format("%"+ i +"s", taskId_to).replace(" ", "0");
		return taskId_to_ZeroPad;
	}

	private Timestamp mostPresentMakeDate() {
		
		List<Task> taskList = taskRepository.findAllOrderByMake_dateAsc();
		Timestamp mostPresentMakeDate = taskList.get(taskList.size() - 1).getMake_date();
		return mostPresentMakeDate;
		
	}

	private Timestamp mostPastMakeDate() {
		
		List<Task> taskList = taskRepository.findAllOrderByMake_dateAsc();
		Timestamp mostPastMakeDate = taskList.get(0).getMake_date();
		return mostPastMakeDate;
	}

	private LocalDate mostPastDueDate() {
		
		LocalDate mostPresentDueDate = LocalDate.now();
		int compareResult;
		for(Task task: findAll()) {
			compareResult = mostPresentDueDate.compareTo(task.getDue_date());
			//compare 小さいは正（古い）、大きいは負（新しい）
			if(compareResult > 0) {
				mostPresentDueDate = task.getDue_date();
			}
		}
//		String mostPresentDueDate_str = mostPresentDueDate.toString();
		return mostPresentDueDate;
	}

	private LocalDate mostPresentDueDate() {
		
		LocalDate mostPastDueDate = LocalDate.now();
		int compareResult;
		for(Task task: findAll()) {
			compareResult = mostPastDueDate.compareTo(task.getDue_date());
			//compare 小さいは正（古い）、大きいは負（新しい）
			if(compareResult < 0) {
				mostPastDueDate = task.getDue_date();
			}
		}
//		String mostPastDueDate_str = mostPastDueDate.toString();
		return mostPastDueDate;
	}

	private int maxPoint() {
		
		int maxPoint = 0;
		for(Task task: findAll()) {
			if(maxPoint < task.getBudget()) {
				maxPoint = task.getBudget();
			}
		}
		return maxPoint;
	}

	public List<Task> findAll() {
		return taskRepository.findAll();
	}
	
	public Timestamp stringToTimestamp(String day) {
		try {
			return new Timestamp(new SimpleDateFormat("yyyy-MM-dd").parse(day).getTime());
		} catch (ParseException e) {
			// 時間の変換失敗
			e.printStackTrace();
			return null;
		}
	}

	public Task newSet_makeDate_makeUser_updateDate_updateUser() {
		
			Task newTask = new Task();
			
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			UserAccount  makeUserAccount = (UserAccount) session.getAttribute("user");
			String makeUserID  = makeUserAccount.getUsername();
			
			newTask.setMake_date(timestamp);
			newTask.setMake_user(makeUserID);
			newTask.setUpdate_date(timestamp);
			newTask.setUpdate_user(makeUserID);
			
			return newTask;
	}

	public String newTaskId() {
		
		List<Task> taskList = taskRepository.findAll();
		int maxId = 0;
		for(Task task: taskList) {
			if(maxId < Integer.parseInt(task.getTask_id())) {
				maxId = Integer.parseInt(task.getTask_id());
			}
		}
		String newTaskId = String.format("%10s", maxId + 1).replace(" ", "0");
		return  newTaskId;
	}

	public void save(Task task) {
		
		Employee employee = employeeService.findById(task.getEmployee_id());
		TaskDivision taskDivision = taskDivisionService.findById(task.getTask_division_id());
		List<TaskDetail> taskDetailList = taskDetailService.findByTaskId(task.getTask_id());
		
		task.setTaskDetailList(taskDetailList);
		task.setTaskDivision(taskDivision);
		task.setEmployee(employee);
		taskRepository.save(task);
		
	}

	public Task findById(String task_id) {
		
		Optional<Task> task_check = taskRepository.findById(task_id);
		Task task = null;
		if(task_check.isPresent()) {
			task = task_check.get();
		}
		return task;
	}
	
	@Transactional
	public void update(Task task) {
		
		Employee employee = employeeService.findById(task.getEmployee_id());
		TaskDivision taskDivision = taskDivisionService.findById(task.getTask_division_id());
		List<TaskDetail> taskDetailList = taskDetailService.findByTaskId(task.getTask_id());
		
		task.setTaskDetailList(taskDetailList);
		task.setTaskDivision(taskDivision);
		task.setEmployee(employee);
		
		taskRepository.save(task);
		
	}

	public void delete(String task_id) {
		
		taskRepository.deleteById(task_id);
		
	}

	public List<Employee> findSekininsya() {
		return taskRepository.findSekininsya();
	}

	public List<Task> getNotFinishedTasks() {
		return taskRepository.getNotFinishedTasks();
	}
	
}
