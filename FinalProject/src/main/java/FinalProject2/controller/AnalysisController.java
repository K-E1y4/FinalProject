package FinalProject2.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import FinalProject2.model.AnalysisDepartment;
import FinalProject2.model.AnalysisEmployee;
import FinalProject2.model.AnalysisForm;
import FinalProject2.model.Department;
import FinalProject2.pagination.PagenationHelper;
import FinalProject2.service.DepartmentService;
import FinalProject2.service.TaskMonthlyResultService;
import FinalProject2.utility.UtilityMethod;

@Controller
@RequestMapping("analysis")
public class AnalysisController {

	@Autowired
	HttpSession session;
	
	@Autowired
	TaskMonthlyResultService taskMRService;
	
	UtilityMethod util = new UtilityMethod();

	@Autowired
	DepartmentService departmentService;
	
	private int pageSize = 20;
	
	@GetMapping
	public String Analysis(Model model) {
		AnalysisForm af = new AnalysisForm();
		List<AnalysisDepartment> analysisDepartments = taskMRService.getAnalysisDepartmentList();
		Page<AnalysisEmployee> analysisEmployees = taskMRService.getAnalysisEmployeePage(0, pageSize);
		List<String> yearList = taskMRService.getYearList();
		List<String> monthList = util.getMonthList();
		List<Department> departmentList = departmentService.findAll();
		session.setAttribute("analysisForm", af);
		model.addAttribute("yearList", yearList);
		model.addAttribute("dMonthList", monthList);
		model.addAttribute("DmonthList", monthList);
		model.addAttribute("departmentList", departmentList);
		model.addAttribute("analysisDepartments", analysisDepartments);
		model.addAttribute("analysisEmployees", analysisEmployees);
		if(analysisEmployees != null) {
			model.addAttribute("analysisEmployeesPage", PagenationHelper.createPagenation(analysisEmployees));
		}
		return "analysis";
	}
	
	@PostMapping
	public String Analysis(@ModelAttribute AnalysisForm af, Model model) {
		List<AnalysisDepartment> analysisDepartments = taskMRService.getAnalysisDepartmentList(af);
		Page<AnalysisEmployee> analysisEmployees = taskMRService.getAnalysisEmployeePage(af, 0, pageSize);
		List<String> yearList = taskMRService.getYearList();
		List<String> dMonthList = util.getMonthList(af.getdYear());
		List<String> eMonthList = util.getMonthList(af.geteYear());
		List<Department> departmentList = departmentService.findAll();
		session.setAttribute("analysisForm", af);
		model.addAttribute("yearList", yearList);
		model.addAttribute("dMonthList", dMonthList);
		model.addAttribute("eMonthList", eMonthList);
		model.addAttribute("departmentList", departmentList);
		model.addAttribute("analysisDepartments", analysisDepartments);
		model.addAttribute("analysisEmployees", analysisEmployees);
		if(analysisEmployees != null) {
			model.addAttribute("analysisEmployeesPage", PagenationHelper.createPagenation(analysisEmployees));
		}
		return "analysis";
	}

	@GetMapping("page={page}")
	public String Page(@PathVariable(name = "page") String page, Model model) {
		int pageNum = Integer.parseInt(page) - 1;
		AnalysisForm af = (AnalysisForm) session.getAttribute("analysisForm");
		List<AnalysisDepartment> analysisDepartments = taskMRService.getAnalysisDepartmentList(af);
		org.springframework.data.domain.Page<AnalysisEmployee> analysisEmployees;
		if(af.getdSort() == null) {
			analysisEmployees = taskMRService.getAnalysisEmployeePage(pageNum, pageSize);

		}else {
			analysisEmployees = taskMRService.getAnalysisEmployeePage(af, pageNum, pageSize);
		}
		List<String> yearList = taskMRService.getYearList();
		List<String> dMonthList = util.getMonthList(af.getdYear());
		List<String> eMonthList = util.getMonthList(af.geteYear());
		List<Department> departmentList = departmentService.findAll();
		model.addAttribute("yearList", yearList);
		model.addAttribute("dMonthList", dMonthList);
		model.addAttribute("eMonthList", eMonthList);
		model.addAttribute("departmentList", departmentList);
		model.addAttribute("analysisDepartments", analysisDepartments);
		model.addAttribute("analysisEmployees", analysisEmployees);
		model.addAttribute("analysisEmployeesPage", PagenationHelper.createPagenation(analysisEmployees));
		return "analysis";
	}
		
}
