package FinalProject2.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
    	//拾ってくるデータが今年度のものかどうかの判定
    	boolean yearFlg = false;
    	if(!(taskYearResult.isEmpty() || year.equals(""))){
    		LocalDate resultDate = taskYearResult.get(0).getResult_date();
    		LocalDate yStart = LocalDate.parse(yearList.get(0) + "-04-01", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    		LocalDate yEnd = LocalDate.parse(Integer.toString(Integer.parseInt(yearList.get(0))+1) + "-03-31", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    		if(resultDate.isAfter(yStart) && resultDate.isBefore(yEnd)) {
    			yearFlg = true;
    		}
    	}
		model.addAttribute("taskYearResult", taskYearResult);
    	model.addAttribute("mypageML", mypageML);
    	model.addAttribute("year", year);
    	model.addAttribute("yearFlg", yearFlg);
    	model.addAttribute("yearList", yearList);
    	model.addAttribute("employee", employee);
		return "mypage/point";
	}
}
