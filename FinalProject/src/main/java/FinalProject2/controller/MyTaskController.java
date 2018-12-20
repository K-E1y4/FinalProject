package FinalProject2.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import FinalProject2.model.TaskManagement;
import FinalProject2.model.UserAccount;
import FinalProject2.service.TaskManagementService;

@Controller
@RequestMapping("mytask")
public class MyTaskController {

	@Autowired
	HttpSession session;
	
	@Autowired
	TaskManagementService taskMService;
	
	@GetMapping
	public String mytask(@RequestParam(name = "start_date", defaultValue = "") String startDate, @RequestParam(name = "due_date", defaultValue = "") String dueDate, Model model) {
		UserAccount user = (UserAccount) session.getAttribute("user");
		List<TaskManagement> taskNormalList = taskMService.findNormalByEmployeeId(user.getUsername(), startDate, dueDate);
		List<TaskManagement> taskExpiredList = taskMService.findExpiredByEmployeeId(user.getUsername(), startDate, dueDate);
		List<TaskManagement> taskEndedList = taskMService.findEndedByEmployeeId(user.getUsername(), startDate, dueDate);
		model.addAttribute("taskNormalList", taskNormalList);
		model.addAttribute("taskExpiredList", taskExpiredList);
		model.addAttribute("taskEndedList", taskEndedList);
		model.addAttribute("startDate", startDate);
		model.addAttribute("dueDate", dueDate);
		return "mypage/task";
	}	
	
	@GetMapping("done/{id}")
	public String mytaskDone(@PathVariable("id") String taskMId, Model model) {
		UserAccount user = (UserAccount) session.getAttribute("user");
		taskMService.taskDone(user, taskMId);
		return "redirect:/mytask";
	}
}
