package FinalProject2.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import FinalProject2.model.CostDivision;
import FinalProject2.model.DayoffDivision;
import FinalProject2.model.Department;
import FinalProject2.model.Employee;
import FinalProject2.model.EmployeeSearch;
import FinalProject2.model.EmploymentInfoForm;
import FinalProject2.model.Position;
import FinalProject2.model.TaskDivision;
import FinalProject2.model.UserAccount;
import FinalProject2.pagination.PagenationHelper;
import FinalProject2.service.CostDivisionService;
import FinalProject2.service.DayOffDivisionService;
import FinalProject2.service.DepartmentService;
import FinalProject2.service.PositionService;
import FinalProject2.service.TaskDivisionService;

@Controller
@RequestMapping("master/others")
public class OtherMastersController {
	
	@Autowired
	HttpSession session;
	
	//ページングの最初のページ
	private static final int FIRST_PAGE = 0;
	
	@Autowired
	private DepartmentService departmentService;
	
	@Autowired
	private PositionService positionService;
	
	@Autowired
	private TaskDivisionService taskDivisionService;
	
	@Autowired
	private CostDivisionService costDivisionService;
	
	@Autowired
	private DayOffDivisionService dayoffDivisionService;
	
	@GetMapping
    public String index(Model model) {
		
        Page<Department> departments = departmentService.findAll(FIRST_PAGE);
        Page<Position> positions = positionService.findAll(FIRST_PAGE);
        Page<TaskDivision> taskDivisions = taskDivisionService.findAll(FIRST_PAGE);
        Page<CostDivision> costDivisions = costDivisionService.findAll(FIRST_PAGE);
        Page<DayoffDivision> dayoffDivisions = dayoffDivisionService.findAll(FIRST_PAGE);
        
        //UPDATE用のBEAN作成
        Department department = new Department();
        Position position = new Position();
        TaskDivision taskDivision = new TaskDivision();
        CostDivision costDivision = new CostDivision();
        DayoffDivision dayoffDivision = new DayoffDivision();
        
        session.setAttribute("departments_page", PagenationHelper.createPagenation(departments));
        session.setAttribute("positions_page",  PagenationHelper.createPagenation(positions));
        session.setAttribute("taskDivisions_page", PagenationHelper.createPagenation(taskDivisions));
        session.setAttribute("costDivisions_page", PagenationHelper.createPagenation(costDivisions));
        session.setAttribute("dayOffDivisions_page", PagenationHelper.createPagenation(dayoffDivisions));
        
       session.setAttribute("departments", departments);
       session.setAttribute("positions", positions);
       session.setAttribute("taskDivisions", taskDivisions);
       session.setAttribute("costDivisions", costDivisions);
       session.setAttribute("dayoffDivisions", dayoffDivisions);
       
       //UPDATE用のBEANをセット
       model.addAttribute("new_department", department);
       model.addAttribute("new_position", position);
       model.addAttribute("new_taskDivision", taskDivision);
       model.addAttribute("new_costDivision", costDivision);
       model.addAttribute("new_dayoffDivision", dayoffDivision);
       
       //新規登録用の各マスタの新IDをセット
       session.setAttribute("department_id_new", departmentService.getNewId());
       session.setAttribute("position_id_new", positionService.getNewId());
       session.setAttribute("task_division_id_new", taskDivisionService.getNewId());
       session.setAttribute("cost_division_id_new", costDivisionService.getNewId());
       session.setAttribute("dayoff_division_id_new", dayoffDivisionService.getNewId());
       
        return "master/others/index";
        
    }
	
	/****************************************************
	 * 
	 * PAGENATE
	 * 
	*****************************************************/
	
	@GetMapping("department/page={page}")
	public String paginate_department(@PathVariable(name = "page") String page, Model model) {
		
		int page_number = Integer.parseInt(page);
		Page<Department> departments = departmentService.findAll(page_number);
		
		session.setAttribute("departments", departments);
		session.setAttribute("departments_page", PagenationHelper.createPagenation(departments));
		
		return "master/others/index";
	
	}
	
	@GetMapping("position/page={page}")
	public String paginate_position(@PathVariable(name = "page") String page, Model model) {
		
		int page_number = Integer.parseInt(page);
		Page<Position> positions = positionService.findAll(page_number);
		
		session.setAttribute("positions", positions);
		session.setAttribute("positions_page", PagenationHelper.createPagenation(positions));
		
		return "master/others/index";
	
	}
	
	@GetMapping("taskDivision/page={page}")
	public String paginate_taskDivision(@PathVariable(name = "page") String page, Model model) {
		
		int page_number = Integer.parseInt(page);
		Page<TaskDivision> taskDivisions = taskDivisionService.findAll(page_number);
		
		session.setAttribute("taskDivisions", taskDivisions);
		session.setAttribute("taskDivisions_page", PagenationHelper.createPagenation(taskDivisions));
		
		return "master/others/index";
	
	}
	
	@GetMapping("costDivision/page={page}")
	public String paginate_costDivision(@PathVariable(name = "page") String page, Model model) {
		
		int page_number = Integer.parseInt(page);
		Page<CostDivision> costDivisions = costDivisionService.findAll(page_number);
		
		session.setAttribute("costDivisions", costDivisions);
		session.setAttribute("costDivisions_page", PagenationHelper.createPagenation(costDivisions));
		
		return "master/others/index";
	
	}
	
	@GetMapping("dayOffDivision/page={page}")
	public String paginate_dayOffDivision(@PathVariable(name = "page") String page, Model model) {
		
		int page_number = Integer.parseInt(page);
		Page<DayoffDivision> dayOffDivisions = dayoffDivisionService.findAll(page_number);
		
		session.setAttribute("dayOffDivisions", dayOffDivisions);
		session.setAttribute("departments_page", PagenationHelper.createPagenation(dayOffDivisions));
		
		return "master/others/index";
	
	}
	
	/****************************************************
	 * 
	 * UPDATE
	 * 
	*****************************************************/
	@PostMapping("department/{department_id}")
	public String update_department(@PathVariable(name = "department_id") String department_id, @ModelAttribute Department department, BindingResult result) {
		
		 if(result.hasErrors()) {
	    	   return "master/others/index";
	       }
	       
	       departmentService.update(department);
	        
	        return "redirect:/master/others";
		
	}
	
	@PostMapping("position/{position_id}")
	public String update_position(@PathVariable(name = "position_id") int position_id, @ModelAttribute Position position, BindingResult result) {
		
		 if(result.hasErrors()) {
	    	   return "master/others/index";
	       }
	       
	       positionService.update(position);
	        
	        return "redirect:/master/others";
		
	}
	
	@PostMapping("taskDivision/{task_division_id}")
	public String update_taskDivision(@PathVariable(name = "task_division_id") int task_division_id, @ModelAttribute TaskDivision taskDivision, BindingResult result) {
		
		 if(result.hasErrors()) {
	    	   return "master/others/index";
	       }
	       
	       taskDivisionService.update(taskDivision);
	        
	        return "redirect:/master/others";
		
	}
	
	@PostMapping("costDivision/{cost_division_id}")
	public String update_costDivision(@PathVariable(name = "cost_division_id") int cost_division_id, @ModelAttribute CostDivision costDivision, BindingResult result) {
		
		 if(result.hasErrors()) {
	    	   return "master/others/index";
	       }
	       
	       costDivisionService.update(costDivision);
	        
	        return "redirect:/master/others";
		
	}
	
	@PostMapping("dayoffDivision/{dayoff_division_id}")
	public String update_dayoffDivision(@PathVariable(name = "dayoff_division_id") int dayoff_division_id, @ModelAttribute DayoffDivision dayoffDivision, BindingResult result) {
		
		 if(result.hasErrors()) {
	    	   return "master/others/index";
	       }
	       
	       dayoffDivisionService.update(dayoffDivision);
	        
	        return "redirect:/master/others";
		
	}
	
	/****************************************************
	 * 
	 * CREATE
	 * 
	*****************************************************/
	@PostMapping("department/new")
	public String create_department(@ModelAttribute Department new_department, BindingResult result, Model model) {
		
			if(result.hasErrors()) {
					return "master/others";
			}
			
			departmentService.save(new_department);
			
			return "redirect:/master/others";
	}
	
	@PostMapping("position/new")
	public String create_position(@ModelAttribute Position new_position, BindingResult result, Model model) {
		
			if(result.hasErrors()) {
					return "master/others";
			}
			
			positionService.save(new_position);
			
			return "redirect:/master/others";
	}
	
	@PostMapping("taskDivision/new")
	public String create_taskDivision(@ModelAttribute TaskDivision new_taskDivision, BindingResult result, Model model) {
		
			if(result.hasErrors()) {
					return "master/others";
			}
			
			taskDivisionService.save(new_taskDivision);
			
			return "redirect:/master/others";
	}
	
	@PostMapping("costDivision/new")
	public String create_costDivision(@ModelAttribute CostDivision new_costDivision, BindingResult result, Model model) {
		
			if(result.hasErrors()) {
					return "master/others";
			}
			
			costDivisionService.save(new_costDivision);
			
			return "redirect:/master/others";
	}
	
	@PostMapping("dayoffDivision/new")
	public String create_dayoffDivision(@ModelAttribute DayoffDivision new_dayoffDivision, BindingResult result, Model model) {
		
			if(result.hasErrors()) {
					return "master/others";
			}
			
			dayoffDivisionService.save(new_dayoffDivision);
			
			return "redirect:/master/others";
	}
	
	/****************************************************
	 * 
	 * DELETE
	 * 
	*****************************************************/
	@GetMapping("department/{department_id}/delete")
	public String delete_department(@PathVariable(name = "department_id") String department_id) {
			
			departmentService.delete(department_id);
			return "redirect:/master/others";
			
	}
	
	@GetMapping("position/{position_id}/delete")
	public String delete_position(@PathVariable(name = "position_id") int position_id) {
			
			positionService.delete(position_id);
			return "redirect:/master/others";
			
	}
	
	@GetMapping("taskDivision/{task_division_id}/delete")
	public String delete_taskDivision(@PathVariable(name = "task_division_id") int task_division_id) {
			
			taskDivisionService.delete(task_division_id);
			return "redirect:/master/others";
			
	}
	
	@GetMapping("costDivision/{cost_division_id}/delete")
	public String delete_costDivision(@PathVariable(name = "cost_division_id") int cost_division_id) {
			
			costDivisionService.delete(cost_division_id);
			return "redirect:/master/others";
			
	}
	
	@GetMapping("dayoffDivision/{dayoff_division_id}/delete")
	public String delete_dayoffDivision(@PathVariable(name = "dayoff_division_id") int dayoff_division_id) {
			
			dayoffDivisionService.delete(dayoff_division_id);
			return "redirect:/master/others";
			
	}
	
}
