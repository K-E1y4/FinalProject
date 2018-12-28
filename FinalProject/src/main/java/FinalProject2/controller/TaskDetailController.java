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
import FinalProject2.model.TaskSearch;
import FinalProject2.model.UserAccount;
import FinalProject2.pagination.PagenationHelper;
import FinalProject2.service.EmployeeService;
import FinalProject2.service.TaskDetailService;
import FinalProject2.service.TaskDivisionService;
import FinalProject2.service.TaskService;

@Controller
@RequestMapping("taskDetail")
public class TaskDetailController {
	
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
		
		//ページングの最初のページ
		private static final int FIRST_PAGE = 0;
		
		@GetMapping
	    public String index(Model model) {
			
				//ページネーションで表示する１ページ目を取得
		        Page<TaskDetail> taskDetail_page = taskDetailService.findAll(FIRST_PAGE);
		        
		        //検索で使用する情報を取得
		        List<Task> tasks = taskService.findAll();
		        List<Employee> employees = taskService.findSekininsya();
		        List<TaskDetail> taskDetails = taskDetailService.findAll();

		        
		        model.addAttribute("taskDetail_page", taskDetail_page);
		        model.addAttribute("page", PagenationHelper.createPagenation(taskDetail_page));
		        
		        session.setAttribute("tasks", tasks);
		        session.setAttribute("employees", employees);
		        session.setAttribute("taskDetails", taskDetails);
		        
		        return "taskDetail/index";
			
		}
		
		@GetMapping("new")
	    public String newTask(Model model) {
			
				TaskDetail newTaskDetail = new TaskDetail();
				
				Timestamp timestamp = new Timestamp(System.currentTimeMillis());
				
				UserAccount  makeUserAccount = (UserAccount) session.getAttribute("user");
				String makeUserID  = makeUserAccount.getUsername();
				
				List<Task> notFinishedTasks = taskService.getNotFinishedTasks();
				model.addAttribute("tasks", notFinishedTasks);
				
				List<TaskDivision> taskDivisionList = taskDivisionService.findAll();
				model.addAttribute("taskDivisions", taskDivisionList);
				
				model.addAttribute("make_date", timestamp);
				model.addAttribute("make_user", makeUserID);
				model.addAttribute("update_date", timestamp);
				model.addAttribute("update_user", makeUserID);
				model.addAttribute("end_flg", false);
				model.addAttribute("NewTaskDetailId", taskDetailService.newTaskDetailId());
				model.addAttribute("newTaskDetail", newTaskDetail);
				
				return "taskDetail/new";
		}
		
		@GetMapping("{task_id}/new")
	    public String newTask(@PathVariable String task_id, Model model) {
			
				TaskDetail newTaskDetail = new TaskDetail();
				newTaskDetail.setTask_id(task_id);
				
				Timestamp timestamp = new Timestamp(System.currentTimeMillis());
				
				UserAccount  makeUserAccount = (UserAccount) session.getAttribute("user");
				String makeUserID  = makeUserAccount.getUsername();
				
				List<Task> notFinishedTasks = taskService.getNotFinishedTasks();
				model.addAttribute("tasks", notFinishedTasks);
				
				List<TaskDivision> taskDivisionList = taskDivisionService.findAll();
				model.addAttribute("taskDivisions", taskDivisionList);
				
				model.addAttribute("task_id", task_id);
				model.addAttribute("make_date", timestamp);
				model.addAttribute("make_user", makeUserID);
				model.addAttribute("update_date", timestamp);
				model.addAttribute("update_user", makeUserID);
				model.addAttribute("end_flg", false);
				model.addAttribute("NewTaskDetailId", taskDetailService.newTaskDetailId());
				model.addAttribute("newTaskDetail", newTaskDetail);
				
				return "taskDetail/new_2";
		}
		
		@PostMapping("JumpTaskShow")
		public String createJumpTaskShow(@ModelAttribute TaskDetail taskDetail, BindingResult result, Model model) {
			
			if(result.hasErrors()) {
				return "taskDetail/new";
			}
			
			taskDetailService.save(taskDetail);
			
			return "redirect:/task/" + taskDetail.getTask_id();
		}

		@PostMapping
		public String create(@ModelAttribute TaskDetail taskDetail, BindingResult result, Model model) {
			
			if(result.hasErrors()) {
				return "taskDetail/new";
			}
			
			taskDetailService.save(taskDetail);
			
			return "redirect:/taskDetail";
		}
		
		//showページにタスク内訳の一覧などを表示させる
		@GetMapping("{task_detail_id}")
		public String show(@PathVariable String task_detail_id, Model model) {
			
			Optional<TaskDetail> taskDetail_check = taskDetailService.findById(task_detail_id);
			TaskDetail taskDetail = null;
			if(taskDetail_check.isPresent()) {
				taskDetail = taskDetail_check.get();
			}
			
			model.addAttribute("taskDetail", taskDetail);
			
			return "taskDetail/show";
		}
		
		@GetMapping("{task_detail_id}/edit")
		public String edit(@PathVariable String task_detail_id, Model model) {
			
			Optional<TaskDetail> taskDetail_check = taskDetailService.findById(task_detail_id);
			TaskDetail taskDetail = null;
			if(taskDetail_check.isPresent()) {
				taskDetail = taskDetail_check.get();
			}
			model.addAttribute("taskDetail", taskDetail);
			
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			UserAccount  makeUserAccount = (UserAccount) session.getAttribute("user");
			String makeUserID  = makeUserAccount.getUsername();
			
			model.addAttribute("update_date", timestamp);
			model.addAttribute("update_user", makeUserID);
			
			List<Task> notFinishedTasks = taskService.getNotFinishedTasks();
			model.addAttribute("tasks", notFinishedTasks);
			
			List<TaskDivision> taskDivisionList = taskDivisionService.findAll();
			model.addAttribute("taskDivisions", taskDivisionList);
			
			return "taskDetail/edit";
			
		}
		
		@GetMapping("{task_detail_id}/edit_show")
		public String edit_show(@PathVariable String task_detail_id, Model model) {
			
			Optional<TaskDetail> taskDetail_check = taskDetailService.findById(task_detail_id);
			TaskDetail taskDetail = null;
			if(taskDetail_check.isPresent()) {
				taskDetail = taskDetail_check.get();
			}
			model.addAttribute("taskDetail", taskDetail);
			
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			UserAccount  makeUserAccount = (UserAccount) session.getAttribute("user");
			String makeUserID  = makeUserAccount.getUsername();
			
			model.addAttribute("update_date", timestamp);
			model.addAttribute("update_user", makeUserID);
			
			List<Task> notFinishedTasks = taskService.getNotFinishedTasks();
			model.addAttribute("tasks", notFinishedTasks);
			
			List<TaskDivision> taskDivisionList = taskDivisionService.findAll();
			model.addAttribute("taskDivisions", taskDivisionList);
			
			return "taskDetail/edit_show";
			
		}
		
		
		@PostMapping("{task_detail_id}/edit")
	    public String update(@PathVariable String task_detail_id, @ModelAttribute TaskDetail taskDetail, BindingResult result, Model model) {
		       
				if(result.hasErrors()) {
						return "taskDetail/" + task_detail_id + "/edit";
				}
	       
		       taskDetailService.update(taskDetail);
		        
		        return "redirect:/taskDetail/" + task_detail_id;
	        
	    }
		
		@PostMapping("{task_detail_id}/edit_show")
	    public String update_show(@PathVariable String task_detail_id, @ModelAttribute TaskDetail taskDetail, BindingResult result, Model model) {
		       
				if(result.hasErrors()) {
						return "taskDetail/" + task_detail_id + "/edit";
				}
	       
		       taskDetailService.update(taskDetail);
		        
		        return "redirect:/task/" +  taskDetail.getTask_id();
	        
	    }
		
		@DeleteMapping("{task_detail_id}/delete")
	    public String destroy(@PathVariable String task_detail_id) {
		       
		       taskDetailService.delete(task_detail_id);
		        
		        return "redirect:/taskDetail";
	        
	    }
		
		@GetMapping("page={page}")
		public String paginate(@PathVariable(name = "page") String page, Model model) {
			
				int page_number = Integer.parseInt(page);
				
				//taskSearchメソッドでセッションに格納したemployeeSearchBeanを取得
				TaskDetailSearch taskDetailSearch = (TaskDetailSearch) session.getAttribute("taskDetailSearch");
				Page<TaskDetail> taskDetail_page;
				//検索条件が何もなかったら
				if(taskDetailSearch == null) {
						taskDetail_page = taskDetailService.findAll(page_number);
				} else {
				//検索条件があったらemployeeSearchを使用して再度ページを取得
						taskDetail_page = taskDetailService.findBySearch(page_number, taskDetailSearch);
				}
				
				model.addAttribute("taskDetail_page", taskDetail_page);
				model.addAttribute("page", PagenationHelper.createPagenation(taskDetail_page));
				
				return "taskDetail/index";
			
		}
		
		@PostMapping("taskDetailSearch")
		public String taskDetailSearch(@Valid @ModelAttribute TaskDetailSearch taskDetailSearch, Model model) {
			
			//検索条件をセッションに格納して、indexで取り出す
			session.setAttribute("due_date_from", taskDetailSearch.getDue_date_from());
			session.setAttribute("due_date_to", taskDetailSearch.getDue_date_to());
			session.setAttribute("make_date_from", taskDetailSearch.getMake_date_from());
			session.setAttribute("make_date_to", taskDetailSearch.getMake_date_to());
			session.setAttribute("make_user", taskDetailSearch.getSekininsya());
			session.setAttribute("end_flg", taskDetailSearch.getEnd_flg());
			session.setAttribute("task_detail_id", taskDetailSearch.getTask_detail_id());
			session.setAttribute("task_id", taskDetailSearch.getTask_id());
			session.setAttribute("sekininsya", taskDetailSearch.getSekininsya());
			
			//paginateメソッドで使用する為に検索条件をセッション格納
			session.setAttribute("taskDetailSearch", taskDetailSearch);
			
			Page<TaskDetail> taskDetail_page = taskDetailService.findBySearch(FIRST_PAGE, taskDetailSearch);
			
			model.addAttribute("taskDetail_page", taskDetail_page);
			model.addAttribute("page", PagenationHelper.createPagenation(taskDetail_page));
	        
			return "taskDetail/index";
			
		}
		

}
