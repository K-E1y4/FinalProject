package FinalProject2.controller;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import FinalProject2.model.Employee;
import FinalProject2.model.Task;
import FinalProject2.model.TaskDetail;
import FinalProject2.model.TaskDetailSearch;
import FinalProject2.model.TaskDivision;
import FinalProject2.model.TaskManagement;
import FinalProject2.model.TaskManagementSearch;
import FinalProject2.model.TaskSearch;
import FinalProject2.model.UserAccount;
import FinalProject2.pagination.PagenationHelper;
import FinalProject2.service.EmployeeService;
import FinalProject2.service.TaskDetailService;
import FinalProject2.service.TaskDivisionService;
import FinalProject2.service.TaskManagementService;
import FinalProject2.service.TaskService;

@Controller
@RequestMapping("taskManagement")
public class TaskManagementController {
	
		@Autowired
		HttpSession session;
		
		@Autowired
		private TaskService taskService;
		
		@Autowired
		private TaskDetailService taskDetailService;
		
		@Autowired
		private EmployeeService employeeService;
		
		@Autowired
		private TaskDivisionService taskDivisionService;
		
		@Autowired
		private TaskManagementService taskManagementService;
		
		//ページングの最初のページ
		private static final int FIRST_PAGE = 0;
		
		@GetMapping
	    public String index(Model model) {
			
				//ページネーションで表示する１ページ目を取得
		        Page<TaskManagement> taskManagement_page = taskManagementService.findAll(FIRST_PAGE);
		        
		        //検索で使用する情報を取得
		        List<Task> tasks = taskService.findAll();
		        List<Employee> employees = taskManagementService.getTantousya();
		        List<TaskDetail> taskDetails = taskDetailService.findTaskManagementAddedl();
		        
				TaskManagementSearch taskManagementSearch = new TaskManagementSearch();
				session.setAttribute("taskManagementSearch", taskManagementSearch );

		        model.addAttribute("taskManagement_page", taskManagement_page);
		        model.addAttribute("page", PagenationHelper.createPagenation(taskManagement_page));
		        
		        model.addAttribute("tasks", tasks);
		        model.addAttribute("employees", employees);
		        model.addAttribute("taskDetails", taskDetails);
		        
		        return "taskManagement/index";
			
		}
		
		@GetMapping("{task_detail_id}/new")
	    public String newTask(@PathVariable String task_detail_id, Model model) {
				
				TaskManagement taskManagement = new TaskManagement();
				
				Timestamp timestamp = new Timestamp(System.currentTimeMillis());
				
				UserAccount  makeUserAccount = (UserAccount) session.getAttribute("user");
				String makeUserID  = makeUserAccount.getUsername();
				
				List<Employee> ActiveEmployees = employeeService.findActiveEmployees();
				model.addAttribute("employees", ActiveEmployees);
				
				model.addAttribute("task_detail_id", task_detail_id);
				model.addAttribute("make_date", timestamp);
				model.addAttribute("make_user", makeUserID);
				model.addAttribute("update_date", timestamp);
				model.addAttribute("update_user", makeUserID);
				model.addAttribute("NewTaskManagementId", taskManagementService.getNewTaskManagementId());
				model.addAttribute("taskManagement", taskManagement);
				
				return "taskManagement/new";
		}
		
//		@GetMapping("{task_id}/new")
//	    public String newTask2(@PathVariable String task_id, Model model) {
//			
//				TaskDetail newTaskDetail = new TaskDetail();
//				newTaskDetail.setTask_id(task_id);
//				
//				Timestamp timestamp = new Timestamp(System.currentTimeMillis());
//				
//				UserAccount  makeUserAccount = (UserAccount) session.getAttribute("user");
//				String makeUserID  = makeUserAccount.getUsername();
//				
//				List<Task> notFinishedTasks = taskService.getNotFinishedTasks();
//				model.addAttribute("tasks", notFinishedTasks);
//				
//				List<TaskDivision> taskDivisionList = taskDivisionService.findAll();
//				model.addAttribute("taskDivisions", taskDivisionList);
//				
//				model.addAttribute("task_id", task_id);
//				model.addAttribute("make_date", timestamp);
//				model.addAttribute("make_user", makeUserID);
//				model.addAttribute("update_date", timestamp);
//				model.addAttribute("update_user", makeUserID);
//				model.addAttribute("end_flg", false);
//				model.addAttribute("NewTaskDetailId", taskDetailService.newTaskDetailId());
//				model.addAttribute("newTaskDetail", newTaskDetail);
//				
//				return "taskDetail/new_2";
//		}
//		
//		@PostMapping("JumpTaskShow")
//		public String createJumpTaskShow(@ModelAttribute TaskDetail taskDetail, BindingResult result, Model model) {
//			
//			if(result.hasErrors()) {
//				return "taskDetail/new";
//			}
//			
//			taskDetailService.save(taskDetail);
//			
//			return "redirect:/task/" + taskDetail.getTask_id();
//		}

		@PostMapping
		public String create(@ModelAttribute TaskManagement taskManagement, BindingResult result, Model model) {
			
			if(result.hasErrors()) {
				return "taskDetail/new";
			}
			
			taskManagementService.save(taskManagement);
			
			String task_detail_id = taskManagement.getTask_detail_id();
			Optional<TaskDetail> taskDetail_check = taskDetailService.findById(task_detail_id);
			TaskDetail taskDetail = null;
			if(taskDetail_check.isPresent()) {
				taskDetail = taskDetail_check.get();
			}
			String task_id = taskDetail.getTask_id();
			
			return "redirect:/task/" + task_id;
		}
		
//		//showページにタスク内訳の一覧などを表示させる
//		@GetMapping("{task_detail_id}")
//		public String show(@PathVariable String task_detail_id, Model model) {
//			
//			Optional<TaskDetail> taskDetail_check = taskDetailService.findById(task_detail_id);
//			TaskDetail taskDetail = null;
//			if(taskDetail_check.isPresent()) {
//				taskDetail = taskDetail_check.get();
//			}
//			
//			model.addAttribute("taskDetail", taskDetail);
//			
//			return "taskDetail/show";
//		}
//		
//		@GetMapping("{task_detail_id}/edit")
//		public String edit(@PathVariable String task_detail_id, Model model) {
//			
//			Optional<TaskDetail> taskDetail_check = taskDetailService.findById(task_detail_id);
//			TaskDetail taskDetail = null;
//			if(taskDetail_check.isPresent()) {
//				taskDetail = taskDetail_check.get();
//			}
//			model.addAttribute("taskDetail", taskDetail);
//			
//			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
//			UserAccount  makeUserAccount = (UserAccount) session.getAttribute("user");
//			String makeUserID  = makeUserAccount.getUsername();
//			
//			model.addAttribute("update_date", timestamp);
//			model.addAttribute("update_user", makeUserID);
//			
//			List<Task> notFinishedTasks = taskService.getNotFinishedTasks();
//			model.addAttribute("tasks", notFinishedTasks);
//			
//			List<TaskDivision> taskDivisionList = taskDivisionService.findAll();
//			model.addAttribute("taskDivisions", taskDivisionList);
//			
//			return "taskDetail/edit";
//			
//		}
//		
//		@PostMapping("{task_detail_id}/edit")
//	    public String update(@PathVariable String task_detail_id, @ModelAttribute TaskDetail taskDetail, BindingResult result, Model model) {
//		       
//				if(result.hasErrors()) {
//						return "taskDetail/" + task_detail_id + "/edit";
//				}
//	       
//		       taskDetailService.update(taskDetail);
//		        
//		        return "redirect:/taskDetail/" + task_detail_id;
//	        
//	    }
//		
//		@DeleteMapping("{task_detail_id}/delete")
//	    public String destroy(@PathVariable String task_detail_id) {
//		       
//		       taskDetailService.delete(task_detail_id);
//		        
//		        return "redirect:/taskDetail";
//	        
//	    }
//		
//		@GetMapping("page={page}")
//		public String paginate(@PathVariable(name = "page") String page, Model model) {
//			
//				int page_number = Integer.parseInt(page);
//				
//				//taskSearchメソッドでセッションに格納したemployeeSearchBeanを取得
//				TaskDetailSearch taskDetailSearch = (TaskDetailSearch) session.getAttribute("taskDetailSearch");
//				Page<TaskDetail> taskDetail_page;
//				//検索条件が何もなかったら
//				if(taskDetailSearch == null) {
//						taskDetail_page = taskDetailService.findAll(page_number);
//				} else {
//				//検索条件があったらemployeeSearchを使用して再度ページを取得
//						taskDetail_page = taskDetailService.findBySearch(page_number, taskDetailSearch);
//				}
//				
//				model.addAttribute("taskDetail_page", taskDetail_page);
//				model.addAttribute("page", PagenationHelper.createPagenation(taskDetail_page));
//				
//				return "taskDetail/index";
//			
//		}
//		
		@PostMapping("taskManagementSearch")
		public String taskManagementSearch(@Valid @ModelAttribute TaskManagementSearch taskManagementSearch, Model model) {
			
			//paginateメソッドで使用する為に検索条件をセッション格納
			session.setAttribute("taskManagementSearch", taskManagementSearch);
			
			Page<TaskManagement> taskManagement_page = taskManagementService.findBySearch(FIRST_PAGE, taskManagementSearch);
			
			model.addAttribute("taskManagement_page", taskManagement_page);
			model.addAttribute("page", PagenationHelper.createPagenation(taskManagement_page));
	        
			return "taskManagement/index";
			
		}
		

}
