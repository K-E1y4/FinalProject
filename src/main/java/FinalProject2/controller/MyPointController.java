package FinalProject2.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import FinalProject2.model.Employee;
import FinalProject2.model.MyPageMonthlyList;
import FinalProject2.model.TaskMonthlyResult;
import FinalProject2.model.UserAccount;
import FinalProject2.service.EmployeeService;
import FinalProject2.service.MyPageMonthlyListService;
import FinalProject2.service.TaskMonthlyResultService;

@Controller
@RequestMapping("mypoint")
public class MyPointController {

	@Autowired
	HttpSession session;
	
	@Autowired
	EmployeeService employeeService;
	
	@Autowired
	TaskMonthlyResultService taskMRService;

	@Autowired
	MyPageMonthlyListService mypageMLService;
	
	@GetMapping
	public String MyPoint(@RequestParam(name = "year", defaultValue = "") String year, Model model) {
		UserAccount user = (UserAccount) session.getAttribute("user");
		List<TaskMonthlyResult> taskYearResult = new ArrayList<>();
		if(year.equals("")) {
			taskYearResult = taskMRService.getYearByEmployeeId(user.getUsername());
		} else {
			taskYearResult = taskMRService.getYearByEmployeeId(user.getUsername(), year);
		}
    	MyPageMonthlyList mypageML = mypageMLService.getById(user);
    	List<String> yearList = employeeService.getYearList(user.getUsername());
    	Employee employee = employeeService.findOne(user.getUsername()).get();
		model.addAttribute("taskYearResult", taskYearResult);
    	model.addAttribute("mypageML", mypageML);
    	model.addAttribute("yearList", yearList);
    	model.addAttribute("employee", employee);
		return "mypage/point";
	}
}
