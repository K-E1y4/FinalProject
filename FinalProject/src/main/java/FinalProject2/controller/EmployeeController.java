package FinalProject2.controller;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import FinalProject2.model.Department;
import FinalProject2.model.Employee;
import FinalProject2.model.EmployeeSearch;
import FinalProject2.model.EmploymentInfo;
import FinalProject2.model.EmploymentInfoForm;
import FinalProject2.model.Position;
import FinalProject2.model.UserAccount;
import FinalProject2.pagination.PagenationHelper;
import FinalProject2.service.DepartmentService;
import FinalProject2.service.EmployeeService;
import FinalProject2.service.PositionService;

@Controller
@RequestMapping("master/employee")
public class EmployeeController {
	
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private DepartmentService departmentService;
	
	@Autowired
	private PositionService positionService;
	
	@Autowired
	HttpSession session;
	
	//ページングの最初のページ
	private static final int FIRST_PAGE = 0;
	
	@GetMapping
    public String index(Model model) {
		
		//ページネーションで表示する１ページ目を取得
        Page<Employee> employees_page = employeeService.findAll(FIRST_PAGE);
        
        //検索で使用するため、従業員が在籍する部署と役職のリストを取得
        List<Department> departments = departmentService.findAll_activeDepartment();
        List<Position> positions = positionService.findAll_activePosition();
        
        model.addAttribute("employees_page", employees_page);
        model.addAttribute("page", PagenationHelper.createPagenation(employees_page));

        session.setAttribute("departments", departments);
        session.setAttribute("positions", positions);
        
        return "master/employee/index";
    }
	
	@GetMapping("page={page}")
	public String paginate(@PathVariable(name = "page") String page, Model model) {
		
			int page_number = Integer.parseInt(page);
			
			//employeeSearchメソッドでセッションに格納したemployeeSearchBeanを取得
			EmployeeSearch employeeSearch = (EmployeeSearch) session.getAttribute("employeeSearch");
			Page<Employee> employees_page;
			//検索条件が何もなかったら
			if(employeeSearch == null) {
					employees_page = employeeService.findAll(page_number);
			} else {
			//検索条件があったらemployeeSearchを使用して再度ページを取得
					employees_page = employeeService.findBySearch(page_number, employeeSearch);
			}
			
			model.addAttribute("employees_page", employees_page);
			model.addAttribute("page", PagenationHelper.createPagenation(employees_page));
			
			return "master/employee/index";
		
	}
	
	@PostMapping("employeeSearch")
	public String employeeSearch(@Valid @ModelAttribute EmployeeSearch employeeSearch, Model model) {
	
		//検索条件をセッションに格納して、indexで取り出す
		session.setAttribute("employeeId_start", employeeSearch.getEmployeeId_start());
		session.setAttribute("employeeId_to", employeeSearch.getEmployeeId_to());
		session.setAttribute("employee_name", employeeSearch.getEmployee_name());
		session.setAttribute("sex", employeeSearch.getSex());
		session.setAttribute("age", employeeSearch.getAge());
		session.setAttribute("department_name", employeeSearch.getDepartment_name());
		session.setAttribute("position_name", employeeSearch.getPosition_name());
		//paginateメソッドで使用する為に検索条件をセッション格納
		session.setAttribute("employeeSearch", employeeSearch);
		
		Page<Employee> employees_page = employeeService.findBySearch(FIRST_PAGE, employeeSearch);
		
		model.addAttribute("employees_page", employees_page);
		model.addAttribute("page", PagenationHelper.createPagenation(employees_page));
        
		return "master/employee/index";
		
	}
	
	@GetMapping("new")
    public String newEmployee(Model model) {
		
		//EmployeeNewForm new_employee = new EmployeeNewForm();
		Employee new_employee = new Employee();
		String NewEmployeeId = employeeService.makeNewEmployeeId();
		int activeEmployeesNumber = employeeService.getActiveEmployeesNumber();
		List<String> prefectures = employeeService.getPrefecturesList();
		
		model.addAttribute("new_employee", new_employee);
		model.addAttribute("NewEmployeeId", NewEmployeeId);
		model.addAttribute("activeEmployeesNumber", activeEmployeesNumber);
		model.addAttribute("prefectures", prefectures);
	 
        return "master/employee/new";
    }
	
	@GetMapping("{employee_id}/edit")
    public String edit(@PathVariable String employee_id, Model model) {
		Optional<Employee> employee_check = employeeService.findOne(employee_id);
		if (employee_check.isPresent()){
		    Employee employee = employee_check.get();
		    model.addAttribute("employee", employee);
		}
		else{
		   // alternative processing....
		}
		
		List<String> prefectures = employeeService.getPrefecturesList();
		model.addAttribute("prefectures", prefectures);

        return "master/employee/edit";
    }
	
	@GetMapping("{employee_id}/info/edit")
	public String editInfo(@PathVariable String employee_id, Model model) {
		Optional<EmploymentInfo> employmentInfo_check = employeeService.findInfo(employee_id);
		if (employmentInfo_check.isPresent()){
		    EmploymentInfo employmentInfo = employmentInfo_check.get();
		    model.addAttribute("employmentInfo", employmentInfo);
		}
		else{
		   // alternative processing....
		}
		List<Department> departments = departmentService.findAll();
		List<Position> positions = positionService.findAll();
		
        model.addAttribute("positions", positions);
        model.addAttribute("departments", departments);
        
		return "master/employee/editInfo";
		
	}
	
	@GetMapping("{employee_id}")
    public String show(@PathVariable String employee_id, Model model) {
		
		LocalDate localdate_now = LocalDate.now();
		model.addAttribute("localdate_now", localdate_now);
		
		Optional<Employee> employee_check = employeeService.findOne(employee_id);
		if (employee_check.isPresent()){
		    Employee employee = employee_check.get();
		    model.addAttribute("employee", employee);
		} else {
		   // alternative processing....
		}
		Optional<EmploymentInfo> employmentInfo_check = employeeService.findInfo(employee_id);
		if(employmentInfo_check.isPresent()) {
			EmploymentInfo employmentInfo = employmentInfo_check.get();
			model.addAttribute("employmentInfo", employmentInfo);
		} else {
			   // alternative processing....
		}
		List<Department> departmentList = (List<Department>) session.getAttribute("departments");
		Map<String, String> department_MAP = new HashMap<>();
		for(Department department: departmentList) {
			department_MAP.put(department.getDepartment_id(), department.getDepartment_name());
		}
		model.addAttribute("department_MAP", department_MAP);
		
		List<Position> positionList = (List<Position>) session.getAttribute("positions");
		Map<String, String> position_MAP = new HashMap<>();
		for(Position position: positionList) {
			position_MAP.put(String.valueOf(position.getPosition_id()), position.getPosition_name());
		}
        model.addAttribute("position_MAP", position_MAP);
        
        return "master/employee/show";
    }
	
	@PostMapping
    public String create(@ModelAttribute Employee new_employee, BindingResult result, Model model) {
		
		if(result.hasErrors()) {
			
			return "master/employee/new";
		}
		UserAccount  makeUserAccount = (UserAccount) session.getAttribute("user");

		employeeService.saveFirst(new_employee);
		
		//EmploymentInfo作成に必要な情報
		EmploymentInfoForm new_employmentInfoForm = new EmploymentInfoForm();
		List<Department> departments = departmentService.findAll();
		List<Position> positions = positionService.findAll();
		
		model.addAttribute("employee_id", new_employee.getEmployee_id());
        model.addAttribute("new_employmentInfoForm", new_employmentInfoForm);
        model.addAttribute("departments", departments);
        model.addAttribute("positions", positions);
        
        return "master/employee/employment_info";
    }
	
	@GetMapping("{employee_id}/info")
	public String newEmploymentInfo(@PathVariable String employee_id, Model model) {
		
		EmploymentInfoForm new_employmentInfoForm = new EmploymentInfoForm();
		List<Department> departments = departmentService.findAll();
		List<Position> positions = positionService.findAll();
		
		model.addAttribute("new_employmentInfoForm", new_employmentInfoForm);
		model.addAttribute("employee_id", employee_id);
		model.addAttribute("departments", departments);
		model.addAttribute("positions", positions);
		
		return "master/employee/employment_info";
		
	}
	
	
	@PostMapping("employmentInfo")
    public String createInfo(@ModelAttribute EmploymentInfoForm new_employmentInfoForm, BindingResult result, Model model) {
		
		if(result.hasErrors()) {
			
			return "master/employee/employment_Info";
		}
		
		employeeService.saveFirst(new_employmentInfoForm);
        
        return "redirect:/master/employee";
    }
	
	@PostMapping("{employee_id}/edit")
    public String update(@PathVariable String employee_id, @ModelAttribute Employee employee, BindingResult result) {
       if(result.hasErrors()) {
    	   return "master/employee/" + employee_id + "/edit";
       }
       
       employeeService.update(employee);
        
        return "redirect:/master/employee/" + employee_id;
        
    }
	
	@PostMapping("{employee_id}/info/edit")
	public String updateInfo(@PathVariable String employee_id, @ModelAttribute EmploymentInfo employmentInfo, BindingResult result) {
		if(result.hasErrors()) {
	    	   return "master/employee/" + employee_id + "/info/edit";
	    }
		
		employeeService.updateInfo(employmentInfo);
	    
		return "redirect:/master/employee/" + employee_id;
		
	}
	
	@DeleteMapping("{employee_id}")
    public String destroy(@PathVariable String employee_id) {
        employeeService.delete(employee_id);
        return "redirect:/employee";
    }
}



