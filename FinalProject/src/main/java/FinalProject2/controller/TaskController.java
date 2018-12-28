package FinalProject2.controller;

import java.sql.Timestamp;
import java.util.List;

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
import FinalProject2.model.TaskDivision;
import FinalProject2.model.TaskSearch;
import FinalProject2.model.UserAccount;
import FinalProject2.pagination.PagenationHelper;
import FinalProject2.service.EmployeeService;
import FinalProject2.service.TaskDetailService;
import FinalProject2.service.TaskDivisionService;
import FinalProject2.service.TaskService;

@Controller
@RequestMapping("task")
public class TaskController {
	
		@Autowired
		HttpSession session;
		
		@Autowired
		private TaskService taskService;
		
		@Autowired
		private EmployeeService employeeService;
		
		@Autowired
		private TaskDivisionService taskDivisionService;
		
		@Autowired
		private TaskDetailService taskDetailService;
		
		//ページングの最初のページ
		private static final int FIRST_PAGE = 0;
		
		@GetMapping
	    public String index(Model model) {
			
				//ページネーションで表示する１ページ目を取得
		        Page<Task> task_page = taskService.findAll(FIRST_PAGE);
		        
		        //検索で使用する情報を取得
		        List<TaskDivision> taskDivisions = taskDivisionService.findAll();
		        List<Employee> employees = employeeService.findAll();
		        
		        model.addAttribute("task_page", task_page);
		        model.addAttribute("page", PagenationHelper.createPagenation(task_page));
	
		        session.setAttribute("taskDivisions", taskDivisions);
		        session.setAttribute("employees", employees);
		        
		      //初期値に0を設定しておきたいformに対するセッション
		        session.setAttribute("taskId_from", "0");
		        session.setAttribute("taskId_to", "0");
		        session.setAttribute("budget_from", "0");
		        session.setAttribute("budget_to", "0");
		        
		        return "task/index";
			
		}
		
		@GetMapping("new")
	    public String newTask(Model model) {
			
				Task newTask = new Task();
				
				Timestamp timestamp = new Timestamp(System.currentTimeMillis());
				UserAccount  makeUserAccount = (UserAccount) session.getAttribute("user");
				String makeUserID  = makeUserAccount.getUsername();
				
				List<Employee> ActiveEmployees = employeeService.findActiveEmployees();
				model.addAttribute("ActiveEmployees", ActiveEmployees);
				
				model.addAttribute("make_date", timestamp);
				model.addAttribute("make_user", makeUserID);
				model.addAttribute("update_date", timestamp);
				model.addAttribute("update_user", makeUserID);
				model.addAttribute("end_flg", false);
				model.addAttribute("NewTaskId", taskService.newTaskId());
				
				model.addAttribute("newTask", newTask);
				
				return "task/new";
		}

		@PostMapping
		public String create(@ModelAttribute Task task, BindingResult result, Model model) {
			
			if(result.hasErrors()) {
				return "task/new";
			}
			
			taskService.save(task);
			
			return "redirect:/task";
		}
		
		//showページにタスク内訳の一覧などを表示させる
		@GetMapping("{task_id}")
		public String show(@PathVariable String task_id, Model model) {
			
			Task task = taskService.findById(task_id);
			
			List<TaskDetail> taskDetailList = taskDetailService.findByTaskId(task_id);
			List<TaskDetail> taskDetailFinishedList = taskDetailService.getTaskDetailFinishedList(task_id);
			List<TaskDetail> taskDetailNotFinishedList = taskDetailService.getTaskDetailNotFinishedList(task_id);
			
			model.addAttribute("task", task);
			model.addAttribute("taskDetailList", taskDetailList);
			model.addAttribute("taskDetailFinishedList", taskDetailFinishedList);
			model.addAttribute("taskDetailNotFinishedList", taskDetailNotFinishedList);
			
			return "task/show";
		}
		
		@GetMapping("{task_id}/edit")
		public String edit(@PathVariable String task_id, Model model) {
			
			Task task = taskService.findById(task_id);
			
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			UserAccount  makeUserAccount = (UserAccount) session.getAttribute("user");
			String makeUserID  = makeUserAccount.getUsername();
			List<Employee> ActiveEmployees = employeeService.findActiveEmployees();
			
			model.addAttribute("ActiveEmployees", ActiveEmployees);
			model.addAttribute("update_date", timestamp);
			model.addAttribute("update_user", makeUserID);
			model.addAttribute("task", task);
			
			return "task/edit";
			
		}
		
		@PostMapping("{task_id}/edit")
	    public String update(@PathVariable String task_id, @ModelAttribute Task task, BindingResult result, Model model) {
		       
				if(result.hasErrors()) {
						return "task/" + task_id + "/edit";
				}
	       
		       taskService.update(task);
		        
		        return "redirect:/task/" + task_id;
	        
	    }
		
		@DeleteMapping("{task_id}/delete")
	    public String destroy(@PathVariable String task_id) {
		       
		       taskService.delete(task_id);
		        
		        return "redirect:/task";
	        
	    }
		
		@GetMapping("page={page}")
		public String paginate(@PathVariable(name = "page") String page, Model model) {
			
				int page_number = Integer.parseInt(page);
				
				//taskSearchメソッドでセッションに格納したemployeeSearchBeanを取得
				TaskSearch taskSearch = (TaskSearch) session.getAttribute("taskSearch");
				Page<Task> task_page;
				//検索条件が何もなかったら
				if(taskSearch == null) {
						task_page = taskService.findAll(page_number);
				} else {
				//検索条件があったらemployeeSearchを使用して再度ページを取得
						task_page = taskService.findBySearch(page_number, taskSearch);
				}
				
				model.addAttribute("task_page", task_page);
				model.addAttribute("page", PagenationHelper.createPagenation(task_page));
				
				return "task/index";
			
		}
		
		@PostMapping("taskSearch")
		public String taskSearch(@Valid @ModelAttribute TaskSearch taskSearch, Model model) {
			
			//検索条件をセッションに格納して、indexで取り出す
			session.setAttribute("budget_from", taskSearch.getBudget_from());
			session.setAttribute("budget_to", taskSearch.getBudget_to());
			session.setAttribute("due_date_from", taskSearch.getDue_date_from());
			session.setAttribute("due_date_to", taskSearch.getDue_date_to());
			session.setAttribute("make_date_from", taskSearch.getMake_date_from());
			session.setAttribute("make_date_to", taskSearch.getMake_date_to());
			session.setAttribute("make_user", taskSearch.getMake_user());
			session.setAttribute("end_flg", taskSearch.getEnd_flg());
			session.setAttribute("task_name", taskSearch.getTask_name());
			session.setAttribute("task_division_id", Integer.parseInt(taskSearch.getTaskDivision()));
			session.setAttribute("taskId_from", taskSearch.getTaskId_from());
			session.setAttribute("taskId_to", taskSearch.getTaskId_to());
			
			//paginateメソッドで使用する為に検索条件をセッション格納
			session.setAttribute("taskSearch", taskSearch);
			
			Page<Task> task_page = taskService.findBySearch(FIRST_PAGE, taskSearch);
			
			model.addAttribute("task_page", task_page);
			model.addAttribute("page", PagenationHelper.createPagenation(task_page));
	        
			return "task/index";
			
		}
		

}
