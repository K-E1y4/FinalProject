package FinalProject2.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import FinalProject2.model.BonusPointUse;
import FinalProject2.model.BonusPointUseForm;
import FinalProject2.model.CostDivision;
import FinalProject2.model.TaskMonthlyResult;
import FinalProject2.model.UserAccount;
import FinalProject2.service.BonusPointUseService;
import FinalProject2.service.CostDivisionService;
import FinalProject2.service.TaskMonthlyResultService;
import FinalProject2.utility.UtilityMethod;

@Controller
@RequestMapping("mypointchange")
public class MyPointChangeController {
	
	@Autowired
	HttpSession session;
	
	@Autowired
	TaskMonthlyResultService taskMRS;
	
	@Autowired
	CostDivisionService costDS;

	@Autowired
	BonusPointUseService bPointUS;
	
	@GetMapping
	public String pointChange(Model model) {
		UserAccount user = (UserAccount) session.getAttribute("user");
		int getpoint = taskMRS.getSumBonusPoint(user.getUsername());
		int usepoint = bPointUS.getSumUsePoint(user.getUsername());
		model.addAttribute("point", getpoint - usepoint);
		List<CostDivision> cd = costDS.findAll();
		model.addAttribute("costDivision", cd);
		return "mypage/pointchange";
	}
	
	@PostMapping
	public String pointForm(@ModelAttribute BonusPointUseForm bPUF, Model model) {
		UserAccount user = (UserAccount) session.getAttribute("user");
		bPointUS.save(user.getUsername(), bPUF);
		return "mypage/pointchange";
	}
	
}
