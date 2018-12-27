package FinalProject2.controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import FinalProject2.utility.UtilityMethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import FinalProject2.model.MyPageMonthlyList;
import FinalProject2.model.TaskManagement;
import FinalProject2.model.UserAccount;
import FinalProject2.service.BonusPointUseService;
import FinalProject2.service.CostDivisionService;
import FinalProject2.service.MyPageMonthlyListService;
import FinalProject2.service.TaskManagementService;
import FinalProject2.service.TaskMonthlyResultService;

@Controller
@RequestMapping("mypage")
public class MyPageController {
	
	@Autowired
	HttpSession session;
	
	@Autowired
	TaskMonthlyResultService taskMRService;
	
	@Autowired
	TaskManagementService taskMService;
	
	@Autowired
	BonusPointUseService bPointUService;

	@Autowired
	MyPageMonthlyListService mypageMLService;
	
	@GetMapping
	public String mypage(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	UserAccount user = UserAccount.class.cast(authentication.getPrincipal());
    	session.setAttribute("user", user);
    	try {
    		List<TaskManagement> taskNormalList = taskMService.findNormalByEmployeeId(user.getUsername());
			List<TaskManagement> taskExpiredList = taskMService.findExpiredByEmployeeId(user.getUsername());
			model.addAttribute("taskNormalList", taskNormalList);
			model.addAttribute("taskExpiredList", taskExpiredList);
	    	MyPageMonthlyList mypageML = mypageMLService.getById(user);
	    	model.addAttribute("mypageML", mypageML);
			int getpoint = taskMRService.getSumBonusPoint(user.getUsername());
			int usepoint = bPointUService.getSumUsePoint(user.getUsername());
			model.addAttribute("point", getpoint - usepoint);
    	}catch (Exception e) {
			// TODO: handle exception
		}
		return "mypage/index";
	}
}
